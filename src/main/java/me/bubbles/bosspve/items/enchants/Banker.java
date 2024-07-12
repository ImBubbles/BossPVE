package me.bubbles.bosspve.items.enchants;

import me.bubbles.bosspve.BossPVE;
import me.bubbles.bosspve.game.GamePlayer;
import me.bubbles.bosspve.items.manager.bases.enchants.Enchant;
import me.bubbles.bosspve.items.manager.bases.enchants.ProcEnchant;
import me.bubbles.bosspve.items.manager.bases.items.Item;
import me.bubbles.bosspve.utility.UtilItemStack;
import me.bubbles.bosspve.utility.UtilNumber;
import me.bubbles.bosspve.utility.chance.Activation;
import me.bubbles.bosspve.utility.messages.PreparedMessages;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class Banker extends ProcEnchant {

    public Banker() {
        super("Banker", Material.EMERALD, 20);
        getEnchantItem().setDisplayName("&a&lBanker");
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
            int level = new UtilItemStack(main).getEnchantLevel(getEnchantment());
            //double addition = level-1*(.5);
            if(shouldActivate(level)) {
                GamePlayer gamePlayer = BossPVE.getInstance().getGameManager().getGamePlayer(player);
                gamePlayer.give(0, level*1000, null, false);
                PreparedMessages.onProc(gamePlayer, this);
            }
            /*if(UtilNumber.rollTheDice(1,1000,3+addition)) {
                GamePlayer gamePlayer = plugin.getGameManager().getGamePlayer(player);
                gamePlayer.give(0, level*1000, null, false);
                PreparedMessages.onProc(gamePlayer, this);
                *//*UtilUser utilUser = new UtilUser(plugin,player);
                utilUser.giveMoney(level*1000,true);
                new UtilPlayerMessage(plugin,player).onProc(this);*//*
            }*/
        }
    }

    @Override
    public String getDescription() {
        return "Chance of getting money when killing mobs";
    }

    @Override
    public Activation getActivation(int level) {
        double addition = level-1*(.5);
        return new Activation(1, 1000, 3+addition);
    }

}
