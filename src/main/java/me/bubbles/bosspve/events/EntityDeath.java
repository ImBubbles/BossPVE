package me.bubbles.bosspve.events;

import me.bubbles.bosspve.BossPVE;
import me.bubbles.bosspve.events.manager.Event;
import me.bubbles.bosspve.game.GameEntity;
import org.bukkit.event.entity.EntityDeathEvent;

public class EntityDeath extends Event {

    public EntityDeath() {
        super(EntityDeathEvent.class);
    }

    @Override
    public void onEvent(org.bukkit.event.Event event) {
        EntityDeathEvent e = (EntityDeathEvent) event;
        GameEntity gameEntity = BossPVE.getInstance().getGameManager().getGameEntity(e.getEntity().getUniqueId());
        if(gameEntity!=null) {
            BossPVE.getInstance().getGameManager().delete(gameEntity);
        }
    }

}
