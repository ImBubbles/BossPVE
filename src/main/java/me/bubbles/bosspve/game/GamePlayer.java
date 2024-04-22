package me.bubbles.bosspve.game;

import me.bubbles.bosspve.util.UtilCalculator;
import org.bukkit.entity.Player;
import org.bukkit.Bukkit;

import java.util.UUID;

public class GamePlayer extends GameBase {

    private Player player;

    public GamePlayer(Player player) {
        super(UtilCalculator.getMaxHealth(player));
        this.player=player;
    }

    public void updateHealthBar() {
        if(player.getHealth()==0) {
            return;
        }
        int percent = (int) ((health/maxHealth)+0.5D);
        player.setHealth(percent);
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
            player.setHealth(0);
            setHealth(maxHealth);
        }
        updateHealthBar();
        return a;
    }
}
