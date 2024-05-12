package me.bubbles.bosspve.items.enchants;

import me.bubbles.bosspve.BossPVE;
import me.bubbles.bosspve.game.GamePlayer;
import me.bubbles.bosspve.items.manager.bases.enchants.Enchant;
import me.bubbles.bosspve.items.manager.bases.items.Item;
import me.bubbles.bosspve.stages.Stage;
import me.bubbles.bosspve.utility.UtilItemStack;
import me.bubbles.bosspve.utility.UtilNumber;
import me.bubbles.bosspve.utility.messages.PreparedMessages;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_20_R3.entity.CraftLivingEntity;
import org.bukkit.craftbukkit.v1_20_R3.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.util.Arrays;

public class Throw extends Enchant {

    public Throw(BossPVE plugin) {
        super(plugin, Rarity.RARE, "Throw", Material.LIGHTNING_ROD, 10);
        getEnchantItem().setDisplayName("&c&lThrow");
        allowedTypes.addAll(
                Arrays.asList(
                        Item.Type.WEAPON
                )
        );
    }

    @Override
    public void onEvent(Event event) {
        if(event instanceof EntityDamageByEntityEvent) {
            EntityDamageByEntityEvent e = (EntityDamageByEntityEvent) event;
            if(!(e.getDamager() instanceof Player)) {
                return;
            }
            Player player = ((Player) e.getDamager());
            ItemStack main = player.getInventory().getItemInMainHand();
            if(!containsEnchant(main)) {
                return;
            }
            Entity entity = e.getEntity();
            if(entity instanceof Player) {
                Stage stage = plugin.getStageManager().getStage(e.getEntity().getLocation());
                if(stage!=null) {
                    return;
                }
            }
            UtilItemStack uis = new UtilItemStack(plugin, main);
            int level = uis.getEnchantLevel(this);
            double addition = level-1*(.25);
            if(!UtilNumber.rollTheDice(1,100,3+addition)) {
                return;
            }
            Vector up = new Vector(entity.getVelocity().getX(),2*Math.min(level,4),entity.getVelocity().getZ());
            entity.setVelocity(up);
            net.minecraft.world.entity.LivingEntity nmsEntity = ((CraftLivingEntity) entity).getHandle();
            nmsEntity.setLastHurtByPlayer(((CraftPlayer) player).getHandle());
            CraftLivingEntity livingEntity=((CraftLivingEntity) entity);
            double result = UtilNumber.clampBorder(livingEntity.getHealth(), 0, livingEntity.getHealth()-(livingEntity.getMaxHealth()/(1.5*level)));
            if(result>0) {
                livingEntity.setHealth(result);
            } else {
                nmsEntity.kill();
            }
            //entity.setLastDamageCause(new EntityDamageByEntityEvent(player , entity, EntityDamageEvent.DamageCause.ENTITY_ATTACK, 1.5*level));
            //restartCoolDown(player);
            GamePlayer gamePlayer = plugin.getGameManager().getGamePlayer(player);
            PreparedMessages.onProc(gamePlayer, this);
        }
    }

    @Override
    public String getDescription() {
        return "Launch your enemies!";
    }

}
