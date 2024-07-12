package me.bubbles.bosspve.events;

import me.bubbles.bosspve.BossPVE;
import me.bubbles.bosspve.events.manager.Event;
import me.bubbles.bosspve.game.GamePlayer;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class UpdateStats extends Event {

    public UpdateStats() {
        super(Arrays.asList(InventoryClickEvent.class, PlayerInteractEvent.class, PlayerItemHeldEvent.class));
    }

    @Override
    public void onEvent(org.bukkit.event.Event event) {
        Player player = null;

        // GET PLAYER OBJ

        if(event instanceof InventoryClickEvent) { // InventoryClick Event
            InventoryClickEvent e = (InventoryClickEvent) event;
            if(e.getClickedInventory()==null) {
                return;
            }
            if(!(e.getClickedInventory().getHolder() instanceof Player)) {
                return;
            }
            player = (Player) e.getClickedInventory().getHolder();
        }
        if(event instanceof PlayerInteractEvent) { // Player Interact Event
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
            player = e.getPlayer();
        }
        if(event instanceof PlayerItemHeldEvent) { // Player Item Held Event
            PlayerItemHeldEvent e = (PlayerItemHeldEvent) event;
            player = e.getPlayer();
        }

        if(player==null) {
            return;
        }

        Player result = player;
        Bukkit.getScheduler().runTaskLater(BossPVE.getInstance(), () -> {
            GamePlayer gamePlayer = BossPVE.getInstance().getGameManager().getGamePlayer(result);
            gamePlayer.updateStats();
        }, 3L);

    }

    private boolean isArmor(Material type) {
        String name = type.name();
        return name.endsWith("_HELMET") || name.endsWith("_LEGGINGS") || name.endsWith("_CHESTPLATE") || name.endsWith("_BOOTS");
    }

}
