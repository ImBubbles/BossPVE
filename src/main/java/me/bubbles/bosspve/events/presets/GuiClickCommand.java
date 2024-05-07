package me.bubbles.bosspve.events.presets;

import me.bubbles.bosspve.BossPVE;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import java.util.HashSet;

public class GuiClickCommand extends GuiClickEvent {

    private int index;
    private String command;
    private CommandSender commandSender;
    private HashSet<InventoryAction> actions;

    public GuiClickCommand(BossPVE plugin, Inventory inventory, int index, String command, CommandSender commandSender) {
        super(plugin, inventory, false);
        this.actions=new HashSet<>();
        this.index=index;
        this.command=command;
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
        Bukkit.getServer().dispatchCommand(commandSender, command);
    }

    public GuiClickCommand setTriggerActions(HashSet<InventoryAction> actions) {
        this.actions=actions;
        return this;
    }

}
