package me.bubbles.bosspve.items.manager;

import me.bubbles.bosspve.BossPVE;
import me.bubbles.bosspve.items.armor.ogre.OgreSet;
import me.bubbles.bosspve.items.manager.bases.armor.ArmorSet;
import me.bubbles.bosspve.items.manager.enchant.EnchantManager;
import me.bubbles.bosspve.items.util.EnchantExtractor;
import me.bubbles.bosspve.items.util.Extracted;
import me.bubbles.bosspve.items.weapons.SkeletonSword;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;

import java.util.HashSet;
import java.util.Optional;
import java.util.stream.Collectors;

public class ItemManager {

    public BossPVE plugin;
    private HashSet<Item> items;
    private HashSet<ArmorSet> armorSets;
    private EnchantManager enchantManager;

    public ItemManager(BossPVE plugin) {
        this.plugin = plugin;
        items=new HashSet<>();
        armorSets=new HashSet<>();
        enchantManager=new EnchantManager(this);
        registerItem(
                new SkeletonSword(plugin),
                new EnchantExtractor(plugin),
                new Extracted(plugin)
        );
        // REGISTER ARMOR
        registerArmorSet(
                new OgreSet(plugin)
        );
    }

    public EnchantManager getEnchantManager() {
        return enchantManager;
    }

    public void registerItem(Item... items) {
        for(Item item : items) {
            this.items.add(item);
            if(item.getRecipe()!=null) {
                Bukkit.addRecipe(item.getRecipe());
            }
        }
    }

    public void registerArmorSet(ArmorSet... armorSets) {
        for(ArmorSet armorSet : armorSets) {
            this.armorSets.add(armorSet);
            registerItem(armorSet.getBoots(),armorSet.getPants(),armorSet.getChestplate(),armorSet.getHelmet());
        }
    }

    public HashSet<Item> getItems() {
        return items;
    }

    public void onTick() {
        enchantManager.onTick();
        items.forEach(Item::onTick);
        armorSets.forEach(ArmorSet::onTick);
    }

    public void onEvent(Event event) {
        items.forEach(item -> item.onEvent(event));
        armorSets.forEach(armorSet -> armorSet.onEvent(event));
        enchantManager.onEvent(event);
    }

    public Item getItemByName(String string) {
        Optional<Item> optItem = items.stream().filter(item -> item.getNBTIdentifier().equalsIgnoreCase(string)).findFirst();
        return optItem.orElse(null);
    }

    public Item getItemFromStack(ItemStack itemStack) {
        // Get FIRST
        Optional<Item> optItem = items.stream().filter(item -> item.equals(itemStack)).collect(Collectors.toList()).stream().findFirst();
        return optItem.orElse(null);
    }

}
