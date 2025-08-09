package me.bubbles.bosspve.stages;

import me.bubbles.bosspve.BossPVE;
import me.bubbles.bosspve.entities.manager.EntityBase;
import me.bubbles.bosspve.entities.manager.IEntity;
import me.bubbles.bosspve.game.GameEntity;
import me.bubbles.bosspve.ticker.Timer;
import net.minecraft.world.entity.Entity;
import org.bukkit.Location;

import java.util.logging.Level;

public class StageEntity extends Timer {

    private Stage stage;
    private EntityBase entityBase;
    private Location location;
    private GameEntity gameEntity;

    public StageEntity(Stage stage, EntityBase entityBase, Location location, int ticks) {
        super(ticks);
        this.stage=stage;
        this.entityBase=entityBase;
        this.location=location;
    }

    public Location getSpawnLocation() {
        return location;
    }

    @Override
    public void onComplete() {
        if(stage.allowSpawn()) {
            Entity entity = entityBase.spawn(location);
            stage.spawnEntity(entity);
            gameEntity=new GameEntity(entityBase, entity);
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
