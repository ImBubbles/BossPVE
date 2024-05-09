package me.bubbles.bosspve.utility;

import me.bubbles.bosspve.BossPVE;
import me.bubbles.bosspve.entities.manager.IEntity;
import me.bubbles.bosspve.game.GameEntity;
import me.bubbles.bosspve.game.GamePlayer;
import me.bubbles.bosspve.stages.Stage;
import me.bubbles.bosspve.utility.messages.PreparedMessages;
import me.bubbles.bosspve.utility.nms.FixedDamageSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.ai.attributes.Attributes;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.craftbukkit.v1_20_R3.damage.CraftDamageSource;
import org.bukkit.craftbukkit.v1_20_R3.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_20_R3.entity.CraftPlayer;
import org.bukkit.damage.DamageType;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class UtilCustomEvents {

    private Event event;
    private BossPVE plugin;

    public UtilCustomEvents(BossPVE plugin, Event event) {
        this.plugin=plugin;
        this.event=event;
    }

    public void customEntityDeathEvent(IEntity entity) { // player kills mob
        if(!(event instanceof EntityDeathEvent)) {
            return;
        }
        EntityDeathEvent e = (EntityDeathEvent) event;
        if(e.getEntity().getKiller()==null) {
            e.getDrops().clear();
            return;
        }
        if(e.getEntity().getKiller().getPlayer()==null) {
            e.getDrops().clear();
            return;
        }
        if(!entity.hasSameTagAs(e.getEntity())) {
            return;
        }
        Player player = e.getEntity().getKiller();
        Stage stage = plugin.getStageManager().getStage(e.getEntity().getLocation());
        if(stage!=null) {
            stage.onKill(((CraftEntity) e.getEntity()).getHandle());
        }
        e.getDrops().clear();
        e.setDroppedExp(0);
        ItemStack itemStack = player.getInventory().getItemInMainHand();
        UtilItemStack uis = new UtilItemStack(plugin,itemStack);
        List<ItemStack> drops = entity.getDrops();
        List<Item> dropped = new ArrayList<>();
        GamePlayer gamePlayer = plugin.getGameManager().getGamePlayer(player);
        if(!drops.isEmpty()) {
            //e.getDrops().addAll(drops);
            boolean telepathy = false;
            if(itemStack.hasItemMeta()) {
                telepathy = uis.getCustomEnchants().contains(plugin.getItemManager().getEnchantManager().getEnchant("telepathy"));
            }
            if(telepathy) {
                int i = 0;
                while(player.getInventory().firstEmpty()!=-1&&(i+1)<drops.size()) {
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
                }
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
                        onlinePlayer.hideEntity(plugin, item);
                    }
                }
            }
        }
        GameEntity gameEntity = plugin.getGameManager().getGameEntity(e.getEntity().getUniqueId());
        plugin.getGameManager().delete(gameEntity);
        int xp=(int) UtilCalculator.getXp(player, entity);
        double money=UtilCalculator.getMoney(player, entity);
        gamePlayer.give(xp, money, entity, true);
        /*UtilUserData uud = gamePlayer.getCache();
        uud.setXp(uud.getXp()+xp);
        plugin.getEconomy().depositPlayer(player,money);
        PreparedMessages.kill(plugin.getGameManager().getGamePlayer(player), entity, xp, money);*/
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
        result=UtilNumber.clampBorder(result, 0, result-UtilCalculator.getProtection(player));
        GamePlayer gamePlayer = plugin.getGameManager().getGamePlayer(player);
        //player.setHealth(20);
        e.setDamage(0);
        //gamePlayer.updateHealthBar();
        /*if(player.getHealth()-e.getFinalDamage()<=0) {
            e.setCancelled(true);
        }*/
        if(!gamePlayer.damage(result)) {
            Stage stage = plugin.getStageManager().getStage(player.getLocation());
            if(stage==null) {
                player.setHealth(0);
                return;
            }
            if(stage.isAllowed(player)) {
                //player.setHealth(20);
                //player.damage(0, e.getEntity());
                gamePlayer.setHealth(gamePlayer.getMaxHealth());
                e.setCancelled(true);
                player.teleport(stage.getSpawn());
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
        double result = UtilCalculator.getDamage(player);
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
            GameEntity gameEntity = plugin.getGameManager().getGameEntity(e.getEntity().getUniqueId());
            if(gameEntity!=null) {
                gameEntity.damage(result);
            }
            e.setDamage(0);
            LivingEntity livingEntity = ((LivingEntity) e.getEntity());
            livingEntity.setHealth(UtilNumber.clampBorder(livingEntity.getHealth(), 0, (livingEntity.getHealth())-result));
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
