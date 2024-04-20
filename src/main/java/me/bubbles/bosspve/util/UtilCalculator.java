package me.bubbles.bosspve.util;

import me.bubbles.bosspve.BossPVE;
import me.bubbles.bosspve.items.flags.Flag;
import me.bubbles.bosspve.items.flags.Flags;
import me.bubbles.bosspve.items.manager.bases.items.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class UtilCalculator {

    private static BossPVE plugin;

    public UtilCalculator(BossPVE plugin) {
        this.plugin=plugin;
    }

    public static double getMaxHealth(Player player) {
        int additive=0;
        int multiplier=1;
        ArrayList<Flag> flags = getActiveFlags(player);
        for(Flag flag : flags) {
            if(flag.getName().equals(Flags.HEALTH_ADD)) {
                additive=additive+((Flag<Integer>) flag).getValue();
            }
            if(flag.getName().equals(Flags.HEALTH_MULT)) {
                multiplier=multiplier*((Flag<Integer>) flag).getValue();
            }
        }
        return additive*multiplier;
    }

    public static double getDamage(Player player) {
        int additive=0;
        int multiplier=1;
        ArrayList<Flag> flags = getActiveFlags(player);
        for(Flag flag : flags) {
            if(flag.getName().equals(Flags.DAMAGE_ADD)) {
                additive=additive+((Flag<Integer>) flag).getValue();
            }
            if(flag.getName().equals(Flags.DAMAGE_MULT)) {
                multiplier=multiplier*((Flag<Integer>) flag).getValue();
            }
        }
        return additive*multiplier;
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
            Item armorPiece = plugin.getItemManager().getItemFromStack(itemStack);
            for(Flag flag : armorPiece.getFlags()) {
                if(!flag.isPassive()) {
                    flags.add(flag);
                }
            }
        }

        return flags;

    }

}
