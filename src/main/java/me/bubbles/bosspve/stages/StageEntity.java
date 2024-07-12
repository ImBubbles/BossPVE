package me.bubbles.bosspve.stages;

import me.bubbles.bosspve.BossPVE;
import me.bubbles.bosspve.entities.manager.IEntity;
import me.bubbles.bosspve.game.GameEntity;
import me.bubbles.bosspve.ticker.Timer;
import net.minecraft.world.entity.Entity;
import org.bukkit.Location;

import java.util.logging.Level;

public class StageEntity extends Timer {

    private Stage stage;
    private IEntity iEntity;
    private Location location;
    private GameEntity gameEntity;

    public StageEntity(Stage stage, IEntity iEntity, Location location, int ticks) {
        super(ticks);
        this.stage=stage;
        this.iEntity=iEntity;
        this.location=location;
    }

    public Location getSpawnLocation() {
        return location;
    }

    @Override
    public void onComplete() {
        if(stage.allowSpawn()) {
            Entity entity = iEntity.spawn(location);
            stage.spawnEntity(entity);
            gameEntity=new GameEntity(iEntity, entity);
            BossPVE.getInstance().getGameManager().register(gameEntity);
        }
        restart();
    }

    public StageEntity setEnabled(boolean bool) {
        if(BossPVE.getInstance().getTimerManager().containsTimer(this)==bool) {
            return this;
        }
        if(bool) {
            BossPVE.getInstance().getTimerManager().addTimer(this);
        } else {
            BossPVE.getInstance().getTimerManager().removeTimer(this);
        }
        return this;
    }

}
