package me.bubbles.bosspve.ticker;

import me.bubbles.bosspve.BossPVE;
import me.bubbles.bosspve.utility.UtilTime;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class PlayerTimerManager extends Timer {

    private HashMap<Player, Timer> timers;
    private long lastClear;

    public PlayerTimerManager() {
        super(3600*20);
        timers=new HashMap<>();
        lastClear= UtilTime.getEpochTimestamp();
    }

    @Override
    public void onTick() {
        super.onTick();
        timers.forEach((player, timer) -> timer.onTick());
    }

    @Override
    public void onComplete() {
        if(lastClear<=UtilTime.getEpochTimestamp()-3600) {
            HashMap<Player, Timer> copy = new HashMap<>(timers);
            copy.forEach(((player, timer) -> {
                if(timer.getLastCall()<=UtilTime.getEpochTimestamp()-1800) {
                    removeTimer(player);
                }
            }));
        }
        restart();
    }

    public Timer addTimer(Player player, Timer timer) {
        return timers.put(player, timer);
    }

    public boolean contains(Player player) {
        return timers.containsKey(player);
    }

    public void removeTimer(Player player) {
        timers.remove(player);
    }

    public void restartTimer(Player player) {
        timers.get(player).restart();
    }

    public boolean isTimerActive(Player player) {
        return getTimer(player).isActive();
    }

    public Timer getTimer(Player player) {
        return timers.get(player);
    }

}
