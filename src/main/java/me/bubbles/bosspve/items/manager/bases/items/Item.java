package me.bubbles.bosspve.items.manager.bases.items;

import com.mojang.serialization.Codec;
import me.bubbles.bosspve.BossPVE;
import me.bubbles.bosspve.utility.UtilItemStack;
import me.bubbles.bosspve.utility.UtilUserData;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.tags.TagKey;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.craftbukkit.v1_21_R1.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class Item implements IItem {

    public BossPVE plugin;
    private String nbtIdentifier;
    private net.minecraft.world.item.ItemStack nmsStack;

    public Item(BossPVE plugin, Material material, String nbtIdentifier) {
        //System.out.println("Item Init");
        this.plugin=plugin;
        ItemStack itemStack=new ItemStack(material);
        itemStack.setAmount(1);
        //System.out.println("itemstack made");

        // Custom Tag

        if(!itemStack.hasItemMeta()) {
            itemStack.setItemMeta(Bukkit.getItemFactory().getItemMeta(material));
            //System.out.println("was null");
        }

        ItemMeta customMeta = itemStack.getItemMeta();

        PersistentDataContainer pdc = customMeta.getPersistentDataContainer();

        NamespacedKey key = new NamespacedKey(plugin, "identifier");
        pdc.set(key, PersistentDataType.STRING, nbtIdentifier);

        itemStack.setItemMeta(customMeta);

        //System.out.println("before nbt identifier");
        this.nbtIdentifier=nbtIdentifier;
        //System.out.println("before nms stack");
        this.nmsStack = CraftItemStack.asNMSCopy(itemStack);
        /*if(nmsStack==null) {
            System.out.println("NMS NULL");
        } else {
            System.out.println("NMS NOT NULL");
        }*/
    }

    public ItemStack create() {
        return create(UUID.randomUUID());
    }

    public ItemStack create(UUID uuid) {
        ItemStack itemStack = ItemStack.deserialize(nmsAsItemStack().serialize());
        //net.minecraft.world.item.ItemStack uuidStack = CraftItemStack.asNMSCopy(itemStack);


        ItemMeta customMeta = itemStack.getItemMeta();
        PersistentDataContainer pdc = customMeta.getPersistentDataContainer();
        NamespacedKey key = new NamespacedKey(plugin, "uuid");
        pdc.set(key, PersistentDataType.STRING, uuid.toString());

        itemStack.setItemMeta(customMeta);

        return itemStack;
    }

    public net.minecraft.world.item.ItemStack getNMSStack() {
        return nmsStack;
    }

    protected void setNMSStack(net.minecraft.world.item.ItemStack nmsStack) {
        this.nmsStack=nmsStack;
    }

    protected void setNMSStack(ItemStack itemStack) {
        this.nmsStack=CraftItemStack.asNMSCopy(itemStack);
    }

    public ItemStack nmsAsItemStack() {
        return CraftItemStack.asBukkitCopy(nmsStack);
    }

    public String getNBTIdentifier() {
        return nbtIdentifier;
    }

    protected void setDisplayName(String string) {
        ItemStack itemStack = nmsAsItemStack();
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&',
                string
        ));
        itemStack.setItemMeta(itemMeta);
        setNMSStack(itemStack);
    }

    protected void setLore(List<String> lore) {
        ItemStack itemStack = nmsAsItemStack();
        ItemMeta itemMeta = nmsAsItemStack().getItemMeta();
        List<String> result = new ArrayList<>();
        lore.forEach(s -> result.add(ChatColor.translateAlternateColorCodes('&',s)));
        itemMeta.setLore(result);
        itemStack.setItemMeta(itemMeta);
        setNMSStack(itemStack);
    }

    protected void setLore(String lore) {
        ItemStack itemStack = nmsAsItemStack();
        ItemMeta itemMeta = nmsAsItemStack().getItemMeta();
        List<String> result = Arrays.stream(lore.split("\n")).collect(Collectors.toList()); // add new lines for the /n
        itemMeta.setLore(result);
        itemStack.setItemMeta(itemMeta);
        setNMSStack(itemStack);
    }

    public boolean allowUsage(Player player) {
        if(getLevelRequirement()<=0) {
            return true;
        }
        UtilUserData uud = plugin.getGameManager().getGamePlayer(player.getUniqueId()).getCache();
        return uud.getLevel()>=getLevelRequirement();
    }

    public boolean equals(ItemStack itemStack) {
        if(itemStack==null) {
            return false;
        }
        if(!itemStack.hasItemMeta()) {
            return false;
        }

        ItemMeta customMeta = itemStack.getItemMeta();
        PersistentDataContainer pdc = customMeta.getPersistentDataContainer();
        NamespacedKey key = new NamespacedKey(plugin, "identifier");
        if(!pdc.has(key)) {
            return false;
        }
        String str = pdc.get(key, PersistentDataType.STRING);

        return str.equalsIgnoreCase(nbtIdentifier);
    }

    public static UUID getUUID(BossPVE plugin, ItemStack itemStack) {
        if(itemStack==null) {
            return null;
        }
        if(!itemStack.hasItemMeta()) {
            return null;
        }
        ItemMeta customMeta = itemStack.getItemMeta();
        PersistentDataContainer pdc = customMeta.getPersistentDataContainer();
        NamespacedKey key = new NamespacedKey(plugin, "uuid");
        if(!pdc.has(key)) {
            return null;
        }
        String str = pdc.get(key, PersistentDataType.STRING);

        return UUID.fromString(str);
    }

    public boolean isInInventory(Player player) {
        for(ItemStack itemStack : player.getInventory().getContents()) {
            if(equals(itemStack)) {
                return true;
            }
        }
        return false;
    }

    public List<Player> playersWithItem() {
        return Bukkit.getOnlinePlayers().stream().filter(this::isInInventory).collect(Collectors.toList());
    }

    public enum Type {
        WEAPON,
        ARMOR,
        ENCHANT,
        MISC
    }

}