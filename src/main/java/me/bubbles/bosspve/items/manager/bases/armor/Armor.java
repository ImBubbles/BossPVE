package me.bubbles.bosspve.items.manager.bases.armor;

import me.bubbles.bosspve.BossPVE;
import me.bubbles.bosspve.items.manager.bases.items.Item;
import org.bukkit.Material;

public abstract class Armor extends Item implements IArmor {

    private ArmorSet armorSet;

    public Armor(Material material, String nbtIdentifier) { // no armor set
        this(null, material, nbtIdentifier);
    }

    public Armor(ArmorSet armorSet, Material material, String nbtIdentifier) {
        super(material, nbtIdentifier);
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
