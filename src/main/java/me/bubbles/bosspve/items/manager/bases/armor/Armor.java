package me.bubbles.bosspve.items.manager.bases.armor;

import me.bubbles.bosspve.BossPVE;
import me.bubbles.bosspve.items.manager.Item;
import org.bukkit.Material;

public abstract class Armor extends Item implements IArmor {

    private ArmorSet armorSet;

    public Armor(BossPVE plugin, Material material, String nbtIdentifier) { // no armor set
        this(plugin, null, material, nbtIdentifier);
    }

    public Armor(BossPVE plugin, ArmorSet armorSet, Material material, String nbtIdentifier) {
        super(plugin, material, nbtIdentifier);
        this.armorSet=armorSet;
    }

    @Override
    public Type getType() {
        return Type.ARMOR;
    }

    @Override
    public ArmorSet getArmorSet() {
        return armorSet;
    }

}
