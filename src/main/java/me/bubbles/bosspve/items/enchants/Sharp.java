package me.bubbles.bosspve.items.enchants;

import me.bubbles.bosspve.BossPVE;
import me.bubbles.bosspve.flags.Flag;
import me.bubbles.bosspve.flags.ItemFlag;
import me.bubbles.bosspve.items.manager.bases.enchants.Enchant;
import me.bubbles.bosspve.items.manager.bases.items.Item;
import org.bukkit.Material;

import java.util.Arrays;
import java.util.HashSet;

public class Sharp extends Enchant {

    public Sharp() {
        super("Sharp", Material.BLADE_POTTERY_SHERD, 10);
        getEnchantItem().setDisplayName("&7Sharp");
        allowedTypes.addAll(
                Arrays.asList(
                        Item.Type.WEAPON
                )
        );
    }

    @Override
    public HashSet<Flag<ItemFlag, Double>> getFlags(int level) {
        HashSet<Flag<ItemFlag, Double>> result = new HashSet<>();
        double val = 1+(0.2D*level);
        result.add(new Flag<>(ItemFlag.DAMAGE_MULT, val, false));
        return result;
    }

    @Override
    public String getDescription() {
        return "Increases damage";
    }

}
