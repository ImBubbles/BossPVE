package me.bubbles.bosspve.events;

import me.bubbles.bosspve.events.manager.Event;
import me.bubbles.bosspve.utility.CustomEntityEventHandler;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class PlayerDmgOther extends Event {

    public PlayerDmgOther() {
        super(EntityDamageByEntityEvent.class);
    }

    @Override
    public void onEvent(org.bukkit.event.Event event) {
        EntityDamageByEntityEvent e = (EntityDamageByEntityEvent) event;
        if(!(e.getEntity() instanceof LivingEntity)) {
            return;
        }
        //new CustomEntityEventHandler(event).entityDamageByEntityEvent();
        CustomEntityEventHandler.entityDamageByEntityEvent(event);
    }

}