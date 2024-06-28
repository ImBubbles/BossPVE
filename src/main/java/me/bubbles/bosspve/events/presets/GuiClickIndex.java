package me.bubbles.bosspve.events.presets;

import me.bubbles.bosspve.BossPVE;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

public class GuiClickIndex extends GuiClick {

    private int index;
    private boolean isIndex;

    public GuiClickIndex(BossPVE plugin, Inventory inventory, int index, boolean allow) {
        super(plugin, inventory, allow);
        this.index=index;
        this.isIndex=false;
    }

    @Override
    public void onEvent(org.bukkit.event.Event event) {
        super.onEvent(event);
        if(!(event instanceof InventoryClickEvent)) {
            return;
        }
        if(!cont()) {
            return;
        }
        InventoryClickEvent e = (InventoryClickEvent) event;
        // CHECK ACTION
        if(!e.getAction().equals(InventoryAction.PICKUP_ALL)) {
            return;
        }
        // CHECK IF SLOT MATCHES
        if(e.getSlot()!=index) {
            isIndex=false;
            return;
        }
        isIndex=true;
    }

    public int getIndex() {
        return index;
    }

    public boolean isIndex() {
        return isIndex;
    }

}
