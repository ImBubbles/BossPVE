package me.bubbles.bosspve.game;

import me.bubbles.bosspve.entities.manager.IEntity;
import me.bubbles.bosspve.game.manager.GameManager;
import net.minecraft.world.entity.Entity;

public class GameEntity extends GameBase {

    private Entity entity;
    private IEntity iEntity;
    private GameManager gameManager;

    public GameEntity(GameManager gameManager, IEntity iEntity, Entity entity) {
        super(
                iEntity.getUtilEntity().getMaxHealth()
        );
        this.gameManager=gameManager;
        this.entity=entity;
    }

    @Override
    public boolean damage(double x) {
        boolean a = super.damage(x);
        if(!a) {
            entity.kill();
        }
        this.gameManager.delete(this);
        return a;
    }

    public Entity getEntity() {
        return entity;
    }
    public IEntity getiEntity() {
        return iEntity;
    }

}
