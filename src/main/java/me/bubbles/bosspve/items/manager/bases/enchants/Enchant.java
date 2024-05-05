package me.bubbles.bosspve.items.manager.bases.enchants;

import me.bubbles.bosspve.BossPVE;
import me.bubbles.bosspve.items.manager.bases.items.Item;
import me.bubbles.bosspve.ticker.PlayerTimerManager;
import me.bubbles.bosspve.ticker.Timer;
import me.bubbles.bosspve.util.UtilEnchant;
import me.bubbles.bosspve.util.UtilUserData;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.craftbukkit.v1_20_R3.enchantments.CraftEnchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.HashSet;

public abstract class Enchant extends Enchantment implements IEnchant {

    public BossPVE plugin;
    private PlayerTimerManager timerManager;
    private String name;
    private int maxLevel;
    private int coolDown;
    private EnchantItem enchantItem;
    private Material material;
    public HashSet<Item.Type> allowedTypes;
    private NamespacedKey namespacedKey;

    public Enchant(BossPVE plugin, Rarity rarity, String name, Material material, int maxLevel) {
        this(plugin, rarity, name, material, maxLevel,0);
    }

    public Enchant(BossPVE plugin, Rarity rarity, String name, Material material, int maxLevel, int coolDown) {
        super(rarity, null, EquipmentSlot.values());
        this.name=name;
        this.plugin=plugin;
        this.coolDown=coolDown;
        timerManager=new PlayerTimerManager(plugin);
        this.maxLevel=maxLevel;
        this.material=material;
        this.allowedTypes=new HashSet<>();
        this.namespacedKey=new NamespacedKey(plugin, name.toLowerCase().replace(" ",""));
        register();
    }

    @Override
    public int getMaxLevel() {
        return this.maxLevel;
    }

    public void register() {
        UtilEnchant.registerEnchant(this);
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
            if(!mainHand.getItemMeta().hasEnchant(CraftEnchantment.minecraftToBukkit(this))) {
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
            if(!mainHand.getItemMeta().hasEnchant(CraftEnchantment.minecraftToBukkit(this))) {
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
        return itemStack.getItemMeta().hasEnchant(CraftEnchantment.minecraftToBukkit(this));
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
        return enchantItem==null ?
                enchantItem=new EnchantItem(plugin, material, this, name)
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
        UtilUserData uud = plugin.getGameManager().getGamePlayer(player.getUniqueId()).getCache();
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
