package me.bubbles.bosspve.entities;

import me.bubbles.bosspve.BossPVE;
import me.bubbles.bosspve.entities.manager.IEntity;
import me.bubbles.bosspve.flags.EntityFlag;
import me.bubbles.bosspve.flags.Flag;
import me.bubbles.bosspve.util.UtilEntity;
import me.bubbles.bosspve.util.UtilNumber;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.monster.Skeleton;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_20_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_20_R3.inventory.CraftItemStack;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Simpleton extends Skeleton implements IEntity {

    private final String customName = ChatColor.translateAlternateColorCodes('&',"&7&lSimpleton");

    private BossPVE plugin;
    private UtilEntity utilEntity;

    public Simpleton(BossPVE plugin) {
        this(plugin, ((CraftWorld) Bukkit.getWorlds().get(0)).getHandle().getWorld().getSpawnLocation());
    }

    public Simpleton(BossPVE plugin, Location location) {
        super(EntityType.SKELETON, ((CraftWorld) plugin.getMultiverseCore().getMVWorldManager().getMVWorld(location.getWorld()).getCBWorld()).getHandle());
        this.plugin=plugin;
        this.utilEntity=new UtilEntity(this);
        setPos(location.getX(),location.getY(),location.getZ());
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
        setItemInHand(InteractionHand.MAIN_HAND,plugin.getItemManager().getItemByName("SkeletonSword").getNMSStack());
        setItemSlot(EquipmentSlot.HEAD, CraftItemStack.asNMSCopy(new ItemStack(Material.LEATHER_HELMET)));
        if(getDrops()!=null) {
            drops.clear();
            drops.addAll(getDrops());
        }
        expToDrop=0;
        addTag(getNBTIdentifier());
    }

    @Override
    public boolean shouldDropExperience() {
        return false;
    }

    @Override
    public Entity spawn(Location location) {
        Entity entity = new Simpleton(plugin,location);
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
        if(UtilNumber.rollTheDice(1,100,2)) {
            result.add(plugin.getItemManager().getItemByName("telepathyEnch").nmsAsItemStack());
        }
        if(UtilNumber.rollTheDice(1,200,2)) {
            result.add(plugin.getItemManager().getItemByName("speedEnch").nmsAsItemStack());
        }
        if(UtilNumber.rollTheDice(1,150,1)) {
            result.add(plugin.getItemManager().getItemByName("skeletonSword").nmsAsItemStack());
        }
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
    public String getShowName() {
        return customName;
    }

    @Override
    public String getNBTIdentifier() {
        return "simpleton";
    }

}