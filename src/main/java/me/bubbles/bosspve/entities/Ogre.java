package me.bubbles.bosspve.entities;

import me.bubbles.bosspve.BossPVE;
import me.bubbles.bosspve.entities.manager.IEntity;
import me.bubbles.bosspve.flags.EntityFlag;
import me.bubbles.bosspve.flags.Flag;
import me.bubbles.bosspve.utility.UtilEntity;
import me.bubbles.bosspve.utility.chance.Drop;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.PanicGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.monster.ZombieVillager;
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

public class Ogre extends ZombieVillager implements IEntity {

    private final String customName = ChatColor.translateAlternateColorCodes('&',"&2&lOgre");
    private UtilEntity utilEntity;

    public Ogre() {
        this(((CraftWorld) Bukkit.getWorlds().get(0)).getHandle().getWorld().getHandle(), null);
    }

    public Ogre(Level level, Location location) {
        super(EntityType.ZOMBIE_VILLAGER, level);
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
        expToDrop=0;
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
        // AMOR
        setItemSlot(EquipmentSlot.FEET, BossPVE.getInstance().getItemManager().getItemByName("ogreBoots").getNMSStack());
        setItemSlot(EquipmentSlot.LEGS, BossPVE.getInstance().getItemManager().getItemByName("ogrePants").getNMSStack());
        setItemSlot(EquipmentSlot.CHEST, BossPVE.getInstance().getItemManager().getItemByName("ogreChestplate").getNMSStack());
        setItemSlot(EquipmentSlot.HEAD, BossPVE.getInstance().getItemManager().getItemByName("ogreHelmet").getNMSStack());
        if(rollDrops()!=null) {
            drops.clear();
            drops.addAll(rollDrops());
        }
        addTag(getNBTIdentifier());
    }

    @Override
    public Entity spawn(Location location) {
        Entity entity = new Ogre(((CraftWorld) location.getWorld()).getHandle(), location);
        ((CraftWorld) location.getWorld()).addEntityToWorld(entity, CreatureSpawnEvent.SpawnReason.CUSTOM);
        return entity;
    }

    @Override
    public UtilEntity getUtilEntity() {
        return utilEntity;
    }

    @Override
    public List<Drop> getDrops() {
        List<Drop> result=new ArrayList<>();
        result.add(new Drop(BossPVE.getInstance().getItemManager().getItemByName("ogreBoots").nmsAsItemStack(), 1, 350, 1));
        result.add(new Drop(BossPVE.getInstance().getItemManager().getItemByName("ogrePants").nmsAsItemStack(), 1, 350, 1));
        result.add(new Drop(BossPVE.getInstance().getItemManager().getItemByName("ogreChestplate").nmsAsItemStack(), 1, 350, 1));
        result.add(new Drop(BossPVE.getInstance().getItemManager().getItemByName("ogreHelmet").nmsAsItemStack(), 1, 350, 1));
        return result;
    }

    @Override
    public HashSet<Flag<EntityFlag, Double>> getFlags() {
        HashSet<Flag<EntityFlag, Double>> result = new HashSet<>();
        result.add(new Flag<>(EntityFlag.MAX_HEALTH, 7D, false));
        result.add(new Flag<>(EntityFlag.MONEY, 2D, false));
        result.add(new Flag<>(EntityFlag.XP, 2D, false));
        result.add(new Flag<>(EntityFlag.DAMAGE, 3D, false));
        return result;
    }

    @Override
    public @NotNull Material getShowMaterial() {
        return Material.ROTTEN_FLESH;
    }

    @Override
    public String getNBTIdentifier() {
        return "ogre";
    }

    @Override
    public String getShowName() {
        return customName;
    }

}
