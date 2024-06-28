package me.bubbles.bosspve.entities;

import me.bubbles.bosspve.BossPVE;
import me.bubbles.bosspve.entities.manager.IEntity;
import me.bubbles.bosspve.flags.EntityFlag;
import me.bubbles.bosspve.flags.Flag;
import me.bubbles.bosspve.utility.UtilEntity;
import me.bubbles.bosspve.utility.UtilNumber;
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
import org.bukkit.craftbukkit.v1_21_R1.CraftWorld;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Ogre extends ZombieVillager implements IEntity {

    private final String customName = ChatColor.translateAlternateColorCodes('&',"&2&lOgre");
    private BossPVE plugin;
    private UtilEntity utilEntity;

    public Ogre(BossPVE plugin) {
        this(plugin, ((CraftWorld) Bukkit.getWorlds().get(0)).getHandle().getWorld().getHandle(), null);
    }

    public Ogre(BossPVE plugin, Level level, Location location) {
        super(EntityType.ZOMBIE_VILLAGER, level);
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
        setItemSlot(EquipmentSlot.FEET, plugin.getItemManager().getItemByName("ogreBoots").getNMSStack());
        setItemSlot(EquipmentSlot.LEGS, plugin.getItemManager().getItemByName("ogrePants").getNMSStack());
        setItemSlot(EquipmentSlot.CHEST, plugin.getItemManager().getItemByName("ogreChestplate").getNMSStack());
        setItemSlot(EquipmentSlot.HEAD, plugin.getItemManager().getItemByName("ogreHelmet").getNMSStack());
        if(getDrops()!=null) {
            drops.clear();
            drops.addAll(getDrops());
        }
        addTag(getNBTIdentifier());
    }

    @Override
    public Entity spawn(Location location) {
        Entity entity = new Ogre(plugin, ((CraftWorld) location.getWorld()).getHandle(), location);
        ((CraftWorld) location.getWorld()).addEntityToWorld(entity, CreatureSpawnEvent.SpawnReason.CUSTOM);
        return entity;
    }

    @Override
    public UtilEntity getUtilEntity() {
        return utilEntity;
    }

    @Override
    public List<ItemStack> getDrops() {
        List<ItemStack> result=new ArrayList<>();
        if(UtilNumber.rollTheDice(1,350,1)) {
            result.add(plugin.getItemManager().getItemByName("ogreBoots").nmsAsItemStack());
        }
        if(UtilNumber.rollTheDice(1,350,1)) {
            result.add(plugin.getItemManager().getItemByName("ogrePants").nmsAsItemStack());
        }
        if(UtilNumber.rollTheDice(1,350,1)) {
            result.add(plugin.getItemManager().getItemByName("ogreChestplate").nmsAsItemStack());
        }
        if(UtilNumber.rollTheDice(1,350,1)) {
            result.add(plugin.getItemManager().getItemByName("ogreHelmet").nmsAsItemStack());
        }
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
    public String getNBTIdentifier() {
        return "ogre";
    }

    @Override
    public String getShowName() {
        return customName;
    }

}
