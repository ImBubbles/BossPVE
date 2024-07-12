package me.bubbles.bosspve.utility;

import me.bubbles.bosspve.BossPVE;
import me.bubbles.bosspve.entities.manager.IEntity;
import me.bubbles.bosspve.game.GameEntity;
import me.bubbles.bosspve.game.GamePlayer;
import me.bubbles.bosspve.stages.Stage;
import me.bubbles.bosspve.utility.messages.PreparedMessages;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_21_R1.entity.CraftLivingEntity;
import org.bukkit.craftbukkit.v1_21_R1.entity.CraftPlayer;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class UtilCustomEvents {

    private Event event;

    public UtilCustomEvents(Event event) {
        this.event=event;
    }

    public void customEntityDeathEvent(IEntity entity) { // player kills mob
        if(!(event instanceof EntityDeathEvent)) {
            return;
        }
        EntityDeathEvent e = (EntityDeathEvent) event;
        if(!entity.hasSameTagAs(e.getEntity())) {
            return;
        }
        /*Stage stage = plugin.getStageManager().getStage(e.getEntity().getLocation());
        if(stage!=null) {
            stage.onKill(((CraftEntity) e.getEntity()).getHandle());
        } else {
            plugin.getLogger().log(Level.SEVERE, "STAGE NOT FOUND");
        }*/
        if(e.getEntity().getKiller()==null) {
            e.getDrops().clear();
            return;
        }
        if(e.getEntity().getKiller().getPlayer()==null) {
            e.getDrops().clear();
            return;
        }
        Player player = e.getEntity().getKiller();
        e.getDrops().clear();
        e.setDroppedExp(0);
        ItemStack itemStack = player.getInventory().getItemInMainHand();
        UtilItemStack uis = new UtilItemStack(itemStack);
        List<ItemStack> drops = entity.rollDrops();
        List<Item> dropped = new ArrayList<>();
        GamePlayer gamePlayer = BossPVE.getInstance().getGameManager().getGamePlayer(player);
        if(!drops.isEmpty()) {
            //e.getDrops().addAll(drops);
            boolean telepathy = false;
            if(itemStack.hasItemMeta()) {
                telepathy = uis.getCustomEnchants().contains(BossPVE.getInstance().getItemManager().getEnchantManager().getEnchant("telepathy"));
            }
            if(telepathy) {
                //int i = 0;

                for(int i=0; i<drops.size(); i++) {
                    ItemStack drop = drops.get(i);
                    PreparedMessages.itemDrop(gamePlayer, entity, drop);
                    if(player.getInventory().firstEmpty()!=-1) {
                        player.getInventory().addItem(drop);
                    } else {
                        Location location = e.getEntity().getLocation();
                        Item item = location.getWorld().dropItem(location, drop);
                        dropped.add(item);
                    }
                }

                /*while((player.getInventory().firstEmpty()!=-1)&&((i+1)<drops.size())) {
                    ItemStack drop = drops.get(i);
                    PreparedMessages.itemDrop(gamePlayer, entity, drop);
                    player.getInventory().addItem(drop);
                    i++;
                }
                if((i+1)<drops.size()) {
                    Location location = e.getEntity().getLocation();
                    for(ItemStack drop : drops) {
                        PreparedMessages.itemDrop(gamePlayer, entity, drop);
                        Item item = location.getWorld().dropItem(location, drop);
                        dropped.add(item);
                    }
                }*/
            } else {
                Location location = e.getEntity().getLocation();
                for(ItemStack drop : drops) {
                    PreparedMessages.itemDrop(gamePlayer, entity, drop);
                    Item item = location.getWorld().dropItem(location, drop);
                    dropped.add(item);
                }
            }
            for(Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                for(Item item : dropped) {
                    if(onlinePlayer!=player) {
                        onlinePlayer.hideEntity(BossPVE.getInstance(), item);
                    }
                }
            }
        }
        int xp=(int) UtilCalculator.getXp(player, entity);
        double money=UtilCalculator.getMoney(player, entity);
        gamePlayer.give(xp, money, entity, true);
    }

    public void customEntityDamageByEntityEvent(IEntity entity) { // mob v player
        if(!(event instanceof EntityDamageByEntityEvent)) {
            return;
        }
        EntityDamageByEntityEvent e = (EntityDamageByEntityEvent) event;
        if(!(e.getEntity() instanceof Player)) {
            return;
        }
        if(e.getDamager() instanceof Player) {
            return;
        }
        if(!entity.hasSameTagAs(e.getDamager())) {
            return;
        }
        double result = entity.getUtilEntity().getDamage();
        Player player = ((Player) e.getEntity()).getPlayer();
        if(player==null) {
            return;
        }
        GamePlayer gamePlayer = BossPVE.getInstance().getGameManager().getGamePlayer(player);
        double protection = gamePlayer.getProtection();
        result=UtilNumber.clampBorder(result, 0, (result/(protection*protection)));
        e.setDamage(0);
        if(!gamePlayer.damage(result)) {
            Stage stage = BossPVE.getInstance().getStageManager().getStage(player.getLocation());
            if(stage==null) {
                player.setHealth(0);
                return;
            }
            if(stage.isAllowed(player)) {
                gamePlayer.setHealth(gamePlayer.getMaxHealth());
                e.setCancelled(true);
                Location location = stage.getSpawn();
                Location playerLoc = player.getLocation();
                location.setPitch(playerLoc.getPitch());
                location.setYaw(playerLoc.getYaw());
                player.teleport(location);
                player.playSound(player, Sound.ENTITY_VILLAGER_HURT, 1, 1);
            }
        }
    }

    public void customEntityDamageByEntityEvent() { // player v entity
        if(!(event instanceof EntityDamageByEntityEvent)) {
            return;
        }
        EntityDamageByEntityEvent e = (EntityDamageByEntityEvent) event;
        if(!(e.getDamager() instanceof Player)) {
            return;
        }
        Player player = ((Player) e.getDamager()).getPlayer();
        if(player==null) {
            return;
        }
        GamePlayer gamePlayer = BossPVE.getInstance().getGameManager().getGamePlayer(player);
        double result = gamePlayer.getDamage();
        if(e.getEntity() instanceof Player) { // If attacking a player
            /*result=UtilNumber.clampBorder(result, 0, result-UtilCalculator.getProtection((Player) e.getEntity()));
            Player player2 = ((Player) e.getEntity());
            GamePlayer gamePlayer = plugin.getGameManager().getGamePlayer(player2);
            if(gamePlayer.damage(result)) {
                e.setDamage(0);
            } else {
                player2.setHealth(0);
            }*/
            e.setCancelled(true);
        } else { // If attacking a mob
            GameEntity gameEntity = BossPVE.getInstance().getGameManager().getGameEntity(e.getEntity().getUniqueId());
            if(gameEntity!=null) {
                gameEntity.damage(result);
            }
            e.setDamage(0);
            LivingEntity livingEntity = ((LivingEntity) e.getEntity());
            net.minecraft.world.entity.LivingEntity nmsEntity = ((CraftLivingEntity) livingEntity).getHandle();
            nmsEntity.setLastHurtByPlayer(((CraftPlayer) player).getHandle());
            //livingEntity.setLastDamageCause(e);
            result = UtilNumber.clampBorder(livingEntity.getHealth(), 0, (livingEntity.getHealth())-result);
            if(result==0) {
                nmsEntity.kill();
            } else {
                livingEntity.setHealth(result);
            }
            //FixedDamageSource damageSource = UtilCalculator.damageSourceFromBukkit(DamageType.PLAYER_ATTACK, player, e.getEntity(), player.getLocation());
            //((CraftEntity) e.getEntity()).getHandle().hurt(damageSource, (float) 0);
            //System.out.println("final dmg: "+e.getFinalDamage());
            //e.setDamage(EntityDamageEvent.DamageModifier., result);
            //e.setDamage(0);


            //e.setDamage(0);

            //DamageSource damageSource = CraftDamageSource.buildFromBukkit(DamageType.PLAYER_ATTACK, player, e.getEntity(), e.getEntity().getLocation());

            //gameEntity.getEntity().hurt(damageSource, (float) result);*/
        }
    }

}
