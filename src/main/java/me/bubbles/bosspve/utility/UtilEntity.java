package me.bubbles.bosspve.utility;

import me.bubbles.bosspve.entities.manager.IEntity;
import me.bubbles.bosspve.flags.EntityFlag;
import me.bubbles.bosspve.flags.Flag;
import net.minecraft.world.entity.Entity;

import java.util.HashMap;

public class UtilEntity {

    private IEntity iEntity;

    private double xp;
    private double damage;
    private double money;
    private double maxHealth;

    public UtilEntity(IEntity iEntity) {
        HashMap<EntityFlag, Double> numFlags = new HashMap<>();
        for(Flag<EntityFlag, Double> flag : iEntity.getFlags()) {
            if(flag.getValue() != null) {
                numFlags.put((EntityFlag) flag.getFlag(), flag.getValue());
            }
        }
        this.iEntity=iEntity;
        this.xp=numFlags.getOrDefault(EntityFlag.XP, 1D);
        this.money=numFlags.getOrDefault(EntityFlag.MONEY, 1D);
        this.maxHealth=numFlags.getOrDefault(EntityFlag.MAX_HEALTH, 10D);
        this.damage=numFlags.getOrDefault(EntityFlag.DAMAGE, 1D);
    }

    public double getMaxHealth() {
        return maxHealth;
    }

    public double getMoney() {
        return money;
    }

    public double getDamage() {
        return damage;
    }

    public double getXp() {
        return xp;
    }

}
