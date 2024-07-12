package me.bubbles.bosspve.events.presets;

import me.bubbles.bosspve.BossPVE;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

public class GuiClickCommand extends GuiClickIndex {

    private String command;
    private CommandSender commandSender;

    public GuiClickCommand(Inventory inventory, int index, String command, CommandSender commandSender) {
        super(inventory, index, false);
        this.command=command;
        this.commandSender=commandSender;
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
            Bukkit.getServer().dispatchCommand(commandSender, command);
        }
    }

}
