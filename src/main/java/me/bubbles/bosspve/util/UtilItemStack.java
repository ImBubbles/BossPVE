package me.bubbles.bosspve.util;

import me.bubbles.bosspve.BossPVE;
import me.bubbles.bosspve.flags.Flag;
import me.bubbles.bosspve.flags.ItemFlag;
import me.bubbles.bosspve.items.manager.bases.enchants.Enchant;
import me.bubbles.bosspve.items.manager.bases.items.IItem;
import me.bubbles.bosspve.items.manager.bases.items.Item;
import me.bubbles.bosspve.util.string.UtilString;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_20_R3.enchantments.CraftEnchantment;
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
        List<String> lore = new ArrayList<>();
        Item item = plugin.getItemManager().getItemFromStack(itemStack);
        UtilUserData uud = plugin.getGameManager().getGamePlayer(player.getUniqueId()).getCache();
        int enchantsAmt = itemStack.getEnchantments().size();
        boolean cont=true;
        for(Enchantment bukkitEnchantment : itemStack.getEnchantments().keySet()) {
            net.minecraft.world.item.enchantment.Enchantment nmsEnchant = CraftEnchantment.bukkitToMinecraft(bukkitEnchantment);
            if(nmsEnchant instanceof Enchant) {
                Enchant enchant = (Enchant) nmsEnchant;
                if(!(uud.getLevel()>=enchant.getLevelRequirement())) {
                    lore.add(ChatColor.translateAlternateColorCodes('&',
                            "&c" + enchant.getName() + " " + itemStack.getItemMeta().getEnchantLevel(bukkitEnchantment)
                    ));
                } else {
                    lore.add(ChatColor.translateAlternateColorCodes('&',
                            "&9" + enchant.getName() + " " + itemStack.getItemMeta().getEnchantLevel(bukkitEnchantment)
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
                        "&9" + bukkitEnchantment.getName() + " " + itemStack.getItemMeta().getEnchantLevel(bukkitEnchantment)
                ));
            }
        }
        if(item!=null) {
            if((!item.getDescription().equals(""))&&item.getDescription()!=null) {
                lore.add(UtilString.colorFillPlaceholders("&8"+item.getDescription()));
            }
            String damage = "%primary%Damage:%secondary%";
            double dmgAdd = UtilCalculator.getFlagSum(this, ItemFlag.DAMAGE_ADD);
            if(dmgAdd!=0) {
                damage+=" +"+dmgAdd;
            }
            double dmgMult = UtilCalculator.getFlagProduct(this, ItemFlag.DAMAGE_MULT);
            if(dmgMult!=1) {
                damage+=(dmgMult>1 ? " +" : " -");
                damage+="(%"+Math.abs((int) ((dmgMult*100)-100))+")";
            }
            if(dmgAdd!=0||dmgMult!=1) {
                lore.add(UtilString.colorFillPlaceholders(damage));
            }
            String xp = "%primary%XP:%secondary%";
            double xpAdd = UtilCalculator.getFlagSum(this, ItemFlag.XP_ADD);
            if(xpAdd!=0) {
                xp+=" +"+xpAdd;
            }
            double xpMult = UtilCalculator.getFlagProduct(this, ItemFlag.XP_MULT);
            if(xpMult!=1) {
                xp+=(xpMult>1 ? " +" : " -");
                xp+="(%"+Math.abs((int) ((xpMult*100)-100))+")";
            }
            if(xpAdd!=0||xpMult!=1) {
                lore.add(UtilString.colorFillPlaceholders(xp));
            }
            String money = "%primary%Money:%secondary%";
            double moneyAdd = UtilCalculator.getFlagSum(this, ItemFlag.MONEY_ADD);
            if(moneyAdd!=0) {
                money+=" +"+moneyAdd;
            }
            double moneyMult = UtilCalculator.getFlagProduct(this, ItemFlag.MONEY_MULT);
            if(moneyMult!=1) {
                money+=(moneyMult>1 ? " +" : " -");
                money+="(%"+Math.abs((int) ((moneyMult*100)-100))+")";
            }
            if(xpAdd!=0||xpMult!=1) {
                lore.add(UtilString.colorFillPlaceholders(money));
            }
            String defence = "%primary%Defence:%secondary%";
            double protAdd = UtilCalculator.getFlagSum(this, ItemFlag.PROT_ADD);
            if(protAdd!=0) {
                defence+=" +"+protAdd;
            }
            double protMult = UtilCalculator.getFlagProduct(this, ItemFlag.PROT_MULT);
            if(protMult!=1) {
                defence+=(protMult>1 ? " +" : " -");
                defence+="(%"+Math.abs((int) ((protAdd*100)-100))+")";
            }
            if(protAdd!=0||protMult!=1) {
                lore.add(UtilString.colorFillPlaceholders(defence));
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

    public HashSet<Flag<ItemFlag, Double>> getFlags() {
        HashSet<Flag<ItemFlag, Double>> result = new HashSet<>();
        Item item = plugin.getItemManager().getItemFromStack(itemStack);
        if(item!=null) {
            result.addAll(item.getFlags());
        }
        for(Enchant enchant : getCustomEnchants()) {
            result.addAll(enchant.getFlags(getEnchantLevel(enchant)));
        }
        return result;
    }

    public int getEnchantLevel(Enchant enchant) {
        if(!itemStack.hasItemMeta()) {
            return -1;
        }
        /*if(!(getCustomEnchants().contains(enchant))) {
            return -1;
        }*/
        return itemStack.getItemMeta().getEnchantLevel(CraftEnchantment.minecraftToBukkit(enchant));
    }

}
