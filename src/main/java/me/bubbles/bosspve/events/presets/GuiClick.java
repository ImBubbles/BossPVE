package me.bubbles.bosspve.events.presets;

import me.bubbles.bosspve.BossPVE;
import me.bubbles.bosspve.events.manager.Event;
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
            if(e.getAction().equals(InventoryAction.MOVE_TO_OTHER_INVENTORY)) {
                plugin.getEventManager().removeEvent(this);
            }
            click(e);
        }
        if(event instanceof InventoryCloseEvent) {
            plugin.getEventManager().removeEvent(this);
        }
    }

    private void click(InventoryClickEvent event) {
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
    }

    public void setInventory(Inventory inventory) {
        this.inventory=inventory;
    }

}
