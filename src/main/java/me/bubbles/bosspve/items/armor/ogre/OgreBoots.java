package me.bubbles.bosspve.items.armor.ogre;

import me.bubbles.bosspve.BossPVE;
import me.bubbles.bosspve.flags.Flag;
import me.bubbles.bosspve.items.manager.bases.armor.Armor;
import me.bubbles.bosspve.utility.UtilItemStack;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import java.util.HashSet;

public class OgreBoots extends Armor {

    public OgreBoots(BossPVE plugin) {
        super(plugin, Material.LEATHER_BOOTS, "ogreBoots");
        ItemStack itemStack = nmsAsItemStack();
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&',
                "&3&lOgre's Boots"
        ));
        itemMeta.setLore(new UtilItemStack(plugin, itemStack, this).getUpdatedLore());
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
    public HashSet<Flag<me.bubbles.bosspve.flags.ItemFlag, Double>> getFlags() {
        HashSet<Flag<me.bubbles.bosspve.flags.ItemFlag, Double>> result = new HashSet<>();
        result.add(new Flag<me.bubbles.bosspve.flags.ItemFlag, Double>(me.bubbles.bosspve.flags.ItemFlag.PROT_ADD, 2D, false));
        result.add(new Flag<me.bubbles.bosspve.flags.ItemFlag, Double>(me.bubbles.bosspve.flags.ItemFlag.DAMAGE_ADD, 1D, false));
        return result;
    }

    @Override
    public String getDescription() {
        return "Boots from an Ogre";
    }

}
