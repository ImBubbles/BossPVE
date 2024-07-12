package me.bubbles.bosspve.game;

import me.bubbles.bosspve.entities.manager.IEntity;
import me.bubbles.bosspve.game.manager.GameManager;
import net.minecraft.core.Holder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.Entity;
import org.bukkit.craftbukkit.v1_21_R1.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_21_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.checkerframework.checker.units.qual.C;

public class GameEntity extends GameBase {

    private Entity entity;
    private IEntity iEntity;

    public GameEntity(IEntity iEntity, Entity entity) {
        super(
                iEntity.getUtilEntity().getMaxHealth()
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
