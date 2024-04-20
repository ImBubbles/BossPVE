package me.bubbles.bosspve.items.manager.bases.armor;

import org.bukkit.event.Event;

public interface IArmorSet {

    Armor getBoots();
    Armor getPants();
    Armor getChestplate();
    Armor getHelmet();

    default void onEvent(Event event) {}
    default void onTick() {}

}
