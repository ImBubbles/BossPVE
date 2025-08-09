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

public class AngryBee extends EntityBase<Bee> {

    public AngryBee() {
        this(((CraftWorld) Bukkit.getWorlds().get(0)).getHandle().getWorld().getHandle(), null);
    }

    public AngryBee(Level level, Location location) {
        super(EntityType.BEE, level, location);
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
        Entity entity = new AngryBee(((CraftWorld) location.getWorld()).getHandle(), location).casted();
        ((CraftWorld) location.getWorld()).addEntityToWorld(entity, CreatureSpawnEvent.SpawnReason.CUSTOM);
        return entity;
    }

    @Override
    public List<Drop> getDrops() {
        List<Drop> result=new ArrayList<>();
        result.add(new Drop(((EnchantItem) BossPVE.getInstance().getItemManager().getItemByName("sharpEnch")).getAtLevel(1), 1, 800, 1));
        result.add(new Drop(BossPVE.getInstance().getItemManager().getItemByName("beeStinger").nmsAsItemStack(), 1, 900, 1));
        result.add(new Drop(BossPVE.getInstance().getItemManager().getItemByName("beeHelmet").nmsAsItemStack(), 1, 500, 1));
        result.add(new Drop(BossPVE.getInstance().getItemManager().getItemByName("beeChestplate").nmsAsItemStack(), 1, 700, 1));
        result.add(new Drop(BossPVE.getInstance().getItemManager().getItemByName("beePants").nmsAsItemStack(), 1, 650, 1));
        result.add(new Drop(BossPVE.getInstance().getItemManager().getItemByName("beeBoots").nmsAsItemStack(), 1, 500, 1));
        return result;
    }

    @Override
    public HashSet<Flag<EntityFlag, Double>> getFlags() {
        HashSet<Flag<EntityFlag, Double>> result = new HashSet<>();
        result.add(new Flag<>(EntityFlag.MAX_HEALTH, 50D, false));
        result.add(new Flag<>(EntityFlag.MONEY, 17D, false));
        result.add(new Flag<>(EntityFlag.XP, 13D, false));
        result.add(new Flag<>(EntityFlag.DAMAGE, 40D, false));
        return result;
    }

    @Override
    public @NotNull Material getShowMaterial() {
        return Material.BEE_NEST;
    }

    @Override
    public String getShowName() {
        return "&e&lAngry Bee";
    }

    @Override
    public String getNBTIdentifier() {
        return "angrybee";
    }

}