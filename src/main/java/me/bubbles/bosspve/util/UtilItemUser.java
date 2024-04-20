package me.bubbles.bosspve.util;

import me.bubbles.bosspve.BossPVE;
import me.bubbles.bosspve.items.manager.bases.items.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class UtilItemUser extends UtilItemStack {

    private BossPVE plugin;
    private ItemStack itemStack;
    private Item item;
    private Player player;

    public UtilItemUser(BossPVE plugin, ItemStack itemStack, Item item, Player player) {
        super(plugin,itemStack);
        this.plugin=plugin;
        this.itemStack=itemStack;
        this.item=item;
        this.player=player;
    }

    public void sendMessage(String message) {
        new UtilPlayerMessage(plugin,player).sendMessage(MessageType.OTHER, message);
    }

}
