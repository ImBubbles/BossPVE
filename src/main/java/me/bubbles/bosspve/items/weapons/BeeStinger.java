package me.bubbles.bosspve.items.weapons;

import me.bubbles.bosspve.BossPVE;
import me.bubbles.bosspve.flags.Flag;
import me.bubbles.bosspve.items.manager.bases.items.Item;
import me.bubbles.bosspve.utility.UtilItemStack;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashSet;

public class BeeStinger extends Item {

    public BeeStinger(BossPVE plugin) {
        super(plugin, Material.GOLDEN_SWORD, "beeStinger");
        ItemStack itemStack = nmsAsItemStack();
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&',
                "&e&lBee Stinger"
        ));
        itemMeta.setLore(new UtilItemStack(plugin, itemStack, this).getUpdatedLore());
        itemMeta.setUnbreakable(true);
        itemMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        itemStack.setItemMeta(itemMeta);
        setNMSStack(itemStack);
    }

    @Override
    public HashSet<Flag<me.bubbles.bosspve.flags.ItemFlag, Double>> getFlags() {
        HashSet<Flag<me.bubbles.bosspve.flags.ItemFlag, Double>> result = new HashSet<>();
        result.add(new Flag<>(me.bubbles.bosspve.flags.ItemFlag.DAMAGE_ADD, 50D, false));
        result.add(new Flag<>(me.bubbles.bosspve.flags.ItemFlag.MONEY_ADD, 7D, false));
        result.add(new Flag<>(me.bubbles.bosspve.flags.ItemFlag.XP_ADD, 6D, false));
        return result;
    }

    @Override
    public Type getType() {
        return Type.WEAPON;
    }

    @Override
    public String getDescription() {
        return "From the rear of a bee";
    }

}
