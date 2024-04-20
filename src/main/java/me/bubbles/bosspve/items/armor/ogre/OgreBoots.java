package me.bubbles.bosspve.items.armor.ogre;

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

public class OgreBoots extends Armor {

    public OgreBoots(BossPVE plugin) {
        super(plugin, Material.LEATHER_BOOTS, "ogreBoots");
        ItemStack itemStack = nmsAsItemStack();
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&',
                "&3&lOgre's Boots"
        ));
        itemMeta.setLore(new UtilItemStack(plugin, itemStack).getUpdatedLore());
        itemMeta.setUnbreakable(true);
        itemMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        itemMeta.addItemFlags(ItemFlag.HIDE_DYE);
        LeatherArmorMeta laMeta = (LeatherArmorMeta) itemMeta;
        laMeta.setColor(Color.LIME);
        itemStack.setItemMeta(itemMeta);
        setNMSStack(itemStack);
    }

    @Override
    public int getBaseProtection() {
        return 2;
    }

    @Override
    public double getDamageMultiplier() {
        return 1;
    }

    @Override
    public String getDescription() {
        return "Boots from an Ogre";
    }

}
