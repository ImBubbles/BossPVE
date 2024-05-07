package me.bubbles.bosspve.items.enchants;

import me.bubbles.bosspve.BossPVE;
import me.bubbles.bosspve.flags.Flag;
import me.bubbles.bosspve.flags.ItemFlag;
import me.bubbles.bosspve.items.manager.bases.enchants.Enchant;
import me.bubbles.bosspve.items.manager.bases.items.Item;
import org.bukkit.Material;

import java.util.Arrays;
import java.util.HashSet;

public class Resistance extends Enchant {

    public Resistance(BossPVE plugin) {
        super(plugin, Rarity.VERY_RARE, "Resistance", Material.CONDUIT, 15);
        getEnchantItem().setDisplayName("&4Resistance");
        allowedTypes.addAll(
                Arrays.asList(
                        Item.Type.ARMOR
                )
        );
    }

    @Override
    public HashSet<Flag<ItemFlag, Double>> getFlags(int level) {
        HashSet<Flag<ItemFlag, Double>> result = new HashSet<>();
        result.add(new Flag<>(ItemFlag.PROT_ADD, 3D*(level), false));
        return result;
    }

    @Override
    public String getDescription() {
        return "Protects you from attacks";
    }

}
