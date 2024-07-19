package me.bubbles.bosspve.items.armor.cyclone;

import me.bubbles.bosspve.BossPVE;
import me.bubbles.bosspve.game.GamePlayer;
import me.bubbles.bosspve.items.manager.bases.armor.Armor;
import me.bubbles.bosspve.items.manager.bases.armor.ArmorSet;
import me.bubbles.bosspve.ticker.Timer;
import me.bubbles.bosspve.utility.UtilNumber;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.HashSet;

public class CycloneSet extends ArmorSet {

    private final Timer timer;

    public CycloneSet() {
        timer=new Timer(20);
        BossPVE.getInstance().getTimerManager().addTimer(timer);
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
            GamePlayer gamePlayer = BossPVE.getInstance().getGameManager().getGamePlayer(player);
            gamePlayer.healPercent(1);
        }
    }

    @Override
    public void onTick() {
        if(timer.isActive()) {
            return;
        }
        HashSet<Player> players = getPlayersWearingFullSet(true);
        players.forEach(player -> {
            PotionEffect slowFall = new PotionEffect(PotionEffectType.SLOW_FALLING,40,0);
            player.addPotionEffect(slowFall);
        });
        timer.restart();
    }

    @Override
    public Armor getBoots() {
        return new CycloneBoots();
    }

    @Override
    public Armor getPants() {
        return new CyclonePants();
    }

    @Override
    public Armor getChestplate() {
        return new CycloneChestplate();
    }

    @Override
    public Armor getHelmet() {
        return new CycloneHelmet();
    }

}
