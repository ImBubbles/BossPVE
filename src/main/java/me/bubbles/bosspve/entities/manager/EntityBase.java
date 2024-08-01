package me.bubbles.bosspve.entities.manager;

import me.bubbles.bosspve.utility.CustomEntityData;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_21_R1.CraftWorld;

public abstract class EntityBase<T extends LivingEntity> extends LivingEntity implements IEntity {

    private final CustomEntityData entityData;

    public EntityBase(EntityType<T> entityType) {
        this(entityType, ((CraftWorld) Bukkit.getWorlds().get(0)).getHandle().getWorld().getHandle(), null);
    }

    public EntityBase(EntityType<T> entityType, Level level, Location location) {
        super(EntityType.IRON_GOLEM, level);
        this.entityData=new CustomEntityData(this);
        setCustomNameVisible(true);
        setCustomName(Component.literal(ChatColor.translateAlternateColorCodes('&', getShowName())));
        getAttribute(Attributes.MAX_HEALTH).setBaseValue(entityData.getMaxHealth());
        setHealth((float) entityData.getMaxHealth());
        expToDrop=0;
        addTag(getNBTIdentifier());
    }

    @Override
    public CustomEntityData getCustomEntityData() {
        return entityData;
    }

}
