package me.bubbles.bosspve.events;

import me.bubbles.bosspve.BossPVE;
import me.bubbles.bosspve.events.manager.Event;
import me.bubbles.bosspve.game.GamePlayer;
import me.bubbles.bosspve.utility.UtilCalculator;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.ItemStack;

public class UpdateLore extends Event {

    public UpdateLore(BossPVE plugin) {
        super(plugin, PlayerItemHeldEvent.class);
    }

    @Override
    public void onEvent(org.bukkit.event.Event event) {
        PlayerItemHeldEvent e = (PlayerItemHeldEvent) event;
        Player player = e.getPlayer();
        ItemStack itemStack = player.getInventory().getItem(e.getNewSlot());
        if(itemStack==null) {
            return;
        }
        if(itemStack.getType().equals(Material.AIR)) {
            return;
        }
        if(!itemStack.hasItemMeta()) {
            return;
        }
        /*Item item = plugin.getItemManager().getItemFromStack(itemStack);
        UtilItemStack uis = new UtilItemStack(plugin, itemStack, item);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setLore(uis.getUpdatedLore(e.getPlayer()));
        itemStack.setItemMeta(itemMeta);
        player.getInventory().setItem(e.getNewSlot(),itemStack);*/
        GamePlayer gamePlayer = plugin.getGameManager().getGamePlayer(player);
        gamePlayer.setMaxHealth(UtilCalculator.getMaxHealth(player));
    }

    /*public boolean isVoucher(ItemStack itemStack) {
        if(itemStack==null) {
            return false;
        }
        if(!itemStack.hasItemMeta()) {
            return false;
        }
        if(itemStack.getData()==null) {
            return false;
        }
        CompoundTag nbtTagCompound = CraftItemStack.asNMSCopy(itemStack).getOrCreateTag();
        return !nbtTagCompound.getString("Tweetzy:Vouchers").equals("");
    }*/

}
