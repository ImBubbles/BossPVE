package me.bubbles.bosspve.game;

import me.bubbles.bosspve.entities.manager.IEntity;
import me.bubbles.bosspve.game.manager.GameManager;
import net.minecraft.core.Holder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.Entity;
import org.bukkit.craftbukkit.v1_20_R3.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_20_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.checkerframework.checker.units.qual.C;

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
        this.iEntity=iEntity;
    }

    @Override
    public boolean damage(double x) {
        boolean a = super.damage(x);
        if (!a) {
            this.gameManager.delete(this);
        }
        return a;
    }

    public Entity getEntity() {
        return entity;
    }
    public IEntity getiEntity() {
        return iEntity;
    }

}
