package me.bubbles.bosspve.game;

import org.bukkit.entity.Player;
import org.bukkit.Bukkit;

import java.util.UUID;

public class GamePlayer extends GameEntity {

    private UUID uuid;

    public GamePlayer(UUID uuid) {
        this.uuid=uuid;
    }

    @Override
    public void kill() {
        getPlayer().setHealth(0);
        setHealth(0);
    }

    public UUID getUuid() {
        return uuid;
    }

    private Player getPlayer() {
        return Bukkit.getPlayer(uuid);
    }

}
