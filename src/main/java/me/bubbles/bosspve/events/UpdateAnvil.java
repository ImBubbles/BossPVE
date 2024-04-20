package me.bubbles.bosspve.events;

import me.bubbles.bosspve.BossPVE;
import me.bubbles.bosspve.events.manager.Event;
import me.bubbles.bosspve.items.manager.Item;
import me.bubbles.bosspve.items.manager.enchant.EnchantItem;
import me.bubbles.bosspve.util.UtilItemStack;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.inventory.ItemStack;

public class UpdateAnvil extends Event {

    public UpdateAnvil(BossPVE plugin) {
        super(plugin, PrepareAnvilEvent.class);
    }

    @Override
    public void onEvent(org.bukkit.event.Event event) {
        PrepareAnvilEvent e = (PrepareAnvilEvent) event;
        if(e.getInventory().getContents()[0]==null||e.getInventory().getContents()[1]==null) {
            return;
        }
        ItemStack firstSlot = e.getInventory().getContents()[0];
        ItemStack secondSlot = e.getInventory().getContents()[1];
        if(!firstSlot.hasItemMeta()&&!secondSlot.hasItemMeta()) {
            return;
        }
        if(firstSlot.getAmount()>1) {
            return;
        }
        if(secondSlot.getAmount()>1) {
            return;
        }
        Item firstItem = plugin.getItemManager().getItemFromStack(firstSlot);
        Item secondItem = plugin.getItemManager().getItemFromStack(secondSlot);
        if(firstItem!=null) {
            if(firstItem instanceof EnchantItem) {
                return;
            }
            if(secondItem!=null) {
                if(firstItem!=secondItem) {
                    return;
                }
            }
        }
        if(secondItem!=null) {
            if(secondItem instanceof EnchantItem) {
                return;
            }
        }
        if(!(firstSlot.getType().equals(secondSlot.getType()))) {
            return;
        }
        UtilItemStack uiu = new UtilItemStack(plugin,firstSlot);
        ItemStack result = uiu.enchantItem(secondSlot);
        e.getInventory().setItem(2,result);
        result.setAmount(1);
        e.setResult(result);
    }

}
