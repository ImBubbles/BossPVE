package me.bubbles.bosspve.utility;

import me.bubbles.bosspve.BossPVE;
import me.bubbles.bosspve.database.databases.XpDB;
import me.bubbles.bosspve.game.GamePlayer;
import me.bubbles.bosspve.settings.Settings;
import me.bubbles.bosspve.stages.Stage;
import me.bubbles.bosspve.ticker.Timer;
import me.bubbles.bosspve.utility.string.UtilString;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class UpdateXP extends Timer {

    public UpdateXP() {
        super(40); // update every 2 seconds
    }

    @Override
    public void onComplete() {
        for(Player player : Bukkit.getOnlinePlayers()) {
            GamePlayer gamePlayer = BossPVE.getInstance().getGameManager().getGamePlayer(player);
            int xp = gamePlayer.getCache().getXp();
            int level = UtilNumber.xpToLevel(xp);
            if(player.getLevel()<level) {
                Stage stage = BossPVE.getInstance().getStageManager().getStage(level);
                if(stage!=null) {
                    if((Boolean) Settings.NEXTSTAGE_MESSAGES.getOption(gamePlayer.getCache().getOrDefault(Settings.NEXTSTAGE_MESSAGES))) {
                        player.playSound(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);
                        player.sendTitle(UtilString.colorFillPlaceholders("&a&lStage Unlocked"), UtilString.colorFillPlaceholders("&aStage "+stage.getLevelRequirement()+" unlocked"), 5, 60, 5);
                    }
                } else {
                    if((Boolean) Settings.LEVELUP_MESSAGES.getOption(gamePlayer.getCache().getOrDefault(Settings.LEVELUP_MESSAGES))) {
                        player.playSound(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);
                        player.sendTitle(UtilString.colorFillPlaceholders("&a&lLevel Up"), UtilString.colorFillPlaceholders("&aLevel "+level), 5, 40, 5);
                    }
                }
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
        float lastXpRequirement = level*level*10;

        return (xp-lastXpRequirement)/(xpRequirement-lastXpRequirement);

    }

}