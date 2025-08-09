package me.bubbles.bosspve.entities.manager;

import me.bubbles.bosspve.utility.CustomEntityData;
import me.bubbles.bosspve.utility.CustomEntityEventHandler;
import me.bubbles.bosspve.utility.chance.Drop;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.ai.attributes.Attributes;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_21_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_21_R1.entity.CraftEntity;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;

import java.util.ArrayList;
import java.util.List;

public abstract class EntityBase<T extends LivingEntity> extends LivingEntity implements IEntity {

    private final CustomEntityData entityData;
    private final EntityType<T> entityType;

    public EntityBase(EntityType<T> entityType) {
        this(entityType, ((CraftWorld) Bukkit.getWorlds().get(0)).getHandle().getWorld().getHandle(), null);
    }

    public EntityBase(EntityType<T> entityType, Level level, Location location) {
        super(entityType, level);
        this.entityType=entityType;
        this.entityData=new CustomEntityData(this);
        if(location!=null) {
            casted().setPos(location.getX(),location.getY(),location.getZ());
        } else {
            casted().remove(RemovalReason.DISCARDED);
            return;
        }
        casted().setCustomNameVisible(true);
        casted().setCustomName(Component.literal(ChatColor.translateAlternateColorCodes('&', getShowName())));
        casted().getAttribute(Attributes.MAX_HEALTH).setBaseValue(entityData.getMaxHealth());
        casted().setHealth((float) entityData.getMaxHealth());
        casted().expToDrop=0;
        if(rollDrops()!=null) {
            casted().drops.clear();
            casted().drops.addAll(rollDrops());
        }
        casted().addTag(getNBTIdentifier());
    }

    public final String getUncoloredName() {
        return ChatColor.stripColor(getShowName());
    }
    public final boolean hasSameTagAs(Entity entity) {
        if(entity.getTags().isEmpty()) {
            return false;
        }
        return entity.getTags().contains(getNBTIdentifier());
    }

    public final boolean hasSameTagAs(org.bukkit.entity.Entity bukkitEntity) {
        return hasSameTagAs(((CraftEntity) bukkitEntity).getHandle());
    }

    @Override
    public void onEvent(Event event) {
        if(event instanceof EntityDeathEvent) {
            CustomEntityEventHandler.entityDeathEvent(this, event);
        }
        if(event instanceof EntityDamageByEntityEvent) {
            CustomEntityEventHandler.entityDamageByEntityEvent(this, event);
        }
    }

    public final List<org.bukkit.inventory.ItemStack> forceDrops() {
        List<org.bukkit.inventory.ItemStack> result = new ArrayList<>();
        for(Drop drop : getDrops()) {
            org.bukkit.inventory.ItemStack itemStack = drop.roll(true);
            if(itemStack!=null) {
                result.add(itemStack);
            }
        }
        return result;
    }

    public final List<org.bukkit.inventory.ItemStack> rollDrops() {
        List<org.bukkit.inventory.ItemStack> result = new ArrayList<>();
        for(Drop drop : getDrops()) {
            org.bukkit.inventory.ItemStack itemStack = drop.roll();
            if(itemStack!=null) {
                result.add(itemStack);
            }
        }
        return result;
    }

    protected T casted() {
        LivingEntity livingEntity = (LivingEntity) this;
        return (T) livingEntity;
        /*return entityType.get;*/
    }

    @Override
    public final CustomEntityData getCustomEntityData() {
        return entityData;
    }


    @Override
    public Iterable<ItemStack> getArmorSlots() {
        return casted().getArmorSlots();
    }

    @Override
    public ItemStack getItemBySlot(EquipmentSlot equipmentSlot) {
        return casted().getItemBySlot(equipmentSlot);
    }

    @Override
    public void setItemSlot(EquipmentSlot equipmentSlot, ItemStack itemStack) {
        casted().setItemSlot(equipmentSlot, itemStack);
    }

    @Override
    public HumanoidArm getMainArm() {
        return casted().getMainArm();
    }

}
