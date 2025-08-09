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
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.monster.Ravager;
import net.minecraft.world.level.Level;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_21_R1.CraftWorld;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Protector extends EntityBase<Ravager> {

    public Protector() {
        this(((CraftWorld) Bukkit.getWorlds().get(0)).getHandle().getWorld().getHandle(), null);
    }

    public Protector(Level level, Location location) {
        super(EntityType.RAVAGER, level, location);
        casted().goalSelector.addGoal(0, new MeleeAttackGoal(
                casted(), 1.0, false
        ));
        casted().goalSelector.addGoal(1, new RandomLookAroundGoal(
                casted()
        ));
    }

    @Override
    public Entity spawn(Location location) {
        Entity entity = new Protector(((CraftWorld) location.getWorld()).getHandle(), location).casted();
        ((CraftWorld) location.getWorld()).addEntityToWorld(entity, CreatureSpawnEvent.SpawnReason.CUSTOM);
        return entity;
    }

    @Override
    public List<Drop> getDrops() {
        List<Drop> result=new ArrayList<>();
        result.add(new Drop(((EnchantItem) BossPVE.getInstance().getItemManager().getItemByName("damagerEnch")).getAtLevel(2), 1, 100, 1));
        result.add(new Drop(((EnchantItem) BossPVE.getInstance().getItemManager().getItemByName("resistanceEnch")).getAtLevel(3), 1, 250, 1));
        return result;
    }

    @Override
    public HashSet<Flag<EntityFlag, Double>> getFlags() {
        HashSet<Flag<EntityFlag, Double>> result = new HashSet<>();
        result.add(new Flag<>(EntityFlag.MAX_HEALTH, 100D, false));
        result.add(new Flag<>(EntityFlag.MONEY, 100D, false));
        result.add(new Flag<>(EntityFlag.XP, 10D, false));
        result.add(new Flag<>(EntityFlag.DAMAGE, 30D, false));
        return result;
    }

    @Override
    public @NotNull Material getShowMaterial() {
        return Material.SHIELD;
    }

    @Override
    public String getShowName() {
        return "&8&lProtector";
    }

    @Override
    public String getNBTIdentifier() {
        return "protector";
    }

}