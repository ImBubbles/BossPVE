package me.bubbles.bosspve.items.manager.bases.enchants;

import me.bubbles.bosspve.utility.chance.Activation;

public interface IProcEnchant {

    Activation getActivation(int level);

}
