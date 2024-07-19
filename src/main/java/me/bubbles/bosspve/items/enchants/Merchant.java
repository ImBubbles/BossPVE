package me.bubbles.bosspve.items.enchants;

import me.bubbles.bosspve.BossPVE;
import me.bubbles.bosspve.flags.Flag;
import me.bubbles.bosspve.flags.ItemFlag;
import me.bubbles.bosspve.items.manager.bases.enchants.Enchant;
import me.bubbles.bosspve.items.manager.bases.items.Item;
import org.bukkit.Material;

import java.util.Arrays;
import java.util.HashSet;

public class Merchant extends Enchant {

    public Merchant() {
        super("Merchant", Material.ARMADILLO_SCUTE, 20);
        getEnchantItem().setDisplayName("&eMerchant");
        allowedTypes.addAll(
                Arrays.asList(
                        Item.Type.WEAPON
                )
        );
    }

    @Override
    public HashSet<Flag<ItemFlag, Double>> getFlags(int level) {
        HashSet<Flag<ItemFlag, Double>> result = new HashSet<>();
        result.add(new Flag<>(ItemFlag.XP_ADD, 5D*(level), false));
        return result;
    }

    @Override
    public String getDescription() {
        return "Get more XP out of your kills";
    }


}
