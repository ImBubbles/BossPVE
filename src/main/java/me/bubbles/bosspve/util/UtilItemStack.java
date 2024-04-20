package me.bubbles.bosspve.util;

import me.bubbles.bosspve.BossPVE;
import me.bubbles.bosspve.items.flags.Flag;
import me.bubbles.bosspve.items.manager.bases.items.Item;
import me.bubbles.bosspve.items.manager.bases.armor.Armor;
import me.bubbles.bosspve.items.manager.bases.enchants.Enchant;
import me.bubbles.bosspve.stages.Stage;
import me.bubbles.bosspve.util.string.UtilString;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class UtilItemStack {

    private ItemStack itemStack;
    private BossPVE plugin;

    public UtilItemStack(BossPVE plugin, ItemStack itemStack) {
        this.plugin=plugin;
        this.itemStack=itemStack;
    }

    @SuppressWarnings("deprecation")
    public List<String> getUpdatedLore(ItemStack itemStack) {
        List<String> lore=new ArrayList<>();
        for(Enchantment enchantment : itemStack.getEnchantments().keySet()) {
            lore.add(ChatColor.translateAlternateColorCodes('&',
                    "&9" + enchantment.getName() + " " + itemStack.getItemMeta().getEnchantLevel(enchantment)
            ));
        }
        return lore;
    }

    public List<String> getUpdatedLoreForPlayer(ItemStack itemStack, Player player) {
        int dmg=(int) calculateDamage(1,player)-1;
        int xp=(int) calculateXp(1,player);
        int money=(int) calculateMoney(1,player);
        List<String> lore = new ArrayList<>();
        Item item = plugin.getItemManager().getItemFromStack(itemStack);
        UtilUserData uud = UtilUserData.getUtilUserData(player.getUniqueId());
        int enchantsAmt = itemStack.getEnchantments().size();
        boolean cont=true;
        for(Enchantment enchantment : itemStack.getEnchantments().keySet()) {
            if(enchantment instanceof Enchant) {
                Enchant enchant = (Enchant) enchantment;
                if(!(uud.getLevel()>=enchant.getLevelRequirement())) {
                    lore.add(ChatColor.translateAlternateColorCodes('&',
                            "&c" + enchantment.getName() + " " + itemStack.getItemMeta().getEnchantLevel(enchantment)
                    ));
                } else {
                    lore.add(ChatColor.translateAlternateColorCodes('&',
                            "&9" + enchantment.getName() + " " + itemStack.getItemMeta().getEnchantLevel(enchantment)
                    ));
                    if(enchantsAmt<=5) {
                        if(enchant.getDescription()!=null) {
                            lore.add(ChatColor.translateAlternateColorCodes('&',"&7"+enchant.getDescription()));
                        }
                    }
                    cont=false;
                }
            }
            if(cont) {
                lore.add(ChatColor.translateAlternateColorCodes('&',
                        "&9" + enchantment.getName() + " " + itemStack.getItemMeta().getEnchantLevel(enchantment)
                ));
            }
        }
        if(item!=null) {
            if((!item.getDescription().equals(""))&&item.getDescription()!=null) {
                lore.add(UtilString.colorFillPlaceholders("&8"+item.getDescription()));
            }
            if(item.getType().equals(Item.Type.WEAPON)) {
                lore.add(
                        UtilString.colorFillPlaceholders("%primary%Damage:%secondary% "+dmg)
                );
                lore.add(
                        UtilString.colorFillPlaceholders("%primary%XP:%secondary% "+xp)
                );
                lore.add(
                        UtilString.colorFillPlaceholders("%primary%Money:%secondary% $"+money)
                );
            }
            if(item.getType().equals(Item.Type.ARMOR)) {
                Armor armor = (Armor) item;
                lore.add(
                        UtilString.colorFillPlaceholders("%primary%Defence:%secondary% "+armor.getBaseProtection())
                );
            }
        }
        return lore;
    }

    public List<String> getUpdatedLoreForPlayer(Player player) {
        return getUpdatedLoreForPlayer(itemStack,player);
    }

    public List<String> getUpdatedLore() {
        return getUpdatedLore(itemStack);
    }

    public ItemStack enchantItem(ItemStack giver) {
        ItemStack receiver = itemStack.clone();

        ItemMeta receiverMeta = receiver.getItemMeta();
        ItemMeta giverMeta = giver.getItemMeta();
        if((!giverMeta.hasEnchants())&&(!giver.getType().equals(Material.ENCHANTED_BOOK))) {
            return receiver;
        }
        if(receiver.getType().equals(Material.ENCHANTED_BOOK)) {
            EnchantmentStorageMeta ems = (EnchantmentStorageMeta) receiverMeta;
            for(Enchantment enchantment : ems.getStoredEnchants().keySet()) {
                receiverMeta.addEnchant(enchantment,ems.getStoredEnchantLevel(enchantment),true);
                ems.removeStoredEnchant(enchantment);
            }
        }
        if(giver.getType().equals(Material.ENCHANTED_BOOK)) {
            EnchantmentStorageMeta ems = (EnchantmentStorageMeta) giverMeta;
            for(Enchantment enchantment : ems.getStoredEnchants().keySet()) {
                int receiverLvl = 0;
                if(receiverMeta!=null&&receiverMeta.hasEnchant(enchantment)) {
                    receiverLvl = receiverMeta.getEnchantLevel(enchantment);
                }
                int giverLvl = ems.getStoredEnchantLevel(enchantment);
                if(receiverLvl>giverLvl) {
                    continue;
                }
                if(receiverLvl==giverLvl&&giverLvl+1<=enchantment.getMaxLevel()) {
                    receiverMeta.addEnchant(enchantment, giverLvl+1, true);
                }else{
                    receiverMeta.addEnchant(enchantment, giverLvl, true);
                }
            }
        }
        for(Enchantment enchantment : giverMeta.getEnchants().keySet()) {
            int receiverLvl = 0;
            if(receiverMeta!=null&&receiverMeta.hasEnchant(enchantment)) {
                receiverLvl = receiverMeta.getEnchantLevel(enchantment);
            }
            int giverLvl = giverMeta.getEnchantLevel(enchantment);
            if(receiverLvl>giverLvl) {
                continue;
            }
            if(receiverLvl==giverLvl&&giverLvl+1<=enchantment.getMaxLevel()) {
                receiverMeta.addEnchant(enchantment, giverLvl+1, true);
            }else{
                receiverMeta.addEnchant(enchantment, giverLvl, true);
            }
        }
        receiverMeta.addItemFlags(org.bukkit.inventory.ItemFlag.HIDE_ENCHANTS);
        receiver.setItemMeta(receiverMeta);
        receiverMeta.setLore(getUpdatedLore(receiver));
        receiver.setItemMeta(receiverMeta);
        return receiver;
    }

    public double calculateMoney(double init, Player player) {
        double result=init;
        Stage stage = plugin.getStageManager().getStage(player.getLocation());
        if(stage!=null) {
            if(stage.isAllowed(player)) {
                result*=stage.getMoneyMultiplier();
            }else{
                return 0;
            }
        }
        if(!itemStack.hasItemMeta()) {
            return result;
        }
        Item item = plugin.getItemManager().getItemFromStack(itemStack);
        if(item==null) {
            if(itemStack.getItemMeta().hasEnchants()) {
                for(Enchant enchant : getCustomEnchants()) {
                    if(enchant.allowUsage(player)) {
                        result*=enchant.getMoneyMultiplier(itemStack.getItemMeta().getEnchantLevel(enchant));
                    }
                }
            }
            return result;
        }
        if(!item.allowUsage(player)) {
            return result;
        }
        if(item instanceof IWeapon) {
            result+=((IWeapon) item).getBaseMoney();
        }
        if(itemStack.getItemMeta().hasEnchants()) {
            for(Enchant enchant : getCustomEnchants()) {
                if(enchant.allowUsage(player)) {
                    result*=enchant.getMoneyMultiplier(itemStack.getItemMeta().getEnchantLevel(enchant));
                }
            }
        }
        return result;
    }

    public double calculateXp(double init, Player player) {
        double result=init;
        Stage stage = plugin.getStageManager().getStage(player.getLocation());
        if(stage!=null) {
            if(stage.isAllowed(player)) {
                result*=stage.getXpMultiplier();
            }else{
                return 0;
            }
        }
        if(!itemStack.hasItemMeta()) {
            return result;
        }
        Item item = plugin.getItemManager().getItemFromStack(itemStack);
        if(item==null) {
            if(itemStack.getItemMeta().hasEnchants()) {
                for(Enchant enchant : getCustomEnchants()) {
                    if(enchant.allowUsage(player)) {
                        result*=enchant.getXpMultiplier(itemStack.getItemMeta().getEnchantLevel(enchant));
                    }
                }
            }
            return result;
        }
        if(!item.allowUsage(player)) {
            return result;
        }
        if(item instanceof IWeapon) {
            result+=((IWeapon) item).getBaseXP();
        }
        if(itemStack.getItemMeta().hasEnchants()) {
            for(Enchant enchant : getCustomEnchants()) {
                if(enchant.allowUsage(player)) {
                    result*=enchant.getXpMultiplier(itemStack.getItemMeta().getEnchantLevel(enchant));
                }
            }
        }
        return result;
    }

    public double calculateDamage(double init, Player player) {
        double result=init;
        if(!itemStack.hasItemMeta()) {
            return init;
        }
        Item item = plugin.getItemManager().getItemFromStack(itemStack);
        if(item==null&&getCustomEnchants().size()==0) {
            return init;
        }
        if(item!=null) {
            if(!item.allowUsage(player)) {
                return result;
            }
            if(item instanceof IWeapon) {
                result+=((IWeapon) item).getBaseDamage();
            }
        }
        if(itemStack.getItemMeta().hasEnchants()) {
            for(Enchant enchant : getCustomEnchants()) {
                if(enchant.allowUsage(player)) {
                    result*=enchant.getDamageMultiplier(itemStack.getItemMeta().getEnchantLevel(enchant));
                }
            }
        }
        return result;
    }

    public HashSet<Enchant> getCustomEnchants() {
        // probably unsafe but idc
        if(!itemStack.hasItemMeta()) {
            return new HashSet<>();
        }
        if(!itemStack.getItemMeta().hasEnchants()) {
            return new HashSet<>();
        }
        HashSet<Enchant> result=new HashSet<>();
        itemStack.getItemMeta().getEnchants().keySet()
                .forEach(enchantment -> {
                    Enchant customEnchant = plugin.getItemManager().getEnchantManager().asCustomEnchant(enchantment);
                    if(customEnchant!=null) {
                        result.add(customEnchant);
                    }
                });
        return result;
    }

    public ArrayList<Flag> getFlags() {
        ArrayList<Flag> result = new ArrayList<>();
        Item item = plugin.getItemManager().getItemFromStack(itemStack);
        if(item!=null) {
            result.addAll(item.getFlags());
        }
        for(Enchant enchant : getCustomEnchants()) {
            result.addAll(enchant.getFlags());
        }
        return result;
    }

}
