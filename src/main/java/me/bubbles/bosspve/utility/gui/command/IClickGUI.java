package me.bubbles.bosspve.utility.gui.command;

import me.bubbles.bosspve.events.presets.GuiClickIndex;
import org.bukkit.inventory.ItemStack;

public interface IClickGUI<T> {

    ItemStack getItemStack(T object);
    GuiClickIndex getGuiClick(T object, int index);
    ItemStack getBackItemStack();
    ItemStack getForwardItemStack();
    GuiClickIndex getBackClick(int index);
    GuiClickIndex getForwardClick(int index);
    ItemStack getBottomItemStack();

}
