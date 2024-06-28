package me.bubbles.bosspve.items.enchants;

import me.bubbles.bosspve.BossPVE;
import me.bubbles.bosspve.items.manager.bases.enchants.Enchant;
import me.bubbles.bosspve.items.manager.bases.items.Item;
import org.bukkit.Material;

import java.util.Arrays;

public class Nuker extends Enchant {

    public Nuker(BossPVE plugin) {
        super(plugin, "Nuker", Material.TNT, 10);
        getEnchantItem().setDisplayName("&c&lNuker");
        allowedTypes.addAll(
                Arrays.asList(
                        Item.Type.WEAPON
                )
        );
    }

/*    @Override
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
            int level = new UtilItemStack(plugin, main).getEnchantLevel(this);
            double addition = level-1*(.5);
            if(!UtilNumber.rollTheDice(1,1000,3+addition)) {
                return;
            }
            Stage stage = plugin.getStageManager().getStage(player.getLocation());
            stage.killAll(player);
            GamePlayer gamePlayer = plugin.getGameManager().getGamePlayer(player);
            PreparedMessages.onProc(gamePlayer, this);
        }
    }*/

    @Override
    public String getDescription() {
        return "Chance of killing all mobs in current stage";
    }

}
