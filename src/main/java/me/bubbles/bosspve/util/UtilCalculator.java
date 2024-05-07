package me.bubbles.bosspve.util;

import me.bubbles.bosspve.BossPVE;
import me.bubbles.bosspve.entities.manager.IEntity;
import me.bubbles.bosspve.flags.Flag;
import me.bubbles.bosspve.flags.ItemFlag;
import me.bubbles.bosspve.stages.Stage;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashSet;

public class UtilCalculator {

    private static BossPVE plugin;

    public UtilCalculator(BossPVE plugin) {
        this.plugin=plugin;
    }

    public static double getMaxHealth(Player player) {
        int base = 10;
        int additive=getFlagSum(player, ItemFlag.HEALTH_ADD);
        double multiplier=getFlagProduct(player, ItemFlag.HEALTH_MULT, 1);
        return (base+additive)*multiplier;
    }

    public static double getDamage(Player player) {
        int base = 3;
        int additive=getFlagSum(player, ItemFlag.DAMAGE_ADD);
        double multiplier=getFlagProduct(player, ItemFlag.DAMAGE_MULT);
        return (base+additive)*multiplier;
    }

    public static double getProtection(Player player) {
        int additive=getFlagSum(player, ItemFlag.PROT_ADD);
        double multiplier=getFlagProduct(player, ItemFlag.PROT_MULT, 1);
        return additive*multiplier;
    }

    public static double getMoney(Player player, IEntity iEntity) {
        double additive=getFlagSum(player, ItemFlag.MONEY_ADD);
        if(iEntity!=null) {
            additive+=iEntity.getUtilEntity().getMoney();
        }
        double multiplier=getFlagProduct(player, ItemFlag.MONEY_MULT, 1);
        Stage stage = plugin.getStageManager().getStage(player.getLocation());
        if(stage!=null) {
            if(stage.isAllowed(player)) {
                multiplier*=stage.getMoneyMultiplier();
            } else {
                return 0D;
            }
        }
        return additive*multiplier;
    }

    public static double getXp(Player player, IEntity iEntity) {
        int additive=getFlagSum(player, ItemFlag.XP_ADD);
        if(iEntity!=null) {
            additive+=(int) iEntity.getUtilEntity().getXp();
        }
        int multiplier=getFlagProduct(player, ItemFlag.XP_MULT, 1);
        Stage stage = plugin.getStageManager().getStage(player.getLocation());
        if(stage!=null) {
            if(stage.isAllowed(player)) {
                multiplier*=stage.getXpMultiplier();
            } else {
                return 0D;
            }
        }
        return additive*multiplier;
    }

    public static int getFlagSum(Player player, ItemFlag flag) {
        return getFlagSum(player, flag, 0);
    }

    public static int getFlagSum(Player player, ItemFlag flag, int orElse) {
        int result = orElse;
        HashSet<Flag<ItemFlag, Double>> flags = getActiveFlags(player);
        for(Flag<ItemFlag, Double> active : flags) {
            if(active.getFlag().equals(flag)) {
                result+=active.getValue();
            }
        }
        return result;
    }

    public static double getFlagSum(UtilItemStack uis, ItemFlag itemFlag) {
        return getFlagSum(uis, itemFlag, 0);
    }

    public static double getFlagSum(UtilItemStack uis, ItemFlag itemFlag, int orElse) {
        double result = orElse;
        HashSet<Flag<ItemFlag, Double>> flags = uis.getFlags();
        for(Flag<ItemFlag, Double> flag : flags) {
            if(flag.getFlag().equals(itemFlag)) {
                result+=(flag).getValue();
            }
        }
        return result;
    }

    public static int getFlagProduct(Player player, ItemFlag flag) {
        return getFlagProduct(player, flag, 1);
    }

    public static int getFlagProduct(Player player, ItemFlag flag, int orElse) {
        int result = orElse;
        HashSet<Flag<ItemFlag, Double>> flags = getActiveFlags(player);
        for(Flag<ItemFlag, Double> active : flags) {
            if(active.getFlag().equals(flag)) {
                result*=active.getValue();
            }
        }
        return result;
    }

    public static double getFlagProduct(UtilItemStack uis, ItemFlag itemFlag) {
        return getFlagProduct(uis, itemFlag, 1);
    }

    public static double getFlagProduct(UtilItemStack uis, ItemFlag itemFlag, int orElse) {
        double result = orElse;
        HashSet<Flag<ItemFlag, Double>> flags = uis.getFlags();
        for(Flag<ItemFlag, Double> flag : flags) {
            if(flag.getFlag().equals(itemFlag)) {
                result*=(flag).getValue();
            }
        }
        return result;
    }

    public static HashSet<Flag<ItemFlag, Double>> getActiveFlags(Player player) {
        HashSet<Flag<ItemFlag, Double>> flags = new HashSet<>();

        // ADD ALL PASSIVE

        for(ItemStack itemStack : player.getInventory()) {
            if(itemStack==null) {
                continue;
            }
            UtilItemStack uis = new UtilItemStack(plugin, itemStack);
            for(Flag<ItemFlag, Double> flag : uis.getFlags()) {
                if(flag.isPassive()) {
                    flags.add(flag);
                }
            }
        }

        // IN HANDS
        ItemStack mainHandStack = player.getInventory().getItemInMainHand();
        if(mainHandStack!=null) {
            UtilItemStack mainHand = new UtilItemStack(plugin, player.getInventory().getItemInMainHand());
            for(Flag<ItemFlag, Double> flag : mainHand.getFlags()) {
                if(!flag.isPassive()) {
                    flags.add(flag);
                }
            }
        }
        ItemStack offHandStack = player.getInventory().getItemInOffHand();
        if(offHandStack!=null) {
            UtilItemStack offHand = new UtilItemStack(plugin, player.getInventory().getItemInOffHand());
            for(Flag<ItemFlag, Double> flag : offHand.getFlags()) {
                if(!flag.isPassive()) {
                    flags.add(flag);
                }
            }
        }

        // ARMOR CONTENTS

        for(ItemStack itemStack : player.getInventory().getArmorContents()) {
            if(itemStack==null) {
                continue;
            }
            UtilItemStack armorPiece = new UtilItemStack(plugin, itemStack);
            for(Flag<ItemFlag, Double> flag : armorPiece.getFlags()) {
                if(!flag.isPassive()) {
                    flags.add(flag);
                }
            }
        }

        return flags;

    }

}
