package me.bubbles.bosspve.items.enchants;

import me.bubbles.bosspve.BossPVE;
import me.bubbles.bosspve.items.manager.bases.enchants.Enchant;
import me.bubbles.bosspve.items.manager.bases.items.Item;
import me.bubbles.bosspve.ticker.Timer;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_21_R1.enchantments.CraftEnchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Arrays;
import java.util.HashMap;

public class Speed extends Enchant {

    private Timer timer;

    public Speed(BossPVE plugin) {
        super(plugin, "Speed", Material.FEATHER, 30);
        getEnchantItem().setDisplayName("&fSpeed");
        timer=new Timer(plugin,20);
        plugin.getTimerManager().addTimer(timer);
        allowedTypes.addAll(
                Arrays.asList(
                        Item.Type.WEAPON
                )
        );
    }

    @Override
    public void onTick() {
        if(timer.isActive()) {
            return;
        }
        HashMap<Player, ItemStack> list = playersWithEnchantInAnyHand();
        list.forEach((player, itemStack) -> {
            if(!allowUsage(player)) {
                return;
            }
            PotionEffect speed = new PotionEffect(PotionEffectType.SPEED,40,itemStack.getItemMeta().getEnchantLevel(CraftEnchantment.minecraftToBukkit(getEnchantment()))-1);
            player.addPotionEffect(speed);
        });
        timer.restart();
    }

    @Override
    public String getDescription() {
        return "Gives you speed";
    }

}
