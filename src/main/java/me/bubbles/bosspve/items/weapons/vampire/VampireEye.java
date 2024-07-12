package me.bubbles.bosspve.items.weapons.vampire;

import me.bubbles.bosspve.BossPVE;
import me.bubbles.bosspve.flags.Flag;
import me.bubbles.bosspve.items.manager.ItemManager;
import me.bubbles.bosspve.items.manager.bases.items.Item;
import me.bubbles.bosspve.utility.UtilItemStack;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashSet;

public class VampireEye extends Item {

    private ItemManager itemManager;

    public VampireEye(ItemManager itemManager) {
        super(Material.FERMENTED_SPIDER_EYE, "vampireEye");
        this.itemManager=itemManager;
        ItemStack itemStack = nmsAsItemStack();
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&',
                "&4&lVampire Eye"
        ));
        itemMeta.setLore(new UtilItemStack(itemStack, this).getUpdatedLore());
        itemMeta.setUnbreakable(true);
        itemMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        itemStack.setItemMeta(itemMeta);
        setNMSStack(itemStack);
    }

    @Override
    public ShapedRecipe getRecipe() {
        ShapedRecipe recipe = new ShapedRecipe(new NamespacedKey(BossPVE.getInstance(), getNBTIdentifier()),nmsAsItemStack());
        recipe.shape(
                "FFF",
                "FFF",
                "FFF"
        );
        recipe.setIngredient('F', new RecipeChoice.ExactChoice(itemManager.getItemByName("vampireeyefragment").nmsAsItemStack()));
        return recipe;
    }

    @Override
    public HashSet<Flag<me.bubbles.bosspve.flags.ItemFlag, Double>> getFlags() {
        HashSet<Flag<me.bubbles.bosspve.flags.ItemFlag, Double>> result = new HashSet<>();
        result.add(new Flag<>(me.bubbles.bosspve.flags.ItemFlag.DAMAGE_ADD, 70D, false));
        result.add(new Flag<>(me.bubbles.bosspve.flags.ItemFlag.MONEY_ADD, 10D, false));
        result.add(new Flag<>(me.bubbles.bosspve.flags.ItemFlag.XP_ADD, 10D, false));
        return result;
    }

    @Override
    public Type getType() {
        return Type.WEAPON;
    }

    @Override
    public String getDescription() {
        return "From the eye of a vampire";
    }

}
