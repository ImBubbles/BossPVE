package me.bubbles.bosspve.events;

import me.bubbles.bosspve.BossPVE;
import me.bubbles.bosspve.events.manager.Event;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_21_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_21_R1.entity.CraftLivingEntity;
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
        ((CraftWorld) world).getHandle().getAllEntities().forEach(entity -> {
            /*if(entity instanceof LivingEntity) {
                if(!(entity instanceof ServerPlayer)) {
                    entity.remove(Entity.RemovalReason.DISCARDED);
                }
            }*/
            entity.remove(Entity.RemovalReason.DISCARDED);
        });
        /*world.getEntities().forEach(entity -> {
            if(!(entity instanceof Player)) {
                LivingEntity livingEntity = ((CraftLivingEntity) entity).getHandle();
                livingEntity.remove(Entity.RemovalReason.DISCARDED);
            }
        });*/
    }

}
