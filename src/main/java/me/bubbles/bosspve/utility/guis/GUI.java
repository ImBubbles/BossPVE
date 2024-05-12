package me.bubbles.bosspve.utility.guis;

import me.bubbles.bosspve.BossPVE;
import me.bubbles.bosspve.events.presets.GuiClick;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;

import java.util.HashSet;

public abstract class GUI implements IGUI {

    protected Inventory inventory;
    protected BossPVE plugin;
    private HashSet<GuiClick> queue;

    public GUI(BossPVE plugin, InventoryHolder holder, InventoryType inventoryType) {
        this(plugin, Bukkit.createInventory(holder, inventoryType));
    }

    public GUI(BossPVE plugin, Inventory inventory) {
        this.inventory=inventory;
        this.plugin=plugin;
        this.queue=new HashSet<>();
    }

    protected void set(int index, ItemStack itemStack, GuiClick guiClick) {
        inventory.setItem(index, itemStack);
        if(guiClick!=null) {
            queueClickEvent(guiClick);
        }
    }

    protected void queueClickEvent(GuiClick guiClick) {
        queue.add(guiClick);
    }

    public void fillBackground() {
        int size = inventory.getContents().length;
        for(int i = 0; i<size-1; i++) {
            if(inventory.getContents()[i]==null) {
                inventory.setItem(i, getBackground());
            } else if(inventory.getContents()[i].getType().equals(Material.AIR)) {
                inventory.setItem(i, getBackground());
            }
        }
    }

    @Override
    public Inventory build() {
        fillBackground();
        for(GuiClick guiClick : queue) {
            guiClick.setInventory(inventory);
            plugin.getEventManager().addEvent(guiClick);
        }
        return inventory;
    }

}
