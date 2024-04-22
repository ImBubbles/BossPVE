package me.bubbles.bosspve.items.manager.bases.enchants;

import me.bubbles.bosspve.flags.Flag;

import java.util.HashSet;

public interface IEnchant {
    default HashSet<Flag> getFlags(int level) {return null;}

}
