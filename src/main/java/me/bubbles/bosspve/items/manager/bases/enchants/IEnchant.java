package me.bubbles.bosspve.items.manager.bases.enchants;

import me.bubbles.bosspve.flags.Flag;
import me.bubbles.bosspve.flags.ItemFlag;

import java.util.HashSet;

public interface IEnchant {
    default HashSet<Flag<ItemFlag, Double>> getFlags(int level) {return new HashSet<>();}

}
