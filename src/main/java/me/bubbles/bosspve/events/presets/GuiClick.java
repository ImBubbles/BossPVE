package me.bubbles.bosspve.events.presets;

import me.bubbles.bosspve.BossPVE;
import me.bubbles.bosspve.events.manager.Event;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.*;
import org.bukkit.inventory.Inventory;

import java.util.Arrays;

public class GuiClick extends Event {

    private Inventory inventory;
    private final boolean allow;

    public GuiClick(BossPVE plugin, Inventory inventory, boolean allow) {
        super(plugin, Arrays.asList(InventoryClickEvent.class, InventoryCloseEvent.class));
        this.inventory=inventory;
        this.allow=allow;
    }

    @Override
    public void onEvent(org.bukkit.event.Event event) {
        if(event instanceof InventoryClickEvent) {
            InventoryClickEvent e = (InventoryClickEvent) event;
            /*if(e.getAction().equals(InventoryAction.PICKUP_ALL)) {
                click(e);
            }*/
            //click(e);
            if(e.getClickedInventory()==null) {
                return;
            }
            if(e.getClickedInventory().getHolder()==null) {
                return;
            }
            if(e.getClickedInventory().getHolder().equals(inventory.getHolder())) {
                e.setCancelled(!allow);
            }
        }
        if(event instanceof InventoryCloseEvent) {
            InventoryCloseEvent e = (InventoryCloseEvent) event;
            if(e.getPlayer().equals(inventory.getHolder())) {
                plugin.getEventManager().removeEvent(this);
            }
        }
    }

    /*private void click(InventoryClickEvent event) {
        Inventory clickedInventory = event.getClickedInventory();
        if(clickedInventory==null) {
            return;
        }
        if (this.inventory!=null){
            if(!clickedInventory.equals(inventory)) {
                return;
            }
        }
        event.setCancelled(!allow);
    }*/

    public void setInventory(Inventory inventory) {
        this.inventory=inventory;
    }

    public void unregister() {
        plugin.getEventManager().removeEvent(this);
    }

}
