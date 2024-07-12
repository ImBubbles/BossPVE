package me.bubbles.bosspve.events;

import me.bubbles.bosspve.BossPVE;
import me.bubbles.bosspve.events.manager.Event;
import me.bubbles.bosspve.items.manager.bases.items.Item;
import me.bubbles.bosspve.items.manager.bases.enchants.EnchantItem;
import me.bubbles.bosspve.utility.UtilItemStack;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.logging.Level;

public class UpdateAnvil extends Event {

    public UpdateAnvil() {
        super(PrepareAnvilEvent.class);
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
        Item firstItem = BossPVE.getInstance().getItemManager().getItemFromStack(firstSlot);
        Item secondItem = BossPVE.getInstance().getItemManager().getItemFromStack(secondSlot);
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
        UtilItemStack uiu = new UtilItemStack(firstSlot);
        ItemStack result = uiu.enchantItem(secondSlot);
        result.setAmount(1);
        e.getInventory().setItem(2,result);
        e.setResult(result);
    }

}
