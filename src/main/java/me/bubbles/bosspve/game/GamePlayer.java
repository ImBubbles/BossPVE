package me.bubbles.bosspve.game;

import me.bubbles.bosspve.BossPVE;
import me.bubbles.bosspve.entities.manager.IEntity;
import me.bubbles.bosspve.utility.UtilCalculator;
import me.bubbles.bosspve.utility.UtilNumber;
import me.bubbles.bosspve.utility.UtilUserData;
import me.bubbles.bosspve.utility.messages.PreparedMessages;
import org.bukkit.entity.Player;

import java.util.UUID;

public class GamePlayer extends GameBase {

    private Player player;
    private BossPVE plugin;
    private UtilUserData cache;

    public GamePlayer(BossPVE plugin, Player player) {
        super(UtilCalculator.getMaxHealth(player));
        this.player=player;
        this.plugin=plugin;
        updateCache();
    }

    public void updateHealthBar() {
        if(player.getHealth()==0D) {
            return;
        }
        double percent = health/maxHealth;
        if(20D*percent<0.5) {
            player.setHealth(1);
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

    public void give(double xp, double money, IEntity cause, boolean message) {
        if(xp!=0) {
            UtilUserData uud = cache;
            uud.setXp((int) (uud.getXp()+xp));
        }
        if(money!=0) {
            plugin.getEconomy().depositPlayer(player,money);
        }
        if(message) {
            if(cause!=null) {
                PreparedMessages.kill(plugin.getGameManager().getGamePlayer(player), cause, (int) xp, money);
            } else {
                PreparedMessages.give(plugin.getGameManager().getGamePlayer(player), (int) xp, money);
            }
        }
    }

}
