package me.bubbles.bosspve.ticker;

import me.bubbles.bosspve.BossPVE;

public class Timer {

    private int cap;
    private int ticks;
    private long lastCall;
    public BossPVE plugin;

    public Timer(BossPVE plugin, int cap) {
        this.cap=cap;
        this.ticks=0;
        this.plugin=plugin;
        this.lastCall=plugin.getEpochTimestamp();
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
        lastCall=plugin.getEpochTimestamp();
        return ticks;
    }

    public void restart() {
        lastCall=plugin.getEpochTimestamp();
        this.ticks=0;
    }

    public int getRemainingTicks() {
        lastCall=plugin.getEpochTimestamp();
        return cap-ticks;
    }

    public boolean isActive() {
        lastCall=plugin.getEpochTimestamp();
        return ticks != cap;
    }

    private int clamp(int result) {
        return Math.min(result, cap);
    }

    public long getLastCall() {
        return lastCall;
    }

}
