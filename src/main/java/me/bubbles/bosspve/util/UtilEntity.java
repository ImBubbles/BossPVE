package me.bubbles.bosspve.util;

import me.bubbles.bosspve.entities.manager.IEntity;
import me.bubbles.bosspve.flags.EntityFlag;
import me.bubbles.bosspve.flags.Flag;

import java.util.HashMap;

public class UtilEntity {

    double xp;
    double damage;
    double money;
    double maxHealth;

    public UtilEntity(IEntity iEntity) {
        HashMap<EntityFlag, Double> numFlags = new HashMap<>();
        for(Flag flag : iEntity.getFlags()) {
            if(flag.getValue() instanceof Double) {
                numFlags.put((EntityFlag) flag.getFlag(), (double) flag.getValue());
            }
        }
        this.xp=numFlags.getOrDefault(EntityFlag.XP, 1D);
        this.money=numFlags.getOrDefault(EntityFlag.MONEY, 1D);
        this.maxHealth=numFlags.getOrDefault(EntityFlag.MAX_HEALTH, 10D);
        this.damage=numFlags.getOrDefault(EntityFlag.DAMAGE, 10D);
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
