package me.bubbles.bosspve.items.manager.bases.enchants;

import me.bubbles.bosspve.BossPVE;
import me.bubbles.bosspve.items.manager.bases.items.Item;
import me.bubbles.bosspve.utility.UtilItemStack;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_21_R1.enchantments.CraftEnchantment;
import org.bukkit.craftbukkit.v1_21_R1.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashSet;
import java.util.concurrent.atomic.AtomicBoolean;

public class EnchantItem extends Item {

    private final Enchant enchant;

    public EnchantItem(Material material, Enchant enchant, String nbtIdentifier) {
        super(material, nbtIdentifier.replace(" ", "").toLowerCase()+"Ench");
        this.enchant=enchant;
        ItemStack itemStack = nmsAsItemStack();
        //itemStack.addUnsafeEnchantment(CraftEnchantment.minecraftToBukkit(enchant.getEnchantment()),1);

        // ADD ENCHANT

        ItemMeta itemMeta = itemStack.getItemMeta();
        /*if(itemMeta.hasEnchant(CraftEnchantment.minecraftToBukkit(enchant.getEnchantment()))) {
            itemMeta.removeEnchant(CraftEnchantment.minecraftToBukkit(enchant.getEnchantment()));
        }*/
        itemMeta.addEnchant(CraftEnchantment.minecraftToBukkit(enchant.getEnchantment()), 1, true);
        itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        itemStack.setItemMeta(itemMeta);

        // SET LORE

        itemMeta = itemStack.getItemMeta();

        itemMeta.setLore(new UtilItemStack(itemStack).getUpdatedLore());
        itemStack.setItemMeta(itemMeta);

        setNMSStack(itemStack);

    }

    /*@Override
    public net.minecraft.world.item.ItemStack getNMSStack() {
        *//*ItemStack itemStack = super.nmsAsItemStack();
        //itemStack.addUnsafeEnchantment(CraftEnchantment.minecraftToBukkit(enchant.getEnchantment()),1);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setLore(new UtilItemStack(plugin,itemStack).getUpdatedLore());
        //itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        itemStack.setItemMeta(itemMeta);*//*


        *//*ItemStack result = ItemStack.deserialize(nmsAsItemStack().serialize());
        ItemMeta itemMeta = result.getItemMeta();
        itemMeta.removeEnchant(CraftEnchantment.minecraftToBukkit(enchant.getEnchantment()));
        itemMeta.addEnchant(CraftEnchantment.minecraftToBukkit(enchant.getEnchantment()), 1, true);
        result.setItemMeta(itemMeta);
        itemMeta.setLore(new UtilItemStack(plugin,result).getUpdatedLore());
        result.setItemMeta(itemMeta);

        return CraftItemStack.asNMSCopy(result);*//*

        return CraftItemStack.asNMSCopy(getAtLevel(1));

        //return super.getNMSStack();
    }*/

    @Override
    public void setDisplayName(String string) {
        super.setDisplayName(string);
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
            if(firstSlot==null) {
                return;
            }
            ItemStack secondSlot = e.getInventory().getContents()[1];
            if(firstSlot.getAmount()>1) {
                return;
            }
            if(secondSlot.getAmount()>1) {
                return;
            }
            UtilItemStack uis = new UtilItemStack(firstSlot);
            UtilItemStack uis2 = new UtilItemStack(secondSlot);
            Item customFirst = BossPVE.getInstance().getItemManager().getItemFromStack(firstSlot);
            Item customSecond = BossPVE.getInstance().getItemManager().getItemFromStack(secondSlot);
            HashSet<Enchant> firstCustomEnchants=uis.getCustomEnchants();
            HashSet<Enchant> secondCustomEnchants=uis2.getCustomEnchants();
            if(customFirst!=null) {
                AtomicBoolean allowedCont = new AtomicBoolean(true);
                if(customSecond!=null) {
                    if(!customFirst.getType().equals(customSecond.getType())) {
                        secondCustomEnchants.forEach(enchant1 -> {
                            if(!enchant1.allowedTypes.contains(customFirst.getType())) {
                                allowedCont.set(false);
                            }});
                    }
                }
                if(!allowedCont.get()) {
                    e.setResult(null);
                    return;
                }
            }
            if((!firstCustomEnchants.isEmpty()&&(!secondCustomEnchants.isEmpty()))) {
                if(!anyOverLap(firstCustomEnchants,secondCustomEnchants)) {
                    e.setResult(null);
                    return;
                }
            }
            ItemStack result = uis.enchantItem(secondSlot);
            UtilItemStack utilResult = new UtilItemStack(result, customFirst);
            ItemMeta resultMeta = result.getItemMeta();
            resultMeta.setLore(utilResult.getUpdatedLore());
            result.setItemMeta(resultMeta);
            e.getInventory().setItem(2,result);
            result.setAmount(1);
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
            if(thirdSlot.getType().equals(Material.AIR)) {
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

    public ItemStack getAtLevel(int level) {
        ItemStack result = ItemStack.deserialize(nmsAsItemStack().serialize());
        ItemMeta itemMeta = result.getItemMeta();
        itemMeta.removeEnchant(CraftEnchantment.minecraftToBukkit(enchant.getEnchantment()));
        itemMeta.addEnchant(CraftEnchantment.minecraftToBukkit(enchant.getEnchantment()), level, true);
        result.setItemMeta(itemMeta);
        itemMeta.setLore(new UtilItemStack(result).getUpdatedLore());
        result.setItemMeta(itemMeta);
        return result;
    }

    public Enchant getEnchant() {
        return enchant;
    }

    private boolean anyOverLap(HashSet<Enchant> first, HashSet<Enchant> second) {
        AtomicBoolean atomicBoolean = new AtomicBoolean(false);
        first.forEach(firstEnch -> {
            second.forEach(secondEnch -> {
                secondEnch.allowedTypes.forEach(type -> {
                    if(firstEnch.allowedTypes.contains(type)) {
                        atomicBoolean.set(true);
                    }
                });
            });
        });
        return atomicBoolean.get();
    }

    @Override
    public Type getType() {
        return Type.ENCHANT;
    }

}