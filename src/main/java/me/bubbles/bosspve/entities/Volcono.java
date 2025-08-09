package me.bubbles.bosspve.entities;

import me.bubbles.bosspve.BossPVE;
import me.bubbles.bosspve.entities.manager.EntityBase;
import me.bubbles.bosspve.entities.manager.IEntity;
import me.bubbles.bosspve.flags.EntityFlag;
import me.bubbles.bosspve.flags.Flag;
import me.bubbles.bosspve.utility.CustomEntityData;
import me.bubbles.bosspve.utility.chance.Drop;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.MagmaCube;
import net.minecraft.world.entity.player.Player;
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

public class Volcono extends EntityBase<MagmaCube> {

    public Volcono() {
        this(((CraftWorld) Bukkit.getWorlds().get(0)).getHandle().getWorld().getHandle(), null);
    }

    public Volcono(Level level, Location location) {
        super(EntityType.MAGMA_CUBE, level, location);
        casted().setSize(5, false);
        casted().goalSelector.addGoal(0, new NearestAttackableTargetGoal<>(
                casted(), Player.class, false
        ));
        casted().goalSelector.addGoal(4, new RandomLookAroundGoal(
                casted()
        ));
    }

    @Override
    public Entity spawn(Location location) {
        Entity entity = new Volcono(((CraftWorld) location.getWorld()).getHandle(), location).casted();
        ((CraftWorld) location.getWorld()).addEntityToWorld(entity, CreatureSpawnEvent.SpawnReason.CUSTOM);
        return entity;
    }

    @Override
    public List<Drop> getDrops() {
        List<Drop> result=new ArrayList<>();
        result.add(new Drop(BossPVE.getInstance().getItemManager().getItemByName("volcanictear").nmsAsItemStack(), 1, 600, 1));
        result.add(new Drop(BossPVE.getInstance().getItemManager().getItemByName("volcanicHelmet").nmsAsItemStack(), 1, 300, 1));
        result.add(new Drop(BossPVE.getInstance().getItemManager().getItemByName("volcanicChestplate").nmsAsItemStack(), 1, 400, 1));
        result.add(new Drop(BossPVE.getInstance().getItemManager().getItemByName("volcanicPants").nmsAsItemStack(), 1, 350, 1));
        result.add(new Drop(BossPVE.getInstance().getItemManager().getItemByName("volcanicBoots").nmsAsItemStack(), 1, 300, 1));
        return result;
    }

    @Override
    public HashSet<Flag<EntityFlag, Double>> getFlags() {
        HashSet<Flag<EntityFlag, Double>> result = new HashSet<>();
        result.add(new Flag<>(EntityFlag.MAX_HEALTH, 50D, false));
        result.add(new Flag<>(EntityFlag.MONEY, 40D, false));
        result.add(new Flag<>(EntityFlag.XP, 10D, false));
        result.add(new Flag<>(EntityFlag.DAMAGE, 35D, false));
        return result;
    }

    @Override
    public @NotNull Material getShowMaterial() {
        return Material.MAGMA_CREAM;
    }

    @Override
    public String getShowName() {
        return "&6&lVolcono";
    }

    @Override
    public String getNBTIdentifier() {
        return "volcono";
    }

}