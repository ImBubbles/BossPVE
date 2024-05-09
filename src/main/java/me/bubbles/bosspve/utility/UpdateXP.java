package me.bubbles.bosspve.utility;

import me.bubbles.bosspve.BossPVE;
import me.bubbles.bosspve.database.databases.XpDB;
import me.bubbles.bosspve.game.GamePlayer;
import me.bubbles.bosspve.ticker.Timer;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class UpdateXP extends Timer {

    private BossPVE plugin;

    public UpdateXP(BossPVE plugin) {
        super(plugin,40); // update every 2 seconds
        this.plugin=plugin;
    }

    @Override
    public void onComplete() {
        for(Player player : Bukkit.getOnlinePlayers()) {
            GamePlayer gamePlayer = plugin.getGameManager().getGamePlayer(player);
            int xp = gamePlayer.getCache().getXp();
            int level = UtilNumber.xpToLevel(xp);
            if(player.getLevel()<level) {
                player.playSound(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);
            }
            player.setLevel(level);
            float result = getPercentComplete(xp, level);
            if(result<0.0||result>=1.0) {
                continue;
            }
            player.setExp(result);
        }
        restart();
    }

    private float getPercentComplete(int xp, int level) { // get the percent complete

        float nextLevel = level+1;
        float xpRequirement = nextLevel*nextLevel*10;

        return xp/xpRequirement;

    }

}