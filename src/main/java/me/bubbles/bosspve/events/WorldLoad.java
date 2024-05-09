package me.bubbles.bosspve.events;

import me.bubbles.bosspve.BossPVE;
import me.bubbles.bosspve.events.manager.Event;
import org.bukkit.World;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.world.WorldLoadEvent;

public class WorldLoad extends Event {

    public WorldLoad(BossPVE plugin) {
        super(plugin, WorldLoadEvent.class);
    }

    @Override
    public void onEvent(org.bukkit.event.Event event) {
        WorldLoadEvent e = (WorldLoadEvent) event;
        World world = e.getWorld();
        world.getEntities().forEach(entity -> {
            if(!(entity instanceof Player)) {
                LivingEntity livingEntity = (LivingEntity) entity;
                livingEntity.setHealth(0);
            }
        });
    }

}
