package me.bubbles.bosspve.stages;

import me.bubbles.bosspve.BossPVE;
import me.bubbles.bosspve.entities.manager.IEntity;
import me.bubbles.bosspve.game.GameEntity;
import me.bubbles.bosspve.ticker.Timer;
import net.minecraft.world.entity.Entity;
import org.bukkit.Location;

public class StageEntity extends Timer {

    private Stage stage;
    private BossPVE plugin;
    private IEntity iEntity;
    private Location location;
    private GameEntity gameEntity;

    public StageEntity(Stage stage, IEntity iEntity, Location location, int ticks) {
        super(stage.plugin,ticks);
        this.stage=stage;
        this.plugin=stage.plugin;
        this.iEntity=iEntity;
        this.location=location;
    }

    @Override
    public void onComplete() {
        if(stage.allowSpawn()) {
            Entity entity = iEntity.spawn(location);
            stage.spawnEntity(entity);
            gameEntity=new GameEntity(plugin.getGameManager(), iEntity, entity);
            plugin.getGameManager().register(gameEntity);
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
