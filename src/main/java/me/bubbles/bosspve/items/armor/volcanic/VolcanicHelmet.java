package me.bubbles.bosspve.items.armor.volcanic;

import me.bubbles.bosspve.BossPVE;
import me.bubbles.bosspve.items.manager.bases.armor.Armor;
import me.bubbles.bosspve.util.UtilItemStack;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

public class VolcanicHelmet extends Armor {

    public VolcanicHelmet(BossPVE plugin) {
        super(plugin, Material.LEATHER_HELMET, "volcanicHelmet");
        ItemStack itemStack = nmsAsItemStack();
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&',
                "&6&lVolcanic Helmet"
        ));
        itemMeta.setLore(new UtilItemStack(plugin, itemStack).getUpdatedLore());
        itemMeta.setUnbreakable(true);
        itemMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        itemMeta.addItemFlags(ItemFlag.HIDE_DYE);
        LeatherArmorMeta laMeta = (LeatherArmorMeta) itemMeta;
        laMeta.setColor(Color.ORANGE);
        itemStack.setItemMeta(itemMeta);
        setNMSStack(itemStack);
    }

    @Override
    public int getBaseProtection() {
        return 3;
    }

    @Override
    public double getDamageMultiplier() {
        return 1;
    }

    @Override
    public String getDescription() {
        return "Helmet from a Volcono";
    }

}
