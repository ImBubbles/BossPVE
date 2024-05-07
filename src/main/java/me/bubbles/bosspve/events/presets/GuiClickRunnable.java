package me.bubbles.bosspve.events.presets;

import me.bubbles.bosspve.BossPVE;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import java.util.HashSet;

public class GuiClickRunnable extends GuiClickEvent {

    private int index;
    private Runnable run;
    private CommandSender commandSender;
    private HashSet<InventoryAction> actions;

    public GuiClickRunnable(BossPVE plugin, Inventory inventory, int index, Runnable run, CommandSender commandSender) {
        super(plugin, inventory, false);
        this.actions=new HashSet<>();
        this.index=index;
        this.run=run;
        this.commandSender=commandSender;
    }

    @Override
    public void onEvent(org.bukkit.event.Event event) {
        super.onEvent(event);
        if(!(event instanceof InventoryClickEvent)) {
            return;
        }
        InventoryClickEvent e = (InventoryClickEvent) event;
        // DENY IF A TRIGGER ACTION ISN'T USED
        if(!actions.isEmpty()) {
            if(!actions.contains(e.getAction())) {
                return;
            }
        } else {
            if(!e.getAction().equals(InventoryAction.PICKUP_ALL)) {
                return;
            }
        }
        // CHECK IF SLOT MATCHES
        if(e.getSlot()!=index) {
            return;
        }
        run.run();
    }

    public GuiClickRunnable setTriggerActions(HashSet<InventoryAction> actions) {
        this.actions=actions;
        return this;
    }

}
