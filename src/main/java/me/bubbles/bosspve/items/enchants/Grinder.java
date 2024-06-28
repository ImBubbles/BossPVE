package me.bubbles.bosspve.items.enchants;

import me.bubbles.bosspve.BossPVE;
import me.bubbles.bosspve.game.GamePlayer;
import me.bubbles.bosspve.items.manager.ItemManager;
import me.bubbles.bosspve.items.manager.bases.enchants.Enchant;
import me.bubbles.bosspve.items.manager.bases.items.Item;
import me.bubbles.bosspve.utility.UtilItemStack;
import me.bubbles.bosspve.utility.UtilNumber;
import me.bubbles.bosspve.utility.messages.PreparedMessages;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class Grinder extends Enchant {

    public Grinder(BossPVE plugin) {
        super(plugin, "Grinder", Material.EMERALD, 20);
        getEnchantItem().setDisplayName("&e&lGrinder");
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
            int level = new UtilItemStack(plugin, main).getEnchantLevel(getEnchantment());
            double addition = level-1*(.5);
            if(UtilNumber.rollTheDice(1,1000,3+addition)) {
                GamePlayer gamePlayer = plugin.getGameManager().getGamePlayer(player);
                gamePlayer.give(level*200, 0, null, false);
                PreparedMessages.onProc(gamePlayer, this);
            }
        }
    }

    @Override
    public String getDescription() {
        return "Chance of getting xp when killing mobs";
    }

}
