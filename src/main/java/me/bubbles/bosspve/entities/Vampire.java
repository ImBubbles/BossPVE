package me.bubbles.bosspve.entities;

import me.bubbles.bosspve.BossPVE;
import me.bubbles.bosspve.entities.manager.IEntity;
import me.bubbles.bosspve.flags.EntityFlag;
import me.bubbles.bosspve.flags.Flag;
import me.bubbles.bosspve.items.manager.bases.enchants.EnchantItem;
import me.bubbles.bosspve.utility.CustomEntityData;
import me.bubbles.bosspve.utility.chance.Drop;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.PanicGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.monster.Stray;
import net.minecraft.world.level.Level;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_21_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_21_R1.inventory.CraftItemStack;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Vampire extends Stray implements IEntity {

    private final String customName = ChatColor.translateAlternateColorCodes('&',"&4&lVampire");
    private CustomEntityData entityData;

    public Vampire() {
        this(((CraftWorld) Bukkit.getWorlds().get(0)).getHandle().getWorld().getHandle(), null);
    }

    public Vampire(Level level, Location location) {
        super(EntityType.STRAY, level);
        this.entityData =new CustomEntityData(this);
        if(location!=null) {
            setPos(location.getX(),location.getY(),location.getZ());
        } else {
            remove(RemovalReason.DISCARDED);
            return;
        }
        setCustomNameVisible(true);
        expToDrop=0;
        setCustomName(Component.literal(ChatColor.translateAlternateColorCodes('&',customName)));
        getAttribute(Attributes.MAX_HEALTH).setBaseValue(entityData.getMaxHealth());
        setHealth((float) entityData.getMaxHealth());
        setItemInHand(InteractionHand.MAIN_HAND, CraftItemStack.asNMSCopy(new ItemStack(Material.IRON_AXE)));
        goalSelector.addGoal(0, new MeleeAttackGoal(
                this, 1, false
        ));
        goalSelector.addGoal(2, new PanicGoal(
                this, 1.5D
        ));
        goalSelector.addGoal(3, new WaterAvoidingRandomStrollGoal(
                this, 0.6D
        ));
        goalSelector.addGoal(4, new RandomLookAroundGoal(
                this
        ));
        // AMOR
        setItemSlot(EquipmentSlot.FEET, BossPVE.getInstance().getItemManager().getItemByName("vampireBoots").getNMSStack());
        setItemSlot(EquipmentSlot.LEGS, BossPVE.getInstance().getItemManager().getItemByName("vampirePants").getNMSStack());
        setItemSlot(EquipmentSlot.CHEST, BossPVE.getInstance().getItemManager().getItemByName("vampireChestplate").getNMSStack());
        setItemSlot(EquipmentSlot.HEAD, BossPVE.getInstance().getItemManager().getItemByName("vampireHelmet").getNMSStack());
        if(rollDrops()!=null) {
            drops.clear();
            drops.addAll(rollDrops());
        }
        addTag(getNBTIdentifier());
    }


    @Override
    public Entity spawn(Location location) {
        Entity entity = new Vampire(((CraftWorld) location.getWorld()).getHandle(), location);
        ((CraftWorld) location.getWorld()).addEntityToWorld(entity, CreatureSpawnEvent.SpawnReason.CUSTOM);
        return entity;
    }

    @Override
    public CustomEntityData getCustomEntityData() {
        return entityData;
    }

    @Override
    public List<Drop> getDrops() {
        List<Drop> result=new ArrayList<>();
        result.add(new Drop(((EnchantItem) BossPVE.getInstance().getItemManager().getItemByName("bloodsuckerEnch")).getAtLevel(1), 1, 800, 1));
        result.add(new Drop(BossPVE.getInstance().getItemManager().getItemByName("vampireeyefragment").nmsAsItemStack(), 1, 300, 1));
        result.add(new Drop(BossPVE.getInstance().getItemManager().getItemByName("vampireHelmet").nmsAsItemStack(), 1, 600, 1));
        result.add(new Drop(BossPVE.getInstance().getItemManager().getItemByName("vampireChestplate").nmsAsItemStack(), 1, 800, 1));
        result.add(new Drop(BossPVE.getInstance().getItemManager().getItemByName("vampirePants").nmsAsItemStack(), 1, 750, 1));
        result.add(new Drop(BossPVE.getInstance().getItemManager().getItemByName("vampireBoots").nmsAsItemStack(), 1, 600, 1));
        return result;
    }

    @Override
    public HashSet<Flag<EntityFlag, Double>> getFlags() {
        HashSet<Flag<EntityFlag, Double>> result = new HashSet<>();
        result.add(new Flag<>(EntityFlag.MAX_HEALTH, 100D, false));
        result.add(new Flag<>(EntityFlag.MONEY, 20D, false));
        result.add(new Flag<>(EntityFlag.XP, 15D, false));
        result.add(new Flag<>(EntityFlag.DAMAGE, 80D, false));
        return result;
    }

    @Override
    public @NotNull Material getShowMaterial() {
        return Material.FERMENTED_SPIDER_EYE;
    }

    @Override
    public String getShowName() {
        return customName;
    }

    @Override
    public String getNBTIdentifier() {
        return "vampire";
    }
}
