package me.bubbles.bosspve.ticker;

import me.bubbles.bosspve.BossPVE;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitScheduler;

public class Ticker {

    private boolean enabled;
    private BossPVE plugin;

    public Ticker(BossPVE plugin) {
        this.plugin=plugin;
    }

    private void tick() {
        if(enabled) {
            plugin.onTick();
            scheduleNextTick();
        }
    }

    private void scheduleNextTick() {
        BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
        scheduler.scheduleSyncDelayedTask(plugin, this::tick, 1);
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