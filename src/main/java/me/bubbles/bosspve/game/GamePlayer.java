package me.bubbles.bosspve.game;

import me.bubbles.bosspve.util.UtilCalculator;
import me.bubbles.bosspve.util.UtilNumber;
import me.bubbles.bosspve.util.UtilTextComponent;
import me.bubbles.bosspve.util.UtilUserData;
import net.md_5.bungee.api.ChatMessageType;
import org.bukkit.entity.Player;

import java.util.UUID;

public class GamePlayer extends GameBase {

    private Player player;
    private UtilUserData cache;

    public GamePlayer(Player player) {
        super(UtilCalculator.getMaxHealth(player));
        this.player=player;
        updateCache();
    }

    public void updateHealthBar() {
        if(player.getHealth()==0) {
            return;
        }
        double percent = health/maxHealth;
        if((int) 20*percent<0.5) {
            return;
        }
        player.setHealth(20*percent);
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

    public void updateCache() {
        this.cache = UtilUserData.getUtilUserData(player.getUniqueId());
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
        if(health-x>0) {
            updateHealthBar();
        }
        return a;
    }

    public boolean heal(double x) {
        boolean a = isAlive();
        if(!a) {
            return false;
        }
        health=UtilNumber.clampBorder(maxHealth, 0, health+x);
        updateHealthBar();
        return true;
    }

    public boolean healPercent(double x) {
        double result = UtilNumber.clampBorder(1, 0.1, x);
        result = maxHealth*result;
        return heal(result);
    }

}
