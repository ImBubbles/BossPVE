package me.bubbles.bosspve.items.armor.vampire;

import me.bubbles.bosspve.BossPVE;
import me.bubbles.bosspve.game.GamePlayer;
import me.bubbles.bosspve.items.manager.bases.armor.Armor;
import me.bubbles.bosspve.items.manager.bases.armor.ArmorSet;
import me.bubbles.bosspve.utility.UtilNumber;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

public class VampireSet extends ArmorSet {

    public VampireSet(BossPVE plugin) {
        super(plugin);
    }

    @Override
    public void onEvent(Event event) {
        if(event instanceof EntityDamageByEntityEvent) {
            EntityDamageByEntityEvent e = (EntityDamageByEntityEvent) event;
            if(!(e.getDamager() instanceof Player)) {
                return;
            }
            Player player = (Player) e.getDamager();
            if(!wearingFullSet(player,true)) {
                return;
            }
            if(!UtilNumber.rollTheDice(1,100,10)) {
                return;
            }
            Entity entity = e.getEntity();
            if(entity instanceof Player) {
                return;
            }
            if(entity.isDead()) {
                return;
            }
            GamePlayer gamePlayer = plugin.getGameManager().getGamePlayer(player);
            gamePlayer.healPercent(1);
        }
    }

    @Override
    public Armor getBoots() {
        return new VampireBoots(plugin);
    }

    @Override
    public Armor getPants() {
        return new VampirePants(plugin);
    }

    @Override
    public Armor getChestplate() {
        return new VampireChestplate(plugin);
    }

    @Override
    public Armor getHelmet() {
        return new VampireHelmet(plugin);
    }

}
