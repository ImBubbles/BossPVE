package me.bubbles.bosspve.utility;

import me.bubbles.bosspve.BossPVE;
import me.bubbles.bosspve.ticker.Timer;
import me.bubbles.bosspve.utility.string.UtilString;
import org.bukkit.Bukkit;

public class JustInCase extends Timer {

    public JustInCase() {
        super(144000); // update every 2 seconds
    }

    @Override
    public void onComplete() {
        Bukkit.broadcastMessage(UtilString.colorFillPlaceholders("%prefix% %primary%Saving cached player data, server may lag."));
        BossPVE.getInstance().saveUserData();
        BossPVE.getInstance().getGameManager().clearDead();
        restart();
    }

}