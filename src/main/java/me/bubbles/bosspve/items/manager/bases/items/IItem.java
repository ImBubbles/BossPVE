package me.bubbles.bosspve.items.manager.bases.items;

import me.bubbles.bosspve.flags.Flag;
import org.bukkit.event.Event;
import org.bukkit.inventory.ShapedRecipe;

import java.util.HashSet;

public interface IItem {

    Item.Type getType();

    default int getLevelRequirement() {
        return -1;
    }

    default ShapedRecipe getRecipe() {
        return null;
    }

    default String getDescription() {
        return "";
    }

    default void onEvent(Event event) {}
    default void onTick() {}

    HashSet<Flag> getFlags();

}
