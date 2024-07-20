package me.bubbles.bosspve.items.manager.bases.enchants;

import me.bubbles.bosspve.BossPVE;
import me.bubbles.bosspve.items.manager.bases.items.Item;
import me.bubbles.bosspve.ticker.PlayerTimerManager;
import me.bubbles.bosspve.ticker.Timer;
import me.bubbles.bosspve.utility.UtilEnchant;
import me.bubbles.bosspve.utility.UtilUserData;
import net.minecraft.world.item.enchantment.Enchantment;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.craftbukkit.v1_21_R1.enchantments.CraftEnchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.HashSet;

public abstract class Enchant implements IEnchant {

    private String name;
    private final int maxLevel;
    //private final int cooldown;
    private EnchantItem enchantItem;
    private final Material material;
    public HashSet<Item.Type> allowedTypes;
    private final NamespacedKey namespacedKey;
    private Enchantment enchantment;

    public Enchant(String name, Material material, int maxLevel) {
        this.name=name;
        this.maxLevel=maxLevel;
        this.material=material;
        this.allowedTypes=new HashSet<>();
        this.namespacedKey=new NamespacedKey(BossPVE.getInstance(), name.toLowerCase().replace(" ",""));
        register();
    }

    public int getMaxLevel() {
        return this.maxLevel;
    }

    public void register() {
        UtilEnchant.registerEnchant(this);
    }
    public void setEnchantment(Enchantment enchantment) {
        this.enchantment=enchantment;
    }
    public Enchantment getEnchantment() {
        return enchantment;
    }

    public void onEvent(Event event) {

    }

    public HashMap<Player, ItemStack> playersWithEnchantInMainHand() {
        HashMap<Player, ItemStack> result = new HashMap<>();
        for(Player player : Bukkit.getOnlinePlayers()) {
            if(player.getInventory().getItemInMainHand()==null) {
                continue;
            }
            ItemStack mainHand = player.getInventory().getItemInMainHand();
            if(!mainHand.hasItemMeta()) {
                continue;
            }
            if(!mainHand.getItemMeta().hasEnchants()) {
                continue;
            }
            if(!mainHand.getItemMeta().hasEnchant(CraftEnchantment.minecraftToBukkit(enchantment))) {
                continue;
            }
            result.put(player,mainHand);
        }
        return result;
    }

    public HashMap<Player, ItemStack> playersWithEnchantInOffHand() {
        HashMap<Player, ItemStack> result = new HashMap<>();
        for(Player player : Bukkit.getOnlinePlayers()) {
            if(player.getInventory().getItemInMainHand()==null) {
                continue;
            }
            ItemStack mainHand = player.getInventory().getItemInMainHand();
            if(!mainHand.hasItemMeta()) {
                continue;
            }
            if(!mainHand.getItemMeta().hasEnchants()) {
                continue;
            }
            if(!mainHand.getItemMeta().hasEnchant(CraftEnchantment.minecraftToBukkit(enchantment))) {
                continue;
            }
            result.put(player,mainHand);
        }
        return result;
    }

    public HashMap<Player, ItemStack> playersWithEnchantInAnyHand() {
        HashMap<Player, ItemStack> result = new HashMap<>();
        result.putAll(playersWithEnchantInMainHand());
        result.putAll(playersWithEnchantInOffHand());
        return result;
    }

    public boolean containsEnchant(ItemStack itemStack) {
        if(itemStack==null) {
            return false;
        }
        if(!itemStack.hasItemMeta()) {
            return false;
        }
        if(!itemStack.getItemMeta().hasEnchants()) {
            return false;
        }
        return itemStack.getItemMeta().hasEnchant(CraftEnchantment.minecraftToBukkit(enchantment));
    }

    public EnchantItem getEnchantItem() {
        return enchantItem==null ?
                enchantItem=new EnchantItem(material, this, name)
                :
                enchantItem;
    }

    public int getLevelRequirement() {
        return -1;
    }

    public String getDescription() {
        return null;
    }

    public boolean allowUsage(Player player) {
        if(getLevelRequirement()<=0) {
            return true;
        }
        UtilUserData uud = BossPVE.getInstance().getGameManager().getGamePlayer(player.getUniqueId()).getCache();
        return uud.getLevel()>=getLevelRequirement();
    }

    public String getName() {
        return this.name;
    }

    public String getKey() {
        return this.name.toLowerCase().replace(" ","");
    }

    public NamespacedKey getNamespacedKey() {
        return this.namespacedKey;
    }

}
