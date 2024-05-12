package me.bubbles.bosspve.utility.guis;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public interface IGUI {

    Inventory build();

    //ItemStack getItemStack(int index);
    ItemStack getBackground();
    //GuiClickEvent getGuiClickEvent(int index);

    default String getTitle() {
        return "Chest";
    }

}
