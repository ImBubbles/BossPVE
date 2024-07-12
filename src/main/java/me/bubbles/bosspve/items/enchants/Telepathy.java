package me.bubbles.bosspve.items.enchants;

import me.bubbles.bosspve.BossPVE;
import me.bubbles.bosspve.items.manager.bases.enchants.Enchant;
import me.bubbles.bosspve.items.manager.bases.items.Item;
import org.bukkit.Material;

import java.util.Arrays;

public class Telepathy extends Enchant {

    public Telepathy() {
        super("Telepathy", Material.BOOK, 1);
        getEnchantItem().setDisplayName("&5Telepathy");
        allowedTypes.addAll(
                Arrays.asList(
                        Item.Type.WEAPON
                )
        );
    }

    @Override
    public String getDescription() {
        return "Teleports items into your inventory";
    }

}
