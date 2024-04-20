package me.bubbles.bosspve.game;

public interface IGameEntity {

    double getMaxHealth();
    void setMaxHealth(double maxHealth);
    boolean setHealth(double health);
    double getHealth();
    boolean damage(int x);
    boolean isAlive();
    void kill();

}
