package me.bubbles.bosspve.events;

import me.bubbles.bosspve.BossPVE;
import me.bubbles.bosspve.events.manager.Event;
import me.bubbles.bosspve.game.GamePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityRegainHealthEvent;

public class UpdateHealthBar extends Event {

    public UpdateHealthBar(BossPVE plugin) {
        super(plugin, EntityRegainHealthEvent.class);
    }

    @Override
    public void onEvent(org.bukkit.event.Event event) {
        //System.out.println("heal event");
        EntityRegainHealthEvent e = (EntityRegainHealthEvent) event;
        if(!(e.getEntity() instanceof Player)) {
            e.setCancelled(true);
            return;
        }
        //System.out.println("is player");
        // Uncomment below if for some reason needed in the future
        /*if(!e.getRegainReason().equals(EntityRegainHealthEvent.RegainReason.REGEN)) {
            return;
        }*/
        if(!e.getRegainReason().equals(EntityRegainHealthEvent.RegainReason.REGEN)) {
            return;
        }
        e.setCancelled(true);
        //System.out.println("cancelled");
        Player player = (Player) e.getEntity();
        GamePlayer gamePlayer = plugin.getGameManager().getGamePlayer(player);
        if(gamePlayer.getHealth()==gamePlayer.getMaxHealth()) {
            //System.out.println("already nax health");
            return;
        }
        gamePlayer.healPercent(0.3D);
    }

}
