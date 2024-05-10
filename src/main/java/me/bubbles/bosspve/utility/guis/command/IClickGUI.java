package me.bubbles.bosspve.utility.guis.command;

import me.bubbles.bosspve.events.presets.GuiClickIndex;
import org.bukkit.inventory.ItemStack;

public interface IClickGUI<T> {

    ItemStack getItemStack(T object);
    GuiClickIndex getGuiClick(T object, int index);
    ItemStack getBackItemStack();
    ItemStack getForwardItemStack();
    GuiClickIndex getBackCommand(int index);
    GuiClickIndex getForwardCommand(int index);
    ItemStack getBottomItemStack();

}
