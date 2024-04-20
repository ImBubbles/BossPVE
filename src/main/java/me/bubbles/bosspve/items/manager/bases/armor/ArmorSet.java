package me.bubbles.bosspve.items.manager.bases.armor;

import me.bubbles.bosspve.BossPVE;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.HashSet;

public abstract class ArmorSet implements IArmorSet {

    public BossPVE plugin;

    public ArmorSet(BossPVE plugin) {
        this.plugin=plugin;
    }

    public boolean wearingFullSet(Player player, boolean permissionCheck) {
        PlayerInventory inventory = player.getInventory();
        ItemStack boots=inventory.getBoots();
        ItemStack pants=inventory.getLeggings();
        ItemStack chestplate=inventory.getChestplate();
        ItemStack helmet=inventory.getHelmet();
        if(!getBoots().equals(boots)) {
            return false;
        }
        if(!getPants().equals(pants)) {
            return false;
        }
        if(!getChestplate().equals(chestplate)) {
            return false;
        }
        if(!getHelmet().equals(helmet)) {
            return false;
        }
        if(permissionCheck) {
            return (getBoots().allowUsage(player)&&
                    getPants().allowUsage(player)&&
                    getChestplate().allowUsage(player)&&
                    getHelmet().allowUsage(player));
        }
        return true;
    }

    public HashSet<Player> getPlayersWearingFullSet(boolean permissionCheck) {
        HashSet<Player> result = new HashSet<>();
        Bukkit.getOnlinePlayers().stream().filter(player -> wearingFullSet(player,permissionCheck)).forEach(result::add);
        return result;
    }

}
