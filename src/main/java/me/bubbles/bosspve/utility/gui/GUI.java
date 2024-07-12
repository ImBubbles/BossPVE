package me.bubbles.bosspve.utility.gui;

import me.bubbles.bosspve.BossPVE;
import me.bubbles.bosspve.events.presets.GuiClick;
import me.bubbles.bosspve.utility.UtilNumber;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import java.util.HashSet;

public abstract class GUI implements IGUI {

    protected Inventory inventory;
    private HashSet<GuiClick> queue;

    public GUI(InventoryHolder holder, int rows) {
        this(Bukkit.createInventory(holder, (int) UtilNumber.clampBorder(54, 18, rows*9), "Chest"));
    }

    public GUI(InventoryHolder holder, int rows, String title) {
        this(Bukkit.createInventory(holder, (int) UtilNumber.clampBorder(54, 18, rows*9), title));
    }

    public GUI(Inventory inventory) {
        this.inventory=inventory;
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
            BossPVE.getInstance().getEventManager().addEvent(guiClick);
        }
        return inventory;
    }

}
