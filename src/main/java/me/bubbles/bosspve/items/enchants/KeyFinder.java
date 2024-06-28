package me.bubbles.bosspve.items.enchants;

import me.bubbles.bosspve.BossPVE;
import me.bubbles.bosspve.items.manager.bases.enchants.Enchant;
import me.bubbles.bosspve.items.manager.bases.items.Item;
import me.bubbles.bosspve.utility.UtilCalculator;
import me.bubbles.bosspve.utility.UtilItemStack;
import me.bubbles.bosspve.utility.UtilNumber;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class KeyFinder extends Enchant {

    public KeyFinder(BossPVE plugin) {
        super(plugin, "Key Finder", Material.TRIPWIRE_HOOK, 20);
        getEnchantItem().setDisplayName("&dKey Finder");
        allowedTypes.addAll(
                Arrays.asList(
                        Item.Type.WEAPON
                )
        );
    }

    @Override
    public void onEvent(Event event) {
        if(event instanceof EntityDeathEvent) {
            EntityDeathEvent e = (EntityDeathEvent) event;
            if(e.getEntity().getKiller()==null) {
                return;
            }
            Player player = e.getEntity().getKiller();
            ItemStack main = player.getInventory().getItemInMainHand();
            if(!containsEnchant(main)) {
                return;
            }
            //int level = main.getItemMeta().getEnchantLevel(this);
            int level = new UtilItemStack(plugin, main).getEnchantLevel(getEnchantment());
            double addition = level-1*(.25);
            if(UtilNumber.rollTheDice(1,1000,3+addition)) {
                giveKey(player,"Stage",level);
            }
            if(UtilNumber.rollTheDice(1,2000,1+addition)) {
                giveKey(player,"Enchant",level);
            }
            if(UtilNumber.rollTheDice(1,10000,1+addition)) {
                giveKey(player,"Rank",1);
            }
        }
    }

    private void giveKey(Player player, String key, int amt) {
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(),"cc give v "+key+" "+amt+" "+player.getName());
    }

    @Override
    public String getDescription() {
        return "Chance of getting keys when killing mobs";
    }

}
