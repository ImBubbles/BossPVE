package me.bubbles.bosspve.items.armor.ogre;

import me.bubbles.bosspve.BossPVE;
import me.bubbles.bosspve.flags.Flag;
import me.bubbles.bosspve.flags.ItemFlag;
import me.bubbles.bosspve.items.manager.bases.armor.Armor;
import me.bubbles.bosspve.utility.UtilItemStack;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import java.util.HashSet;

public class OgrePants extends Armor {

    public OgrePants(BossPVE plugin) {
        super(plugin, Material.LEATHER_LEGGINGS, "ogrePants");
        ItemStack itemStack = nmsAsItemStack();
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&',
                "&3&lOgre's Pants"
        ));
        itemMeta.setLore(new UtilItemStack(plugin, itemStack).getUpdatedLore());
        itemMeta.setUnbreakable(true);
        itemMeta.addItemFlags(org.bukkit.inventory.ItemFlag.HIDE_UNBREAKABLE);
        itemMeta.addItemFlags(org.bukkit.inventory.ItemFlag.HIDE_ATTRIBUTES);
        itemMeta.addItemFlags(org.bukkit.inventory.ItemFlag.HIDE_DYE);
        LeatherArmorMeta laMeta = (LeatherArmorMeta) itemMeta;
        laMeta.setColor(Color.LIME);
        itemStack.setItemMeta(itemMeta);
        setNMSStack(itemStack);
    }

    @Override
    public String getDescription() {
        return "Pants from an Ogre";
    }

    @Override
    public HashSet<Flag<ItemFlag, Double>> getFlags() {
        HashSet<Flag<ItemFlag, Double>> result = new HashSet<>();
        result.add(new Flag<ItemFlag, Double>(ItemFlag.PROT_ADD, 2D, false));
        result.add(new Flag<ItemFlag, Double>(ItemFlag.DAMAGE_ADD, 1D, false));
        return result;
    }

}
