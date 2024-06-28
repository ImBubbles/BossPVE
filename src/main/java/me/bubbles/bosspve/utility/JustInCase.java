package me.bubbles.bosspve.utility;

import me.bubbles.bosspve.BossPVE;
import me.bubbles.bosspve.ticker.Timer;

public class JustInCase extends Timer {

    private BossPVE plugin;

    public JustInCase(BossPVE plugin) {
        super(plugin,144000); // update every 2 seconds
        this.plugin=plugin;
    }

    @Override
    public void onComplete() {
        plugin.saveUserData();
        plugin.getGameManager().clearDead();
        restart();
    }

}