package me.bubbles.bosspve.items.enchants;

import me.bubbles.bosspve.BossPVE;
import me.bubbles.bosspve.flags.Flag;
import me.bubbles.bosspve.flags.ItemFlag;
import me.bubbles.bosspve.items.manager.bases.enchants.Enchant;
import me.bubbles.bosspve.items.manager.bases.items.Item;
import org.bukkit.Material;

import java.util.Arrays;
import java.util.HashSet;

public class Soulful extends Enchant {

    public Soulful(BossPVE plugin) {
        super(plugin, "Soulful", Material.REDSTONE, 15);
        getEnchantItem().setDisplayName("&eSoulful");
        allowedTypes.addAll(
                Arrays.asList(
                        Item.Type.ARMOR
                )
        );
    }

    @Override
    public HashSet<Flag<ItemFlag, Double>> getFlags(int level) {
        HashSet<Flag<ItemFlag, Double>> result = new HashSet<>();
        result.add(new Flag<>(ItemFlag.HEALTH_ADD, 2D*(level), false));
        return result;
    }

    @Override
    public String getDescription() {
        return "Gives the soul another reason to live";
    }

}
