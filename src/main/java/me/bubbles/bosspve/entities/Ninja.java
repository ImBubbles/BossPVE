package me.bubbles.bosspve.entities;

import me.bubbles.bosspve.BossPVE;
import me.bubbles.bosspve.entities.manager.IEntity;
import me.bubbles.bosspve.flags.EntityFlag;
import me.bubbles.bosspve.flags.Flag;
import me.bubbles.bosspve.utility.UtilEntity;
import me.bubbles.bosspve.utility.UtilNumber;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.monster.Skeleton;
import net.minecraft.world.level.Level;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_21_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_21_R1.inventory.CraftItemStack;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Ninja extends Skeleton implements IEntity {

    private final String customName = ChatColor.translateAlternateColorCodes('&',"&8&lNinja");

    private BossPVE plugin;
    private UtilEntity utilEntity;

    public Ninja(BossPVE plugin) {
        this(plugin, ((CraftWorld) Bukkit.getWorlds().get(0)).getHandle().getWorld().getHandle(), null);
    }

    public Ninja(BossPVE plugin, Level level, Location location) {
        super(EntityType.SKELETON, level);
        this.plugin=plugin;
        this.utilEntity=new UtilEntity(this);
        if(location!=null) {
            setPos(location.getX(),location.getY(),location.getZ());
        } else {
            remove(RemovalReason.DISCARDED);
            return;
        }
        setCustomNameVisible(true);
        setCustomName(Component.literal(ChatColor.translateAlternateColorCodes('&',customName)));
        getAttribute(Attributes.MAX_HEALTH).setBaseValue(utilEntity.getMaxHealth());
        setHealth((float) utilEntity.getMaxHealth());
        goalSelector.addGoal(0, new RangedBowAttackGoal<>(
                this, 1, 1, 5
        ));
        goalSelector.addGoal(0, new MeleeAttackGoal(
                this, 1, false
        ));
        goalSelector.addGoal(1, new PanicGoal(
                this, 1.5D
        ));
        goalSelector.addGoal(2, new WaterAvoidingRandomStrollGoal(
                this, 0.6D
        ));
        goalSelector.addGoal(3, new RandomLookAroundGoal(
                this
        ));
        setItemInHand(InteractionHand.MAIN_HAND, CraftItemStack.asNMSCopy(new ItemStack(Material.SHEARS)));
        setItemInHand(InteractionHand.OFF_HAND,CraftItemStack.asNMSCopy(new ItemStack(Material.SHEARS)));
        setItemSlot(EquipmentSlot.FEET, plugin.getItemManager().getItemByName("ninjaBoots").getNMSStack());
        setItemSlot(EquipmentSlot.LEGS, plugin.getItemManager().getItemByName("ninjaPants").getNMSStack());
        setItemSlot(EquipmentSlot.CHEST, plugin.getItemManager().getItemByName("ninjaChestplate").getNMSStack());
        setItemSlot(EquipmentSlot.HEAD, plugin.getItemManager().getItemByName("ninjaHelmet").getNMSStack());
        if(getDrops()!=null) {
            drops.clear();
            drops.addAll(getDrops());
        }
        addTag(getNBTIdentifier());
    }

    @Override
    public Entity spawn(Location location) {
        Entity entity = new Ninja(plugin, ((CraftWorld) location.getWorld()).getHandle(), location);
        ((CraftWorld) location.getWorld()).addEntityToWorld(entity, CreatureSpawnEvent.SpawnReason.CUSTOM);
        return entity;
    }

    @Override
    public List<ItemStack> getDrops() {
        List<ItemStack> result=new ArrayList<>();
        // ENCHANTS
        if(UtilNumber.rollTheDice(1,100,2)) {
            result.add(plugin.getItemManager().getItemByName("bankerEnch").nmsAsItemStack());
        }
        if(UtilNumber.rollTheDice(1,100,2)) {
            result.add(plugin.getItemManager().getItemByName("grinderEnch").nmsAsItemStack());
        }
        /*if(UtilNumber.rollTheDice(1,1000,2)) {
            result.add(plugin.getItemManager().getItemByName("nukerEnch").nmsAsItemStack());
        }*/
        // NINJA SET
        if(UtilNumber.rollTheDice(1,800,1)) {
            result.add(plugin.getItemManager().getItemByName("ninjaDagger").nmsAsItemStack());
        }
        if(UtilNumber.rollTheDice(1,400,1)) {
            result.add(plugin.getItemManager().getItemByName("ninjaHelmet").nmsAsItemStack());
        }
        if(UtilNumber.rollTheDice(1,500,1)) {
            result.add(plugin.getItemManager().getItemByName("ninjaChestplate").nmsAsItemStack());
        }
        if(UtilNumber.rollTheDice(1,450,1)) {
            result.add(plugin.getItemManager().getItemByName("ninjaPants").nmsAsItemStack());
        }
        if(UtilNumber.rollTheDice(1,400,1)) {
            result.add(plugin.getItemManager().getItemByName("ninjaBoots").nmsAsItemStack());
        }
        return result;
    }

    @Override
    public HashSet<Flag<EntityFlag, Double>> getFlags() {
        HashSet<Flag<EntityFlag, Double>> result = new HashSet<>();
        result.add(new Flag<>(EntityFlag.MAX_HEALTH, 70D, false));
        result.add(new Flag<>(EntityFlag.MONEY, 15D, false));
        result.add(new Flag<>(EntityFlag.XP, 12D, false));
        result.add(new Flag<>(EntityFlag.DAMAGE, 45D, false));
        return result;
    }

    @Override
    public UtilEntity getUtilEntity() {
        return utilEntity;
    }

    @Override
    public String getNBTIdentifier() {
        return "ninja";
    }

    @Override
    public String getShowName() {
        return customName;
    }

}