package me.bubbles.bosspve.game;

import me.bubbles.bosspve.utility.UtilNumber;

public abstract class GameBase implements IGameEntity {

    double maxHealth;
    double health;

    public GameBase(double maxHealth, double health) {
        this.maxHealth=maxHealth>0 ? maxHealth : 10;
        this.health=health;
    }

    public GameBase(double maxHealth) {
        this.maxHealth=maxHealth>0 ? maxHealth : 10;
        this.health=maxHealth;
    }

    @Override
    public void setMaxHealth(double maxHealth) {
        this.maxHealth = maxHealth;
    }

    @Override
    public boolean setHealth(double health) {
        /*if(!isAlive()) {
            return true;
        }*/
        this.health = health;
        return isAlive();
    }

    @Override
    public double getMaxHealth() {
        return maxHealth;
    }

    @Override
    public double getHealth() {
        return health;
    }

    @Override
    public boolean damage(double x) {
        if(!isAlive()) {
            return false;
        }
        health=UtilNumber.clampBorder(getMaxHealth(), 0, health-x);
        return isAlive();
    }

    @Override
    public boolean isAlive() {
        return health>0;
    }

}
