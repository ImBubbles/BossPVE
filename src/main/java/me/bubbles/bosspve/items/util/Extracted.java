package me.bubbles.bosspve.items.util;

import me.bubbles.bosspve.BossPVE;
import me.bubbles.bosspve.items.manager.bases.items.Item;
import me.bubbles.bosspve.utility.UtilItemStack;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Extracted extends Item {

    public Extracted(BossPVE plugin) {
        super(plugin, Material.KNOWLEDGE_BOOK, "extracted");
        ItemStack itemStack = nmsAsItemStack();
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&',
                "&5&k|&5&lEXTRACTED&5&k|"
        ));
        itemMeta.setLore(new UtilItemStack(plugin, itemStack, this).getUpdatedLore());
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
            if(e.getInventory().getContents()[0]==null||e.getInventory().getContents()[1]==null) {
                return;
            }
            if(equals(e.getInventory().getContents()[0])&&e.getInventory().getContents()[1].getType().equals(Material.ENCHANTED_BOOK)) {
                e.setResult(null);
                return;
            }
            if(!equals(e.getInventory().getContents()[1])) {
                return;
            }
            ItemStack firstSlot = e.getInventory().getContents()[0];
            ItemStack secondSlot = e.getInventory().getContents()[1];
            if(firstSlot.getAmount()>1) {
                return;
            }
            if(secondSlot.getAmount()>1) {
                return;
            }
            UtilItemStack uis = new UtilItemStack(plugin,firstSlot);
            Item customFirst = plugin.getItemManager().getItemFromStack(firstSlot);
            if(customFirst!=null) {
                uis.getCustomEnchants().forEach(enchant ->{
                    if(!enchant.allowedTypes.contains(customFirst.getType())) {
                        e.setResult(null);
                        return;
                    }
                });
            }
            ItemStack result = uis.enchantItem(secondSlot);
            e.getInventory().setItem(2,result);
            result.setAmount(1);

            UtilItemStack ris = new UtilItemStack(plugin, result);
            ItemMeta itemMeta = result.getItemMeta();
            itemMeta.setLore(ris.getUpdatedLore());
            result.setItemMeta(itemMeta);

            e.setResult(result);
        }
        if(event instanceof PlayerInteractEvent) {
            PlayerInteractEvent e = (PlayerInteractEvent) event;
            if(!(e.getAction().equals(Action.RIGHT_CLICK_BLOCK)||e.getAction().equals(Action.RIGHT_CLICK_AIR))) {
                return;
            }
            if(!equals(e.getItem())) {
                return;
            }
            e.setCancelled(true);
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

}
