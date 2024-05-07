package me.bubbles.bosspve.settings;

import org.bukkit.Material;

public enum Settings {

    KILL_MESSAGES("Kill Messages", 1, 1, 0),
    PROCC_MESSAGES("Activation Messages", 1, 1 ,0, Material.ENCHANTED_BOOK),
    ITEMDROP_MESSAGES("Item Drop Messages", 1, 1, 0);

    private String displayName;
    private int defaultValue;
    private int a;
    private int b;
    private Material m;

    Settings(String s, int i, int a, int b) {
        this.displayName=s;
        this.defaultValue=i;
        this.a=a;
        this.b=b;
        this.m=Material.WHITE_STAINED_GLASS;
    }

    Settings(String s, int i, int a, int b, Material m) {
        this.displayName=s;
        this.defaultValue=i;
        this.a=a;
        this.b=b;
        this.m=m;
    }

    public String getDisplayName() {
        return displayName;
    }

    public int getDefaultValue() {
        return defaultValue;
    }

    public int getMax() {
        return Math.max(a, b);
    }
    public int getMin() {
        return Math.min(a, b);
    }
    public Material getMaterial() {
        return m;
    }

}
