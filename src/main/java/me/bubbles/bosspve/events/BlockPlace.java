package me.bubbles.bosspve.events;

import me.bubbles.bosspve.BossPVE;
import me.bubbles.bosspve.events.manager.Event;
import me.bubbles.bosspve.items.manager.bases.items.Item;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

public class BlockPlace extends Event {

    public BlockPlace() {
        super(BlockPlaceEvent.class);
    }

    @Override
    public void onEvent(org.bukkit.event.Event event) {
        BlockPlaceEvent e = (BlockPlaceEvent) event;
        ItemStack itemStack = e.getItemInHand();
        if(itemStack==null) {
            return;
        }
        Item item = BossPVE.getInstance().getItemManager().getItemFromStack(itemStack);
        if(item==null) {
            return;
        }
        if(item.allowPlace()) {
            return;
        }
        e.setCancelled(true);
    }

}
