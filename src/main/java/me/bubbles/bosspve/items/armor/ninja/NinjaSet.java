package me.bubbles.bosspve.items.armor.ninja;

import me.bubbles.bosspve.BossPVE;
import me.bubbles.bosspve.items.manager.bases.armor.Armor;
import me.bubbles.bosspve.items.manager.bases.armor.ArmorSet;
import me.bubbles.bosspve.utility.UtilNumber;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

public class NinjaSet extends ArmorSet {

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
            entity.setLastDamageCause(new EntityDamageByEntityEvent(player, entity, EntityDamageEvent.DamageCause.CUSTOM, 1000));
        }
    }

    @Override
    public Armor getBoots() {
        return new NinjaBoots();
    }

    @Override
    public Armor getPants() {
        return new NinjaPants();
    }

    @Override
    public Armor getChestplate() {
        return new NinjaChestplate();
    }

    @Override
    public Armor getHelmet() {
        return new NinjaHelmet();
    }

}
