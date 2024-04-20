package me.bubbles.bosspve.items.manager.bases.enchants;

import me.bubbles.bosspve.BossPVE;
import me.bubbles.bosspve.items.flags.Flag;
import me.bubbles.bosspve.items.manager.bases.items.Item;
import me.bubbles.bosspve.items.manager.ItemManager;
import me.bubbles.bosspve.ticker.PlayerTimerManager;
import me.bubbles.bosspve.ticker.Timer;
import me.bubbles.bosspve.util.UtilUserData;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.stream.Collectors;

public class Enchant extends Enchantment {

    public BossPVE plugin;
    private PlayerTimerManager timerManager;
    private String name;
    private int maxLevel;
    private int coolDown;
    private EnchantItem enchantItem;
    private Material material;
    public HashSet<Item.Type> allowedTypes;
    private HashSet<Flag> additiveFlags;

    public Enchant(ItemManager itemManager, String name, Material material, int maxLevel) {
        this(itemManager,name,material,maxLevel,0);
    }

    public Enchant(ItemManager itemManager, String name, Material material, int maxLevel, int coolDown) {
        super(NamespacedKey.minecraft(name.toLowerCase()));
        this.name=name;
        this.plugin=itemManager.plugin;
        this.coolDown=coolDown;
        timerManager=new PlayerTimerManager(plugin);
        this.maxLevel=maxLevel;
        this.material=material;
        this.allowedTypes=new HashSet<>();
        register(itemManager);
    }

    public void register(ItemManager itemManager) {
        enchantItem=new EnchantItem(plugin, material, this, name);
        itemManager.registerItem(enchantItem);
        if(Arrays.stream(Enchantment.values()).collect(Collectors.toList()).contains(this)) {
            return;
        }
        try {
            Field f = Enchantment.class.getDeclaredField("acceptingNew");
            f.setAccessible(true);
            f.set(null, true);
            Enchantment.registerEnchantment(this);
        } catch(NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    protected void addFlags(Flag... flags) {
        this.additiveFlags.addAll(Arrays.asList(flags));
    }

    public HashSet<Flag> getFlags() {
        return this.additiveFlags;
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
            if(!mainHand.getItemMeta().hasEnchant(this)) {
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
            if(!mainHand.getItemMeta().hasEnchant(this)) {
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
        return itemStack.getItemMeta().hasEnchant(this);
    }

    public boolean coolDowns() {
        return coolDown!=0;
    }

    public boolean isOnCoolDown(Player player) {
        if(!coolDowns()) {
            return false;
        }
        if(!timerManager.contains(player)) {
            timerManager.addTimer(player,new Timer(plugin,coolDown));
            return false;
        }
        return timerManager.isTimerActive(player);
    }

    public void restartCoolDown(Player player) {
        if(coolDown==0) {
            return;
        }
        if(!timerManager.contains(player)) {
            timerManager.addTimer(player, new Timer(plugin,coolDown));
            return;
        }
        timerManager.restartTimer(player);
    }

    public int getCoolDown(Player player) {
        if(timerManager.contains(player)) {
            return timerManager.getTimer(player).getRemainingTicks();
        }
        return -1;
    }

    public void onTick() {
        if(coolDowns()) {
            timerManager.onTick();
        }
    }

    public EnchantItem getEnchantItem() {
        return enchantItem;
    }

    public int getLevelRequirement() {
        return -1;
    }

    public String getDescription() {
        return null;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getMaxLevel() {
        return maxLevel;
    }

    @Override
    public int getStartLevel() {
        return 1;
    }

    @Override
    public EnchantmentTarget getItemTarget() {
        return null;
    }

    @Override
    public boolean isTreasure() {
        return false;
    }

    @Override
    public boolean isCursed() {
        return false;
    }

    @Override
    public boolean conflictsWith(Enchantment enchantment) {
        return false;
    }

    @Override
    public boolean canEnchantItem(ItemStack itemStack) {
        return false;
    }

    public boolean allowUsage(Player player) {
        if(getLevelRequirement()<=0) {
            return true;
        }
        UtilUserData uud = UtilUserData.getUtilUserData(player.getUniqueId());
        return uud.getLevel()>=getLevelRequirement();
    }

}
