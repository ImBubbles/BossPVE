package me.bubbles.bosspve.items.armor.bee;

import me.bubbles.bosspve.BossPVE;
import me.bubbles.bosspve.game.GamePlayer;
import me.bubbles.bosspve.items.manager.bases.armor.Armor;
import me.bubbles.bosspve.items.manager.bases.armor.ArmorSet;
import me.bubbles.bosspve.utility.UtilNumber;
import me.bubbles.bosspve.utility.messages.PreparedMessages;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDeathEvent;

public class BeeSet extends ArmorSet {

    public BeeSet(BossPVE plugin) {
        super(plugin);
    }

    @Override
    public void onEvent(Event event) {
        if(event instanceof EntityDeathEvent) {
            EntityDeathEvent e = (EntityDeathEvent) event;
            if(e.getEntity().getKiller() == null) {
                return;
            }
            Player player = e.getEntity().getKiller();
            if(!wearingFullSet(player,true)) {
                return;
            }
            if(!UtilNumber.rollTheDice(1,100,1)) {
                return;
            }
            GamePlayer gamePlayer = plugin.getGameManager().getGamePlayer(player);
            gamePlayer.give(100, 0, null, false);
            PreparedMessages.other(gamePlayer, "%prefix% %primary%You received %secondary%100 xp %primary%from your armor!");
        }
    }

    @Override
    public Armor getBoots() {
        return new BeeBoots(plugin);
    }

    @Override
    public Armor getPants() {
        return new BeePants(plugin);
    }

    @Override
    public Armor getChestplate() {
        return new BeeChestplate(plugin);
    }

    @Override
    public Armor getHelmet() {
        return new BeeHelmet(plugin);
    }

}
