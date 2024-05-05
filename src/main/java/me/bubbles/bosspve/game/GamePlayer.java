package me.bubbles.bosspve.game;

import me.bubbles.bosspve.util.UtilCalculator;
import me.bubbles.bosspve.util.UtilUserData;
import org.bukkit.entity.Player;
import org.bukkit.Bukkit;

import java.util.UUID;

public class GamePlayer extends GameBase {

    private Player player;
    private UtilUserData cache;

    public GamePlayer(Player player) {
        super(UtilCalculator.getMaxHealth(player));
        this.player=player;
        updateCache(UtilUserData.getUtilUserData(player.getUniqueId()));
    }

    public void updateHealthBar() {
        if(player.getHealth()==0) {
            return;
        }
        int percent = (int) ((health/maxHealth)+0.5D);
        player.setHealth(percent);
    }

    @Override
    public boolean setHealth(double health) {
        boolean bool = super.setHealth(health);
        updateHealthBar();
        return bool;
    }

    public void updateCache(UtilUserData uud) {
        this.cache=uud;
    }

    public UtilUserData getCache() {
        return cache;
    }

    public UUID getUuid() {
        return player.getUniqueId();
    }

    public Player getBukkitPlayer() {
        return player;
    }

    @Override
    public boolean damage(double x) {
        boolean a = super.damage(x);
        if(!a) {
            setHealth(maxHealth);
        }
        updateHealthBar();
        return a;
    }
}
