package me.bubbles.bosspve.game.manager;

import me.bubbles.bosspve.BossPVE;
import me.bubbles.bosspve.game.GameEntity;
import me.bubbles.bosspve.game.GameBase;
import me.bubbles.bosspve.game.GamePlayer;
import net.minecraft.world.entity.Entity;
import org.bukkit.entity.Player;

import java.util.HashSet;
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
            GamePlayer gamePlayer = new GamePlayer(player);
            register(gamePlayer);
            result=gamePlayer;
        }
        return result;
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
        gamePlayers.add(gamePlayer);
    }

    public void register(GameEntity gameEntity) {
        gameEntities.add(gameEntity);
    }

    public void delete(GameEntity gameEntity) {
        gameEntities.remove(gameEntity);
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

}
