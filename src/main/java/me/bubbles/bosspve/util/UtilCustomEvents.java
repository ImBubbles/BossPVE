package me.bubbles.bosspve.util;

import me.bubbles.bosspve.BossPVE;
import me.bubbles.bosspve.entities.manager.IEntity;
import me.bubbles.bosspve.game.GameEntity;
import me.bubbles.bosspve.game.GamePlayer;
import me.bubbles.bosspve.items.manager.bases.enchants.Enchant;
import me.bubbles.bosspve.util.messages.UtilPreparedMessage;
import net.minecraft.world.entity.Entity;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_20_R3.CraftServer;
import org.bukkit.craftbukkit.v1_20_R3.entity.CraftEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

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
        e.getDrops().clear();
        ItemStack itemStack = player.getInventory().getItemInMainHand();
        UtilItemStack uis = new UtilItemStack(plugin,itemStack);
        List<ItemStack> drops = entity.getDrops();
        if(!drops.isEmpty()) {
            e.getDrops().addAll(drops);
            if(itemStack.hasItemMeta()) {
                boolean telepathy=false;
                for(Enchant enchant : uis.getCustomEnchants()) {
                    if(enchant.getName().equalsIgnoreCase("telepathy")) {
                        telepathy=true;
                    }
                }
                if(telepathy) {
                    if(player.getInventory().firstEmpty()!=-1) {
                        e.getDrops().clear();
                        drops.forEach(drop -> player.getInventory().addItem(drop));
                    }
                }
            }
        }
        GameEntity gameEntity = plugin.getGameManager().getGameEntity(e.getEntity().getUniqueId());
        plugin.getGameManager().delete(gameEntity);
        int xp=(int) UtilCalculator.getXp(player, entity);
        double money=UtilCalculator.getMoney(player, entity);
        UtilUserData uud = UtilUserData.getUtilUserData(player.getUniqueId());
        uud.setXp(uud.getXp()+xp);
        plugin.getEconomy().depositPlayer(player,money);
        UtilPreparedMessage.kill(player, entity, xp, (int) (money+0.5D));
    }

    public void customEntityDamageByEntityEvent(IEntity entity) { // mob v player
        if(!(event instanceof EntityDamageByEntityEvent)) {
            return;
        }
        EntityDamageByEntityEvent e = (EntityDamageByEntityEvent) event;
        if(!(e.getEntity() instanceof Player)) {
            return;
        }

        if(!entity.hasSameTagAs(e.getDamager())) {
            return;
        }
        double result = entity.getUtilEntity().damage;;
        Player player = ((Player) e.getEntity()).getPlayer();
        if(player==null) {
            return;
        }
        result=UtilNumber.clampBorder(result, 0, result-UtilCalculator.getProtection(player));
        GamePlayer gamePlayer = plugin.getGameManager().getGamePlayer(player);
        if(gamePlayer.damage(result)) {
            e.setDamage(0);
            return;
        } else {
            player.setHealth(0);
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
        ItemStack usedItem = player.getInventory().getItemInMainHand();
        if(usedItem==null) {
            return;
        }
        if(usedItem.getType().equals(Material.AIR)) {
            return;
        }
        double result = UtilCalculator.getDamage(player);
        if(e.getEntity() instanceof Player) {
            result=UtilNumber.clampBorder(result, 0, result-UtilCalculator.getProtection((Player) e.getEntity()));
            Player player2 = ((Player) e.getEntity());
            GamePlayer gamePlayer = plugin.getGameManager().getGamePlayer(player2);
            if(gamePlayer.damage(result)) {
                e.setDamage(0);
            } else {
                player2.setHealth(0);
            }
        } else {
            plugin.getGameManager().getGameEntity(e.getEntity().getUniqueId()).damage(result);
        }
    }

}
