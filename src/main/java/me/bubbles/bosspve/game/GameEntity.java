package me.bubbles.bosspve.game;

import me.bubbles.bosspve.entities.manager.IEntity;
import net.minecraft.world.entity.Entity;

public class GameEntity extends GameBase {

    private Entity entity;
    private IEntity iEntity;

    public GameEntity(IEntity iEntity, Entity entity) {
        super(
                iEntity.getCustomEntityData().getMaxHealth()
        );
        this.entity=entity;
        this.iEntity=iEntity;
    }

    public Entity getEntity() {
        return entity;
    }
    public IEntity getiEntity() {
        return iEntity;
    }

}
