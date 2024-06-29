package me.bubbles.bosspve.events.presets;

import me.bubbles.bosspve.BossPVE;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

public class GuiClickRunnable extends GuiClickIndex {

    private Runnable run;

    public GuiClickRunnable(BossPVE plugin, Inventory inventory, int index, Runnable run) {
        super(plugin, inventory, index, false);
        this.run=run;
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
        if(isIndex()) {
            run.run();
        }
    }

}
