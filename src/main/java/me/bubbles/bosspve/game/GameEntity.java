package me.bubbles.bosspve.game;

import me.bubbles.bosspve.entities.manager.EntityBase;
import me.bubbles.bosspve.entities.manager.IEntity;
import net.minecraft.world.entity.Entity;

public class GameEntity extends GameBase {

    private Entity entity;
    private EntityBase entityBase;

    public GameEntity(EntityBase entityBase, Entity entity) {
        super(
                entityBase.getCustomEntityData().getMaxHealth()
        );
        this.entity=entity;
        this.entityBase=entityBase;
    }

    public Entity getEntity() {
        return entity;
    }
    public IEntity getiEntity() {
        return entityBase;
    }

}
