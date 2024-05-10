package me.bubbles.bosspve.events;

import me.bubbles.bosspve.BossPVE;
import me.bubbles.bosspve.events.manager.Event;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_20_R3.entity.CraftLivingEntity;
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
                LivingEntity livingEntity = ((CraftLivingEntity) entity).getHandle();
                livingEntity.remove(Entity.RemovalReason.DISCARDED);
            }
        });
    }

}
