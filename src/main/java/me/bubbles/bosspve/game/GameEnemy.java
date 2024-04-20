package me.bubbles.bosspve.game;

import me.bubbles.bosspve.entities.manager.IEntity;

public class GameEnemy extends GamePlayer {

    private IEntity entity;

    public GameEnemy(double maxHealth, double health) {
        super(maxHealth, health);
    }

    public GameEnemy(double maxHealth) {
        super(maxHealth);
    }

    @Override
    public void kill() {

    }

    public IEntity getEntity() {
        return entity;
    }

}
