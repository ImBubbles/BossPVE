package me.bubbles.bosspve.entities;

import me.bubbles.bosspve.BossPVE;
import me.bubbles.bosspve.entities.manager.EntityBase;
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
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.PanicGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.animal.Bee;
import net.minecraft.world.entity.monster.breeze.Breeze;
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

public class Cyclone extends EntityBase<Breeze> {

    public Cyclone() {
        this(((CraftWorld) Bukkit.getWorlds().get(0)).getHandle().getWorld().getHandle(), null);
    }

    public Cyclone(Level level, Location location) {
        super(EntityType.BREEZE, level, location);
        casted().goalSelector.addGoal(0, new MeleeAttackGoal(
                casted(), 1, false
        ));
        casted().goalSelector.addGoal(2, new PanicGoal(
                casted(), 1.5D
        ));
        casted().goalSelector.addGoal(3, new WaterAvoidingRandomStrollGoal(
                casted(), 0.6D
        ));
        casted().goalSelector.addGoal(4, new RandomLookAroundGoal(
                casted()
        ));
        casted().setAggressive(true);
    }


    @Override
    public Entity spawn(Location location) {
        Entity entity = new Cyclone(((CraftWorld) location.getWorld()).getHandle(), location).casted();
        ((CraftWorld) location.getWorld()).addEntityToWorld(entity, CreatureSpawnEvent.SpawnReason.CUSTOM);
        return entity;
    }

    @Override
    public List<Drop> getDrops() {
        List<Drop> result=new ArrayList<>();
        result.add(new Drop(((EnchantItem) BossPVE.getInstance().getItemManager().getItemByName("soulfulEnch")).getAtLevel(1), 1, 800, 1));
        result.add(new Drop(BossPVE.getInstance().getItemManager().getItemByName("cycloneFragment").nmsAsItemStack(), 1, 1000, 1));
        result.add(new Drop(BossPVE.getInstance().getItemManager().getItemByName("cycloneHelmet").nmsAsItemStack(), 1, 700, 1));
        result.add(new Drop(BossPVE.getInstance().getItemManager().getItemByName("cycloneChestplate").nmsAsItemStack(), 1, 900, 1));
        result.add(new Drop(BossPVE.getInstance().getItemManager().getItemByName("cyclonePants").nmsAsItemStack(), 1, 800, 1));
        result.add(new Drop(BossPVE.getInstance().getItemManager().getItemByName("cycloneBoots").nmsAsItemStack(), 1, 700, 1));
        return result;
    }

    @Override
    public HashSet<Flag<EntityFlag, Double>> getFlags() {
        HashSet<Flag<EntityFlag, Double>> result = new HashSet<>();
        result.add(new Flag<>(EntityFlag.MAX_HEALTH, 150D, false));
        result.add(new Flag<>(EntityFlag.MONEY, 30D, false));
        result.add(new Flag<>(EntityFlag.XP, 20D, false));
        result.add(new Flag<>(EntityFlag.DAMAGE, 100D, false));
        return result;
    }

    @Override
    public @NotNull Material getShowMaterial() {
        return Material.BREEZE_ROD;
    }

    @Override
    public String getShowName() {
        return "&f&lCyclone";
    }

    @Override
    public String getNBTIdentifier() {
        return "cyclone";
    }
}
