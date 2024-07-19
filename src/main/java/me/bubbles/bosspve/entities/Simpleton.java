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
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Simpleton extends Skeleton implements IEntity {

    private final String customName = ChatColor.translateAlternateColorCodes('&',"&7&lSimpleton");
    private CustomEntityData entityData;

    public Simpleton() {
        this(((CraftWorld) Bukkit.getWorlds().get(0)).getHandle().getWorld().getHandle(), null);
    }

    public Simpleton(Level level, Location location) {
        super(EntityType.SKELETON, level);
        this.entityData =new CustomEntityData(this);
        if(location!=null) {
            setPos(location.getX(),location.getY(),location.getZ());
        } else {
            remove(RemovalReason.DISCARDED);
            return;
        }
        setCustomNameVisible(true);
        setCustomName(Component.literal(ChatColor.translateAlternateColorCodes('&',customName)));
        getAttribute(Attributes.MAX_HEALTH).setBaseValue(entityData.getMaxHealth());
        setHealth((float) entityData.getMaxHealth());
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
        setItemInHand(InteractionHand.MAIN_HAND,BossPVE.getInstance().getItemManager().getItemByName("SkeletonSword").getNMSStack());
        setItemSlot(EquipmentSlot.HEAD, CraftItemStack.asNMSCopy(new ItemStack(Material.LEATHER_HELMET)));
        //getAttribute(Attributes.ARMOR).setBaseValue(0D);
        if(rollDrops()!=null) {
            drops.clear();
            drops.addAll(rollDrops());
        }
        addTag(getNBTIdentifier());
    }

    @Override
    public Entity spawn(Location location) {
        Entity entity = new Simpleton(((CraftWorld) location.getWorld()).getHandle(), location);
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
        result.add(new Drop(((EnchantItem) BossPVE.getInstance().getItemManager().getItemByName("telepathyEnch")).getAtLevel(1), 1, 200, 1));
        result.add(new Drop(((EnchantItem) BossPVE.getInstance().getItemManager().getItemByName("speedEnch")).getAtLevel(1), 1, 200, 1));
        result.add(new Drop(BossPVE.getInstance().getItemManager().getItemByName("skeletonSword").nmsAsItemStack(), 1, 200, 1));
        return result;
    }

    @Override
    public HashSet<Flag<EntityFlag, Double>> getFlags() {
        HashSet<Flag<EntityFlag, Double>> result = new HashSet<>();
        result.add(new Flag<>(EntityFlag.MAX_HEALTH, 2D, false));
        result.add(new Flag<>(EntityFlag.MONEY, 0.5D, false));
        result.add(new Flag<>(EntityFlag.XP, 1D, false));
        result.add(new Flag<>(EntityFlag.DAMAGE, 1D, false));
        return result;
    }

    @Override
    public @NotNull Material getShowMaterial() {
        return Material.SKELETON_SKULL;
    }

    @Override
    public String getShowName() {
        return customName;
    }

    @Override
    public String getNBTIdentifier() {
        return "simpleton";
    }

}