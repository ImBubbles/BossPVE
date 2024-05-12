package me.bubbles.bosspve.items.armor.volcanic;

import me.bubbles.bosspve.BossPVE;
import me.bubbles.bosspve.items.manager.bases.armor.Armor;
import me.bubbles.bosspve.items.manager.bases.armor.ArmorSet;
import me.bubbles.bosspve.ticker.Timer;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.HashSet;

public class VolcanicSet extends ArmorSet {

    private Timer timer;

    public VolcanicSet(BossPVE plugin) {
        super(plugin);
        timer=new Timer(plugin,20);
        plugin.getTimerManager().addTimer(timer);
    }

    @Override
    public void onTick() {
        if(timer.isActive()) {
            return;
        }
        HashSet<Player> players = getPlayersWearingFullSet(true);
        players.forEach(player -> {
            PotionEffect fireRes = new PotionEffect(PotionEffectType.FIRE_RESISTANCE,40,0);
            player.addPotionEffect(fireRes);
        });
        timer.restart();
    }

    @Override
    public Armor getBoots() {
        return new VolcanicBoots(plugin);
    }

    @Override
    public Armor getPants() {
        return new VolcanicPants(plugin);
    }

    @Override
    public Armor getChestplate() {
        return new VolcanicChestplate(plugin);
    }

    @Override
    public Armor getHelmet() {
        return new VolcanicHelmet(plugin);
    }

}
