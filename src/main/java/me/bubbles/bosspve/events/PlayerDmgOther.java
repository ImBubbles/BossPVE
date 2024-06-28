package me.bubbles.bosspve.events;

import me.bubbles.bosspve.BossPVE;
import me.bubbles.bosspve.events.manager.Event;
import me.bubbles.bosspve.utility.UtilCustomEvents;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class PlayerDmgOther extends Event {

    public PlayerDmgOther(BossPVE plugin) {
        super(plugin, EntityDamageByEntityEvent.class);
    }

    @Override
    public void onEvent(org.bukkit.event.Event event) {
        EntityDamageByEntityEvent e = (EntityDamageByEntityEvent) event;
        if(!(e.getEntity() instanceof LivingEntity)) {
            return;
        }
        new UtilCustomEvents(plugin,event).customEntityDamageByEntityEvent();
    }

}