package me.bubbles.bosspve.items.weapons;

import me.bubbles.bosspve.BossPVE;
import me.bubbles.bosspve.flags.Flag;
import me.bubbles.bosspve.flags.ItemFlag;
import me.bubbles.bosspve.items.manager.bases.items.Item;
import me.bubbles.bosspve.utility.UtilItemStack;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashSet;

public class SkeletonSword extends Item {

    public SkeletonSword(BossPVE plugin) {
        super(plugin, Material.IRON_SWORD, "skeletonSword");
        ItemStack itemStack = nmsAsItemStack();
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&',
                "&7&lSimpleton's Sword"
        ));
        itemMeta.setLore(new UtilItemStack(plugin, itemStack).getUpdatedLore());
        itemMeta.setUnbreakable(true);
        itemMeta.addItemFlags(org.bukkit.inventory.ItemFlag.HIDE_UNBREAKABLE);
        itemMeta.addItemFlags(org.bukkit.inventory.ItemFlag.HIDE_ATTRIBUTES);
        itemStack.setItemMeta(itemMeta);
        setNMSStack(itemStack);
    }

    @Override
    public Type getType() {
        return Type.WEAPON;
    }

    @Override
    public HashSet<Flag<ItemFlag, Double>> getFlags() {
        HashSet<Flag<ItemFlag, Double>> result = new HashSet<>();
        result.add(new Flag<>(ItemFlag.DAMAGE_ADD, 2D, false));
        result.add(new Flag<>(ItemFlag.MONEY_ADD, 1D, false));
        result.add(new Flag<>(ItemFlag.XP_ADD, 1D, false));
        return result;
    }

    @Override
    public String getDescription() {
        return "Simple, yet reliable sword from Simpletons";
    }

}
