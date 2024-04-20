package me.bubbles.bosspve.events;

import me.bubbles.bosspve.BossPVE;
import me.bubbles.bosspve.events.manager.Event;
import me.bubbles.bosspve.stages.Stage;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class AntiDeathRespawn extends Event {

    public AntiDeathRespawn(BossPVE plugin) {
        super(plugin, EntityDamageByEntityEvent.class);
    }

    @Override
    public void onEvent(org.bukkit.event.Event event) {
        EntityDamageByEntityEvent e = (EntityDamageByEntityEvent) event;
        if(!(e.getEntity() instanceof Player)) {
            return;
        }
        Player player = ((Player) e.getEntity()).getPlayer();
        if(player==null) {
            return;
        }
        if(player.getHealth()!=0&&player.getHealth()-e.getFinalDamage()>0) {
            return;
        }
        Stage stage = plugin.getStageManager().getStage(player.getLocation());
        if(stage==null) {
            return;
        }
        if(!stage.isAllowed(player)) {
            return;
        }
        player.setHealth(20);
        e.setDamage(0);
        player.teleport(stage.getSpawn());
        player.setFireTicks(0);
        e.setCancelled(true);
        player.playSound(player, Sound.ENTITY_VILLAGER_HURT, 1, 1);
    }

}
