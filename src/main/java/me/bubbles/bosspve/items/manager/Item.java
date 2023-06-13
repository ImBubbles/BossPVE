package me.bubbles.bosspve.items.manager;

import me.bubbles.bosspve.BossPVE;
import net.minecraft.nbt.CompoundTag;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_20_R1.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Item {

    public BossPVE plugin;
    private String nbtIdentifier;
    private net.minecraft.world.item.ItemStack nmsStack;
    private ShapedRecipe recipe;
    private ItemStack itemStack;
    private int baseXP;
    private int baseMoney;

    public Item(BossPVE plugin, Material material, String nbtIdentifier) {
        this.plugin=plugin;
        this.itemStack=new ItemStack(material);
        itemStack.setAmount(1);
        this.nbtIdentifier=nbtIdentifier;
        this.nmsStack = CraftItemStack.asNMSCopy(itemStack);
        CompoundTag nbtTagCompound = nmsStack.getOrCreateTag();
        nbtTagCompound.putString("bpveIdentifier",nbtIdentifier);
        nmsStack.setTag(nbtTagCompound);
        this.baseXP=1;
        this.baseMoney=1;
    }

    public int getBaseXP() {
        return baseXP;
    }

    public void setBaseXP(int baseXP) {
        this.baseXP = baseXP;
    }

    public int getBaseMoney() {
        return baseMoney;
    }

    public void setBaseMoney(int baseMoney) {
        this.baseMoney = baseMoney;
    }

    public ShapedRecipe getRecipe() {
        return recipe;
    }

    public void setRecipe(ShapedRecipe recipe) {
        this.recipe = recipe;
    }

    public net.minecraft.world.item.ItemStack getNMSStack() {
        return nmsStack;
    }

    public void setNMSStack(net.minecraft.world.item.ItemStack nmsStack) {
        this.nmsStack = nmsStack;
    }

    public void setNMSStack(ItemStack itemStack) {
        this.nmsStack=CraftItemStack.asNMSCopy(itemStack);
    }

    public ItemStack nmsAsItemStack() {
        return CraftItemStack.asBukkitCopy(nmsStack);
    }

    public String getNBTIdentifier() {
        return nbtIdentifier;
    }

    public void setDisplayName(String string) {
        ItemStack itemStack = nmsAsItemStack();
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&',
                string
        ));
        itemStack.setItemMeta(itemMeta);
        setNMSStack(itemStack);
    }

    public void setLore(List<String> lore) {
        ItemStack itemStack = nmsAsItemStack();
        ItemMeta itemMeta = nmsAsItemStack().getItemMeta();
        List<String> result = new ArrayList<>();
        lore.forEach(s -> result.add(ChatColor.translateAlternateColorCodes('&',s)));
        itemMeta.setLore(result);
        itemStack.setItemMeta(itemMeta);
        setNMSStack(itemStack);
    }

    public void setLore(String lore) {
        ItemStack itemStack = nmsAsItemStack();
        ItemMeta itemMeta = nmsAsItemStack().getItemMeta();
        List<String> result = Arrays.stream(lore.split("\n")).collect(Collectors.toList()); // add new lines for the /n
        itemMeta.setLore(result);
        itemStack.setItemMeta(itemMeta);
        setNMSStack(itemStack);
    }

    public void onEvent(Event event) {

    }

    public boolean isInInventory(Player player) {
        for(ItemStack itemStack : player.getInventory().getContents()) {
            if(equals(itemStack)) {
                return true;
            }
        }
        return false;
    }

    public boolean equals(ItemStack itemStack) {
        if(itemStack==null) {
            return false;
        }
        if(!itemStack.hasItemMeta()) {
            return false;
        }
        if(itemStack.getData()==null) {
            return false;
        }
        CompoundTag nbtTagCompound = CraftItemStack.asNMSCopy(itemStack).getOrCreateTag();
        if(nbtTagCompound.getString("bpveIdentifier")==null) {
            return false;
        }
        String name = nbtTagCompound.getString("bpveIdentifier");
        return name.equals(nbtIdentifier);
    }

}