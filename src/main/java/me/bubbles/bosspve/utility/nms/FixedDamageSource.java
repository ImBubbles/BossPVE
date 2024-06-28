package me.bubbles.bosspve.utility.nms;

import net.minecraft.core.Holder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;

public class FixedDamageSource extends DamageSource {

    public FixedDamageSource(Holder<DamageType> holder, @Nullable Entity entity, @Nullable Entity entity1, @Nullable Vec3 vec3d) {
        super(holder, entity, entity1, vec3d);
    }

    @Override
    public DamageSource sweep() {
        return this;
    }

    @Override
    public DamageSource melting() {
        return this;
    }

    @Override
    public DamageSource poison() {
        return this;
    }

    @Override
    public boolean isSweep() {
        return false;
    }

    @Override
    public boolean isPoison() {
        return false;
    }

    @Override
    public boolean isCreativePlayer() {
        return super.isCreativePlayer();
    }

    @Override
    public boolean isMelting() {
        return false;
    }

}
