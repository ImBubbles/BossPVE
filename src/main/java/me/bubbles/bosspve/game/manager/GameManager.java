package me.bubbles.bosspve.game.manager;

import me.bubbles.bosspve.BossPVE;
import me.bubbles.bosspve.game.GameEntity;
import me.bubbles.bosspve.game.GameBase;
import me.bubbles.bosspve.game.GamePlayer;
import me.bubbles.bosspve.stages.Stage;
import net.minecraft.world.entity.Entity;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_21_R1.entity.CraftEntity;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Iterator;
import java.util.UUID;

public class GameManager {

    private HashSet<GamePlayer> gamePlayers;
    private HashSet<GameEntity> gameEntities;
    private BossPVE plugin;

    public GameManager(BossPVE plugin) {
        this.plugin=plugin;
        gamePlayers=new HashSet<>();
        gameEntities=new HashSet<>();
    }

    public GamePlayer getGamePlayer(Player player) {
        GamePlayer result = null;
        for(GamePlayer gamePlayer : gamePlayers) {
            if(gamePlayer.getBukkitPlayer().equals(player)) {
                result=gamePlayer;
                break;
            }
        }
        if(result==null) {
            GamePlayer gamePlayer = new GamePlayer(plugin, player);
            register(gamePlayer);
            result=gamePlayer;
        }
        return result;
    }

    public GamePlayer getGamePlayer(UUID uuid) {
        return getGamePlayer(Bukkit.getPlayer(uuid));
    }

    public GameEntity getGameEntity(Entity entity) {
        GameEntity result = null;
        for(GameEntity gameEntity : gameEntities) {
            if(gameEntity.getEntity().equals(entity)) {
                result=gameEntity;
                break;
            }
        }
        return result;
    }

    public GameEntity getGameEntity(UUID uuid) {
        GameEntity result = null;
        for(GameEntity gameEntity : gameEntities) {
            if(gameEntity.getEntity().getUUID().equals(uuid)) {
                result=gameEntity;
                break;
            }
        }
        return result;
    }

    public void register(GamePlayer gamePlayer) {
        if(gamePlayers.contains(gamePlayer)) {
            return;
        }
        gamePlayers.add(gamePlayer);
    }

    public void register(GameEntity gameEntity) {
        if(gameEntities.contains(gameEntity)) {
            return;
        }
        gameEntities.add(gameEntity);
    }

    public void delete(GameEntity gameEntity) {
        gameEntities.remove(gameEntity);
        CraftEntity entity = gameEntity.getEntity().getBukkitEntity();
        Stage stage = plugin.getStageManager().getStage(entity.getLocation());
        if(stage!=null) {
            stage.onKill(entity.getHandle());
        }
    }

    public void delete(GamePlayer gamePlayer) {
        gamePlayers.remove(gamePlayer);
    }

    public HashSet<GameBase> getAllEntities() {
        HashSet<GameBase> result = new HashSet<>();
        result.addAll(gamePlayers);
        result.addAll(gameEntities);
        return result;
    }

    public HashSet<GamePlayer> getGamePlayers() {
        return gamePlayers;
    }

    public HashSet<GameEntity> getGameEntities() {
        return gameEntities;
    }

    public void clearDead() {
        Iterator<GameEntity> iterator = gameEntities.iterator();
        while(iterator.hasNext()) {
            GameEntity val = iterator.next();
            if(val.getEntity().isAlive()) {
                continue;
            }
            iterator.remove();
        }
    }

}
