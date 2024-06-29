package me.bubbles.bosspve.utility;

import me.bubbles.bosspve.BossPVE;
import me.bubbles.bosspve.flags.Flag;
import me.bubbles.bosspve.flags.ItemFlag;
import me.bubbles.bosspve.items.manager.ItemManager;
import me.bubbles.bosspve.items.manager.bases.enchants.Enchant;
import me.bubbles.bosspve.items.manager.bases.items.Item;
import me.bubbles.bosspve.utility.string.UtilString;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_21_R1.enchantments.CraftEnchantment;
import org.bukkit.craftbukkit.v1_21_R1.inventory.CraftItemStack;
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
    private Item item;
    private BossPVE plugin;

    public UtilItemStack(BossPVE plugin, ItemStack itemStack) {
        this.plugin=plugin;
        this.itemStack=itemStack;
        this.item=plugin.getItemManager().getItemFromStack(itemStack);
    }

    public UtilItemStack(BossPVE plugin, ItemStack itemStack, Item item) {
        this.plugin=plugin;
        this.itemStack=itemStack;
        this.item=item;
    }

    public List<String> getUpdatedLore() {
        return getUpdatedLore(null);
    }

    public List<String> getUpdatedLore(Player player) {
        List<String> lore = new ArrayList<>();
        //Item item = plugin.getItemManager().getItemFromStack(itemStack);
        UtilUserData uud=null;
        if(player!=null) {
            uud=plugin.getGameManager().getGamePlayer(player.getUniqueId()).getCache();
        }
        int enchantsAmt = itemStack.getEnchantments().size();
        if(item!=null) {
            if((!item.getDescription().equals(""))&&item.getDescription()!=null) {
                lore.add(UtilString.colorFillPlaceholders("&8"+item.getDescription()));
            }
        }
        for(Enchantment bukkitEnchantment : itemStack.getEnchantments().keySet()) {
            net.minecraft.world.item.enchantment.Enchantment nmsEnchant = CraftEnchantment.bukkitToMinecraft(bukkitEnchantment);
            Enchant enchant = plugin.getItemManager().getEnchantManager().getEnchant(nmsEnchant.description().getString());
            if(enchant!=null) {
                if(uud!=null) {
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
                        continue;
                    }
                } else {
                    lore.add(ChatColor.translateAlternateColorCodes('&',
                            "&9" + enchant.getName() + " " + itemStack.getItemMeta().getEnchantLevel(bukkitEnchantment)
                    ));
                    if(enchantsAmt<=5) {
                        if(enchant.getDescription()!=null) {
                            lore.add(ChatColor.translateAlternateColorCodes('&',"&7"+enchant.getDescription()));
                        }
                    }
                    continue;
                }
            }/* else {
                lore.add(ChatColor.translateAlternateColorCodes('&',
                        "&9" + bukkitEnchantment.getName() + " " + itemStack.getItemMeta().getEnchantLevel(bukkitEnchantment)
                ));
            }*/
        }
        if(item!=null) {

            if(!this.getFlags().isEmpty()) {
                lore.add("");
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

            // Health

            String health = "%primary%Health:%secondary%";
            double healthAdd = UtilCalculator.getFlagSum(this, ItemFlag.HEALTH_ADD);
            if(healthAdd!=0) {
                health+=" +"+healthAdd;
            }
            double healthMult = UtilCalculator.getFlagProduct(this, ItemFlag.HEALTH_MULT);
            if(healthMult!=1) {
                health+=(healthMult>1 ? " +" : " -");
                health+="(%"+Math.abs((int) ((healthAdd*100)-100))+")";
            }
            if(healthAdd!=0||healthMult!=1) {
                lore.add(UtilString.colorFillPlaceholders(health));
            }

            // Defence

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
        //receiver.setItemMeta(receiverMeta);
        UtilItemStack utilItemStack = new UtilItemStack(plugin, receiver, item);
        receiverMeta.setLore(utilItemStack.getUpdatedLore());
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
                    net.minecraft.world.item.enchantment.Enchantment nmsEnchant = CraftEnchantment.bukkitToMinecraft(enchantment);
                    Enchant enchant = plugin.getItemManager().getEnchantManager().getEnchant(nmsEnchant.description().getString());
                    if(enchant!=null) {
                        result.add(enchant);
                    }
                });
        return result;
    }

    public HashSet<Flag<ItemFlag, Double>> getFlags() {
        HashSet<Flag<ItemFlag, Double>> result = new HashSet<>();
        if(item!=null) {
            result.addAll(item.getFlags());
        }
        for(Enchant enchant : getCustomEnchants()) {
            result.addAll(enchant.getFlags(getEnchantLevel(enchant.getEnchantment())));
        }
        return result;
    }

    public int getEnchantLevel(net.minecraft.world.item.enchantment.Enchantment enchantment) {
        return getEnchantLevel(CraftEnchantment.minecraftToBukkit(enchantment));
    }

    public int getEnchantLevel(Enchantment enchant) {
        if(!itemStack.hasItemMeta()) {
            return -1;
        }
        if(!itemStack.getItemMeta().hasEnchant(enchant)) {
            return -1;
        }
        return itemStack.getItemMeta().getEnchantLevel(enchant);
    }

}
