package me.bubbles.bosspve.stages;

import me.bubbles.bosspve.BossPVE;
import me.bubbles.bosspve.entities.manager.IEntity;
import me.bubbles.bosspve.ticker.Timer;
import org.bukkit.Location;

public class StageEntity extends Timer {

    private Stage stage;
    private BossPVE plugin;
    private IEntity entity;
    private Location location;

    public StageEntity(Stage stage, IEntity entity, Location location, int ticks) {
        super(stage.plugin,ticks);
        this.stage=stage;
        this.plugin=stage.plugin;
        this.entity=entity;
        this.location=location;
    }

    @Override
    public void onComplete() {
        if(stage.allowSpawn()) {
            stage.spawnEntity(entity.spawn(location));
        }
        restart();
    }

    public StageEntity setEnabled(boolean bool) {
        if(plugin.getTimerManager().containsTimer(this)==bool) {
            return this;
        }
        if(bool) {
            plugin.getTimerManager().addTimer(this);
        } else {
            plugin.getTimerManager().removeTimer(this);
        }
        return this;
    }

}
