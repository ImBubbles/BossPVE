package me.bubbles.bosspve.items.manager.bases.enchants;

import me.bubbles.bosspve.BossPVE;
import org.bukkit.Material;

public abstract class ProcEnchant extends Enchant implements IProcEnchant {

    public ProcEnchant(String name, Material material, int maxLevel) {
        super(name, material, maxLevel);
    }

    protected boolean shouldActivate(int level) {
        return getActivation(level).roll();
    }

}
