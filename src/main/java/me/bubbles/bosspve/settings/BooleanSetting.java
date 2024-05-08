package me.bubbles.bosspve.settings;

import org.bukkit.Material;

import java.util.Arrays;

public abstract class BooleanSetting extends Setting<Boolean> {


    public BooleanSetting(String displayName, Boolean normal) {
        super(displayName, normal, Arrays.asList(false, true));
    }

    public BooleanSetting(String key, String displayName, Boolean normal) {
        super(key, displayName, normal, Arrays.asList(false, true));
    }

    @Override
    public String valueToString(Boolean option) {
        return option ? "true" : "false";
    }

    @Override
    public Material getMaterial(Boolean option) {
        return option ? Material.LIME_STAINED_GLASS : Material.RED_STAINED_GLASS;
    }

}