package me.bubbles.bosspve.items.enchants;

import me.bubbles.bosspve.items.manager.enchant.Enchant;
import me.bubbles.bosspve.items.manager.Item;
import me.bubbles.bosspve.items.manager.ItemManager;
import org.bukkit.Material;

import java.util.List;

public class Resistance extends Enchant {

    public Resistance(ItemManager itemManager) {
        super(itemManager, "Resistance", Material.CONDUIT, 15);
        getEnchantItem().setDisplayName("&4Resistance");
        allowedTypes.addAll(
                List.of(
                        Item.Type.ARMOR
                )
        );
    }

    @Override
    public double getDamageProtection(int level) {
        return level*1.1;
    }

    @Override
    public String getDescription() {
        return "Protects you from attacks";
    }

}
