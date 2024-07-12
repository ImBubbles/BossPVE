package me.bubbles.bosspve.items.enchants;

import me.bubbles.bosspve.BossPVE;
import me.bubbles.bosspve.game.GamePlayer;
import me.bubbles.bosspve.items.manager.bases.enchants.Enchant;
import me.bubbles.bosspve.items.manager.bases.enchants.ProcEnchant;
import me.bubbles.bosspve.items.manager.bases.items.Item;
import me.bubbles.bosspve.stages.Stage;
import me.bubbles.bosspve.utility.UtilItemStack;
import me.bubbles.bosspve.utility.UtilNumber;
import me.bubbles.bosspve.utility.chance.Activation;
import me.bubbles.bosspve.utility.messages.PreparedMessages;
import net.minecraft.world.entity.LivingEntity;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_21_R1.enchantments.CraftEnchantment;
import org.bukkit.craftbukkit.v1_21_R1.entity.CraftEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class Nuker extends ProcEnchant {

    public Nuker() {
        super("Nuker", Material.TNT, 10);
        getEnchantItem().setDisplayName("&c&lNuker");
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
            LivingEntity livingEntity = (LivingEntity) ((CraftEntity) e.getEntity()).getHandle();
            if(livingEntity.getTags().contains("nuked")) {
                return;
            }
            int level = main.getItemMeta().getEnchantLevel(CraftEnchantment.minecraftToBukkit(getEnchantment()))-1;
            /*double addition = (level-1)*(.5);
            if(!UtilNumber.rollTheDice(1,1000,3+addition)) {
                return;
            }*/
            if(!shouldActivate(level)) {
                return;
            }
            /*if(!UtilNumber.rollTheDice(1,100,50)) {
                return;
            }*/
            Stage stage = BossPVE.getInstance().getStageManager().getStage(player.getLocation());
            stage.killAll(player);
            GamePlayer gamePlayer = BossPVE.getInstance().getGameManager().getGamePlayer(player);
            PreparedMessages.onProc(gamePlayer, this);
        }
    }

    @Override
    public String getDescription() {
        return "Chance of killing all mobs in current stage";
    }

    @Override
    public Activation getActivation(int level) {
        double addition = (level-1)*(.5);
        return new Activation(1, 1000, 3+addition);
    }
}
