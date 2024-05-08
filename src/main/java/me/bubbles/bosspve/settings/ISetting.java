package me.bubbles.bosspve.settings;

import org.bukkit.Material;

public interface ISetting<T> {

    Material getMaterial(T option);
    String valueToString(T option);

}
