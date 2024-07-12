package me.bubbles.bosspve.items.util;

import me.bubbles.bosspve.BossPVE;
import me.bubbles.bosspve.items.manager.bases.items.Item;
import me.bubbles.bosspve.utility.UtilItemStack;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class EnchantExtractor extends Item {

    public EnchantExtractor() {
        super(Material.BRUSH, "enchantExtractor");
        ItemStack itemStack = nmsAsItemStack();
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&',
                "&5&lENCHANT EXTRACTOR"
        ));
        itemMeta.setLore(new UtilItemStack(itemStack, this).getUpdatedLore());
        itemMeta.setUnbreakable(true);
        itemMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        itemStack.setItemMeta(itemMeta);
        setNMSStack(itemStack);
    }

    @Override
    public void onEvent(Event event) {
        if(event instanceof PrepareAnvilEvent) {
            PrepareAnvilEvent e = (PrepareAnvilEvent) event;
            ItemStack firstSlot = e.getInventory().getContents()[0];
            ItemStack secondSlot = e.getInventory().getContents()[1];
            if(firstSlot==null||secondSlot==null) {
                return;
            }
            if(firstSlot.getAmount()!=1) {
                return;
            }
            if(!firstSlot.hasItemMeta()) {
                return;
            }
            if(!firstSlot.getItemMeta().hasEnchants()) {
                return;
            }
            if(!equals(secondSlot)) {
                return;
            }
            if(secondSlot.getAmount()!=1) {
                return;
            }
            UtilItemStack uis = new UtilItemStack(new Extracted().nmsAsItemStack());
            ItemStack result = uis.enchantItem(firstSlot);
            ItemMeta itemMeta = result.getItemMeta();
            uis = new UtilItemStack(result);
            itemMeta.setLore(uis.getUpdatedLore());
            result.setItemMeta(itemMeta);
            e.setResult(result);
        }
        if(event instanceof InventoryClickEvent) {
            InventoryClickEvent e = (InventoryClickEvent) event;
            if(!(e.getClickedInventory() instanceof AnvilInventory)) {
                return;
            }
            if(!e.getSlotType().equals(InventoryType.SlotType.RESULT)) {
                return;
            }
            if(e.getWhoClicked().getInventory().firstEmpty()==-1) {
                return;
            }
            AnvilInventory inventory = (AnvilInventory) e.getClickedInventory();
            ItemStack firstSlot = inventory.getContents()[0];
            ItemStack secondSlot = inventory.getContents()[1];
            ItemStack thirdSlot = e.getCurrentItem();
            if(firstSlot==null||secondSlot==null||thirdSlot==null) {
                return;
            }
            if(!equals(secondSlot)) {
                return;
            }
            Player player = (Player) e.getWhoClicked();
            player.getInventory().addItem(thirdSlot);
            inventory.setItem(0,null);
            inventory.setItem(1,null);
            inventory.setItem(2,null);
        }
    }

    @Override
    public Type getType() {
        return Type.ENCHANT;
    }

    @Override
    public String getDescription() {
        return "Combine this with any item in an anvil to extract the enchants";
    }

}
