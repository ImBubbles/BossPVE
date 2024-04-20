package me.bubbles.bosspve.events;

import me.bubbles.bosspve.BossPVE;
import me.bubbles.bosspve.events.manager.Event;
import org.bukkit.event.entity.CreatureSpawnEvent;

public class PreventSpawning extends Event {

    public PreventSpawning(BossPVE plugin) {
        super(plugin, CreatureSpawnEvent.class);
    }

    @Override
    public void onEvent(org.bukkit.event.Event event) {
        CreatureSpawnEvent e = (CreatureSpawnEvent) event;
        if(e.getSpawnReason().equals(CreatureSpawnEvent.SpawnReason.CUSTOM)||
                e.getSpawnReason().equals(CreatureSpawnEvent.SpawnReason.DISPENSE_EGG)||
                e.getSpawnReason().equals(CreatureSpawnEvent.SpawnReason.COMMAND)||
                e.getSpawnReason().equals(CreatureSpawnEvent.SpawnReason.SPAWNER_EGG)) {
            return;
        }
        e.setCancelled(true);
    }

}
