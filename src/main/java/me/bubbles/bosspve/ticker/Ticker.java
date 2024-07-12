package me.bubbles.bosspve.ticker;

import me.bubbles.bosspve.BossPVE;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitScheduler;

public class Ticker {

    private boolean enabled;
    private Runnable onTick;

    public Ticker(Runnable onTick) {
        this(onTick, false);
    }

    public Ticker(Runnable onTick, boolean enabled) {
        this.onTick=onTick;
        setEnabled(enabled);
    }

    private void tick() {
        if(enabled) {
            onTick.run();
            scheduleNextTick();
        }
    }

    private void scheduleNextTick() {
        BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
        scheduler.scheduleSyncDelayedTask(BossPVE.getInstance(), this::tick, 1);
    }

    public Ticker toggle() {
        enabled=!enabled;
        if(enabled) {
            tick();
        }
        return this;
    }

    public Ticker setEnabled(boolean bool) {
        if(enabled==bool) {
            return this;
        }
        enabled=bool;
        if(enabled) {
            tick();
        }
        return this;
    }

    public boolean isEnabled() {
        return enabled;
    }

}