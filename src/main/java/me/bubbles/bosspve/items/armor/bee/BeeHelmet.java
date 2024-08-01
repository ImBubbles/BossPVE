package me.bubbles.bosspve.items.armor.bee;

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

public class BeeHelmet extends Armor {

    public BeeHelmet() {
        super(Material.LEATHER_HELMET, "beeHelmet");
        ItemStack itemStack = nmsAsItemStack();
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&',
                "&e&lBee Helmet"
        ));
        itemMeta.setLore(new UtilItemStack(itemStack, this).getUpdatedLore());
        itemMeta.setUnbreakable(true);
        itemMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        itemMeta.addItemFlags(ItemFlag.HIDE_DYE);
        LeatherArmorMeta laMeta = (LeatherArmorMeta) itemMeta;
        laMeta.setColor(Color.YELLOW);
        itemStack.setItemMeta(itemMeta);
        setNMSStack(itemStack);
    }

    @Override
    public HashSet<Flag<me.bubbles.bosspve.flags.ItemFlag, Double>> getFlags() {
        HashSet<Flag<me.bubbles.bosspve.flags.ItemFlag, Double>> result = new HashSet<>();
        result.add(new Flag<me.bubbles.bosspve.flags.ItemFlag, Double>(me.bubbles.bosspve.flags.ItemFlag.HEALTH_ADD, 7D, false));
        result.add(new Flag<me.bubbles.bosspve.flags.ItemFlag, Double>(me.bubbles.bosspve.flags.ItemFlag.PROT_ADD, 4D, false));
        result.add(new Flag<me.bubbles.bosspve.flags.ItemFlag, Double>(me.bubbles.bosspve.flags.ItemFlag.DAMAGE_ADD, 3D, false));
        return result;
    }
    @Override
    public String getDescription() {
        return "Helmet from a Bee";
    }

}
