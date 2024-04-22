package me.bubbles.bosspve.util;

import me.bubbles.bosspve.BossPVE;
import me.bubbles.bosspve.entities.manager.IEntity;
import me.bubbles.bosspve.flags.Flag;
import me.bubbles.bosspve.flags.ItemFlag;
import me.bubbles.bosspve.stages.Stage;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class UtilCalculator {

    private static BossPVE plugin;

    public UtilCalculator(BossPVE plugin) {
        this.plugin=plugin;
    }

    public static double getMaxHealth(Player player) {
        int base = 10;
        int additive=getFlagSum(player, ItemFlag.HEALTH_ADD);
        double multiplier=getFlagSum(player, ItemFlag.HEALTH_MULT);
        return (base+additive)*multiplier;
    }

    public static double getDamage(Player player) {
        int base = 2;
        int additive=getFlagSum(player, ItemFlag.DAMAGE_ADD);
        double multiplier=getFlagSum(player, ItemFlag.DAMAGE_MULT);
        return (base+additive)*multiplier;
    }

    public static double getProtection(Player player) {
        int additive=getFlagSum(player, ItemFlag.PROT_ADD);
        double multiplier=getFlagSum(player, ItemFlag.PROT_MULT);
        return additive*multiplier;
    }

    public static double getMoney(Player player, IEntity iEntity) {
        double additive=getFlagSum(player, ItemFlag.MONEY_ADD);
        if(iEntity!=null) {
            additive+=iEntity.getUtilEntity().getMoney();
        }
        double multiplier=getFlagSum(player, ItemFlag.MONEY_MULT);
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
            additive+=iEntity.getUtilEntity().getXp();
        }
        int multiplier=getFlagSum(player, ItemFlag.XP_MULT);
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
        int result = 0;
        ArrayList<Flag> flags = getActiveFlags(player);
        for(Flag active : flags) {
            if(!(active.getValue() instanceof ItemFlag)) {
                continue;
            }
            if(active.getValue().equals(flag)) {
                result+=((Flag<ItemFlag, Double>) active).getValue();
            }
        }
        return result;
    }

    public static ArrayList<Flag> getActiveFlags(Player player) {
        ArrayList<Flag> flags = new ArrayList<>();

        // ADD ALL PASSIVE

        for(ItemStack itemStack : player.getInventory()) {
            UtilItemStack uis = new UtilItemStack(plugin, itemStack);
            for(Flag flag : uis.getFlags()) {
                if(flag.isPassive()) {
                    flags.add(flag);
                }
            }
        }

        // IN HANDS

        UtilItemStack mainHand = new UtilItemStack(plugin, player.getInventory().getItemInMainHand());
        UtilItemStack offHand = new UtilItemStack(plugin, player.getInventory().getItemInOffHand());

        for(Flag flag : mainHand.getFlags()) {
            if(!flag.isPassive()) {
                flags.add(flag);
            }
        }

        for(Flag flag : offHand.getFlags()) {
            if(!flag.isPassive()) {
                flags.add(flag);
            }
        }

        // ARMOR CONTENTS

        for(ItemStack itemStack : player.getInventory().getArmorContents()) {
            UtilItemStack armorPiece = new UtilItemStack(plugin, itemStack);
            for(Flag flag : armorPiece.getFlags()) {
                if(!flag.isPassive()) {
                    flags.add(flag);
                }
            }
        }

        return flags;

    }

}
