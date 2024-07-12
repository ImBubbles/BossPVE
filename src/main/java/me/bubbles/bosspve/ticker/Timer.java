package me.bubbles.bosspve.ticker;

import me.bubbles.bosspve.BossPVE;
import me.bubbles.bosspve.utility.UtilTime;

public class Timer {

    private int cap;
    private int ticks;
    private long lastCall;

    public Timer(int cap) {
        this.cap=cap;
        this.ticks=0;
        this.lastCall=UtilTime.getEpochTimestamp();
    }

    public void onTick() {
        ticks=clamp(ticks+1);
        if(ticks==cap) {
            onComplete();
        }
    }

    public void onComplete() {

    }

    public int getTicks() {
        lastCall=UtilTime.getEpochTimestamp();
        return ticks;
    }

    public void restart() {
        lastCall=UtilTime.getEpochTimestamp();
        this.ticks=0;
    }

    public int getRemainingTicks() {
        lastCall=UtilTime.getEpochTimestamp();
        return cap-ticks;
    }

    public boolean isActive() {
        lastCall=UtilTime.getEpochTimestamp();
        return ticks != cap;
    }

    private int clamp(int result) {
        return Math.min(result, cap);
    }

    public long getLastCall() {
        return lastCall;
    }

}
