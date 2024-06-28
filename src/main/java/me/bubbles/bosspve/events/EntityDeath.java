package me.bubbles.bosspve.events;

import me.bubbles.bosspve.BossPVE;
import me.bubbles.bosspve.events.manager.Event;
import me.bubbles.bosspve.game.GameEntity;
import org.bukkit.event.entity.EntityDeathEvent;

public class EntityDeath extends Event {

    public EntityDeath(BossPVE plugin) {
        super(plugin, EntityDeathEvent.class);
    }

    @Override
    public void onEvent(org.bukkit.event.Event event) {
        EntityDeathEvent e = (EntityDeathEvent) event;
        GameEntity gameEntity = plugin.getGameManager().getGameEntity(e.getEntity().getUniqueId());
        if(gameEntity!=null) {
            plugin.getGameManager().delete(gameEntity);
        }
    }

}
