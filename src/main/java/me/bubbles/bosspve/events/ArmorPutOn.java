package me.bubbles.bosspve.events;

import me.bubbles.bosspve.BossPVE;
import me.bubbles.bosspve.events.manager.Event;
import me.bubbles.bosspve.game.GamePlayer;
import me.bubbles.bosspve.utility.UtilCalculator;
import me.bubbles.bosspve.utility.UtilUserData;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;

public class ArmorPutOn extends Event {

    public ArmorPutOn(BossPVE plugin) {
        super(plugin, InventoryClickEvent.class);
    }

    @Override
    public void onEvent(org.bukkit.event.Event event) {
        InventoryClickEvent e = (InventoryClickEvent) event;
        if(e.getClickedInventory()==null) {
            return;
        }
        if(!(e.getClickedInventory().getHolder() instanceof Player)) {
            return;
        }
        if(!e.getSlotType().equals(InventoryType.SlotType.ARMOR)) {
            return;
        }
        Player player = (Player) e.getClickedInventory().getHolder();
        GamePlayer gamePlayer = plugin.getGameManager().getGamePlayer(player);
        gamePlayer.setMaxHealth(UtilCalculator.getMaxHealth(player));
    }

}
