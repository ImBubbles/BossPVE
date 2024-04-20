package me.bubbles.bosspve.items.weapons;

import me.bubbles.bosspve.BossPVE;
import me.bubbles.bosspve.items.manager.bases.items.Item;
import me.bubbles.bosspve.util.UtilItemStack;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class SkeletonSword extends Item implements IWeapon {

    public SkeletonSword(BossPVE plugin) {
        super(plugin, Material.IRON_SWORD, "skeletonSword");
        ItemStack itemStack = nmsAsItemStack();
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&',
                "&7&lSimpleton's Sword"
        ));
        itemMeta.setLore(new UtilItemStack(plugin, itemStack).getUpdatedLore());
        itemMeta.setUnbreakable(true);
        itemMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        itemStack.setItemMeta(itemMeta);
        setNMSStack(itemStack);
    }

    @Override
    public int getBaseXP() {
        return 0;
    }

    @Override
    public double getBaseMoney() {
        return 0;
    }

    @Override
    public double getBaseDamage() {
        return 10;
    }

    @Override
    public Type getType() {
        return Type.WEAPON;
    }

    @Override
    public String getDescription() {
        return "Simple, yet reliable sword from Simpletons";
    }

}
