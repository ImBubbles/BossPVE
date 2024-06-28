package me.bubbles.bosspve.items.enchants;

import me.bubbles.bosspve.BossPVE;
import me.bubbles.bosspve.flags.Flag;
import me.bubbles.bosspve.flags.ItemFlag;
import me.bubbles.bosspve.game.GamePlayer;
import me.bubbles.bosspve.items.manager.bases.enchants.Enchant;
import me.bubbles.bosspve.items.manager.bases.items.Item;
import me.bubbles.bosspve.utility.UtilItemStack;
import me.bubbles.bosspve.utility.messages.PreparedMessages;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class Bloodsucker extends Enchant {

    public Bloodsucker(BossPVE plugin) {
        super(plugin, "Bloodsucker", Material.PRISMARINE_SHARD, 5);
        getEnchantItem().setDisplayName("&cBloodsucker");
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
            GamePlayer gamePlayer = plugin.getGameManager().getGamePlayer(player);
            gamePlayer.healPercent(0.05*level);
            PreparedMessages.onProc(gamePlayer, this);
        }
    }


    @Override
    public String getDescription() {
        return "Heal when killing an enemy";
    }

}
