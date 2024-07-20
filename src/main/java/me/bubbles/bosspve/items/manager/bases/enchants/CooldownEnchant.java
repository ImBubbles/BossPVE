package me.bubbles.bosspve.items.manager.bases.enchants;

import me.bubbles.bosspve.ticker.PlayerTimerManager;
import me.bubbles.bosspve.ticker.Timer;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public abstract class CooldownEnchant extends Enchant {

    private final int cooldown;
    private final PlayerTimerManager timerManager;

    public CooldownEnchant(String name, Material material, int maxLevel, int cooldown) {
        super(name, material, maxLevel);
        this.cooldown=cooldown;
        this.timerManager=new PlayerTimerManager();
    }

    public boolean isOnCooldown(Player player) {
        if(!timerManager.contains(player)) {
            timerManager.addTimer(player,new Timer(cooldown));
            return false;
        }
        return timerManager.isTimerActive(player);
    }

    public void restartCooldown(Player player) {
        if(cooldown ==0) {
            return;
        }
        if(!timerManager.contains(player)) {
            timerManager.addTimer(player, new Timer(cooldown));
            return;
        }
        timerManager.restartTimer(player);
    }

    public int getCooldown(Player player) {
        if(timerManager.contains(player)) {
            return timerManager.getTimer(player).getRemainingTicks();
        }
        return -1;
    }

    public void onTick() {
        timerManager.onTick();
    }

}
