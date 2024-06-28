package me.bubbles.bosspve.items.enchants;

import me.bubbles.bosspve.BossPVE;
import me.bubbles.bosspve.flags.Flag;
import me.bubbles.bosspve.flags.ItemFlag;
import me.bubbles.bosspve.items.manager.bases.enchants.Enchant;
import me.bubbles.bosspve.items.manager.bases.items.Item;
import org.bukkit.Material;

import java.util.Arrays;
import java.util.HashSet;

public class Essence extends Enchant {

    public Essence(BossPVE plugin) {
        super(plugin, "Essence", Material.REDSTONE, 15);
        getEnchantItem().setDisplayName("&d&lEssence");
        allowedTypes.addAll(
                Arrays.asList(
                        Item.Type.ARMOR
                )
        );
    }

    @Override
    public HashSet<Flag<ItemFlag, Double>> getFlags(int level) {
        HashSet<Flag<ItemFlag, Double>> result = new HashSet<>();
        double val = 1+(0.2D*level);
        result.add(new Flag<>(ItemFlag.HEALTH_MULT, val, false));
        return result;
    }

    @Override
    public String getDescription() {
        return "Blesses the soul";
    }

}
