package me.bubbles.bosspve.entities;

import me.bubbles.bosspve.BossPVE;
import me.bubbles.bosspve.entities.manager.EntityBase;
import me.bubbles.bosspve.flags.EntityFlag;
import me.bubbles.bosspve.flags.Flag;
import me.bubbles.bosspve.items.manager.bases.enchants.EnchantItem;
import me.bubbles.bosspve.utility.chance.Drop;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.monster.Skeleton;
import net.minecraft.world.level.Level;
import org.bukkit.Bukkit;
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

public class Simpleton extends EntityBase<Skeleton> {

    public Simpleton() {
        this(((CraftWorld) Bukkit.getWorlds().get(0)).getHandle().getWorld().getHandle(), null);
    }

    public Simpleton(Level level, Location location) {
        super(EntityType.SKELETON, level, location);
        casted().goalSelector.addGoal(0, new RangedBowAttackGoal<>(
                casted(), 1, 1, 5
        ));
        casted().goalSelector.addGoal(0, new MeleeAttackGoal(
                casted(), 1, false
        ));
        casted().goalSelector.addGoal(1, new PanicGoal(
                casted(), 1.5D
        ));
        casted().goalSelector.addGoal(2, new WaterAvoidingRandomStrollGoal(
                casted(), 0.6D
        ));
        casted().goalSelector.addGoal(3, new RandomLookAroundGoal(
                casted()
        ));
        setItemInHand(InteractionHand.MAIN_HAND,BossPVE.getInstance().getItemManager().getItemByName("SkeletonSword").getNMSStack());
        setItemSlot(EquipmentSlot.HEAD, CraftItemStack.asNMSCopy(new ItemStack(Material.LEATHER_HELMET)));
    }

    @Override
    public Entity spawn(Location location) {
        Entity entity = new Simpleton(((CraftWorld) location.getWorld()).getHandle(), location).casted();
        ((CraftWorld) location.getWorld()).addEntityToWorld(entity, CreatureSpawnEvent.SpawnReason.CUSTOM);
        return entity;
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
        return "&7&lSimpleton";
    }

    @Override
    public String getNBTIdentifier() {
        return "simpleton";
    }

}