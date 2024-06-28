package me.bubbles.bosspve.events;

import me.bubbles.bosspve.BossPVE;
import me.bubbles.bosspve.events.manager.Event;
import me.bubbles.bosspve.game.GamePlayer;
import me.bubbles.bosspve.utility.UtilCalculator;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class ArmorClickOn extends Event {

    public ArmorClickOn(BossPVE plugin) {
        super(plugin, PlayerInteractEvent.class);
    }

    @Override
    public void onEvent(org.bukkit.event.Event event) {
        PlayerInteractEvent e = (PlayerInteractEvent) event;
        if(!(e.getAction().equals(Action.RIGHT_CLICK_AIR)||e.getAction().equals(Action.RIGHT_CLICK_BLOCK))) {
            return;
        }
        ItemStack itemStack = e.getItem();
        if(itemStack==null) {
            return;
        }
        if(!isArmor(itemStack.getType())) {
            return;
        }
        Player player = e.getPlayer();
        GamePlayer gamePlayer = plugin.getGameManager().getGamePlayer(player);
        gamePlayer.setMaxHealth(UtilCalculator.getMaxHealth(player));
    }

    private boolean isArmor(Material type) {
        String name = type.name();
        return name.endsWith("_HELMET") || name.endsWith("_LEGGINGS") || name.endsWith("_CHESTPLATE") || name.endsWith("_BOOTS");
    }

}
