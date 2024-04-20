package me.bubbles.bosspve.game.manager;

import me.bubbles.bosspve.BossPVE;
import me.bubbles.bosspve.game.GameEnemy;
import me.bubbles.bosspve.game.GameEntity;
import me.bubbles.bosspve.game.GamePlayer;

import java.util.HashSet;
import java.util.UUID;

public class GameManager {

    private HashSet<GamePlayer> gamePlayers;
    private HashSet<GameEnemy> gameEnemies;
    private BossPVE plugin;

    public GameManager(BossPVE plugin) {
        this.plugin=plugin;
        gamePlayers=new HashSet<>();
        gameEnemies=new HashSet<>();
    }

    public GamePlayer getGamePlayer(UUID uuid) {
        GamePlayer result = null;
        for(GamePlayer gamePlayer : gamePlayers) {
            if(gamePlayer.getUuid().equals(uuid)) {
                result=gamePlayer;
                break;
            }
        }
        if(result==null) {
            GamePlayer gamePlayer = new GamePlayer(uuid);
            gamePlayers.add(gamePlayer);
            result=gamePlayer;
        }
        return result;
    }

    public HashSet<GameEntity> getAllEntities() {
        HashSet<GameEntity> result = new HashSet<>();
        result.addAll(gamePlayers);
        result.addAll(gameEnemies);
        return result;
    }

    public HashSet<GamePlayer> getGamePlayers() {
        return gamePlayers;
    }

    public HashSet<GameEnemy> getGameEnemies() {
        return gameEnemies;
    }

}
