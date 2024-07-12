package me.bubbles.bosspve.events;

import me.bubbles.bosspve.BossPVE;
import me.bubbles.bosspve.events.manager.Event;
import me.bubbles.bosspve.game.GameEntity;
import me.bubbles.bosspve.stages.Stage;
import org.bukkit.craftbukkit.v1_21_R1.entity.CraftEntity;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityRemoveEvent;

public class EntityRemove extends Event {

    public EntityRemove() {
        super(EntityRemoveEvent.class);
    }

    @Override
    public void onEvent(org.bukkit.event.Event event) {
        /*ChunkUnloadEvent e = (ChunkUnloadEvent) event;
        for(Entity entity : e.getChunk().getEntities()) {
            GameEntity gameEntity = plugin.getGameManager().getGameEntity(entity.getUniqueId());
            if(gameEntity!=null) {
                plugin.getGameManager().delete(gameEntity);
            }
            Stage stage = plugin.getStageManager().getStage(entity.getLocation());
            if(stage!=null) {
                stage.onKill(((CraftEntity) entity).getHandle());
            }
        }*/

        /*GameEntity gameEntity = plugin.getGameManager().getGameEntity(e.getEntity().getUniqueId());
        if(gameEntity!=null) {
            plugin.getGameManager().delete(gameEntity);
        }*/

        EntityRemoveEvent e = (EntityRemoveEvent) event;
        if(!(e.getCause().equals(EntityRemoveEvent.Cause.UNLOAD)||e.getCause().equals(EntityRemoveEvent.Cause.DESPAWN)||e.getCause().equals(EntityRemoveEvent.Cause.OUT_OF_WORLD))) {
            return;
        }
        Entity entity = e.getEntity();
        if(!(entity instanceof LivingEntity)) {
            return;
        }
        GameEntity gameEntity = BossPVE.getInstance().getGameManager().getGameEntity(entity.getUniqueId());
        if(gameEntity!=null) {
            BossPVE.getInstance().getGameManager().delete(gameEntity);
        }
        Stage stage = BossPVE.getInstance().getStageManager().getStage(entity.getLocation());
        if(stage!=null) {
            stage.onKill(((CraftEntity) entity).getHandle());
        }
        /*e.getEntity().remove();
        LivingEntity livingEntity = (LivingEntity) entity;
        livingEntity.setHealth(0);*/
    }

}
