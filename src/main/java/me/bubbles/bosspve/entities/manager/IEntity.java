package me.bubbles.bosspve.entities.manager;

import me.bubbles.bosspve.BossPVE;
import me.bubbles.bosspve.flags.EntityFlag;
import me.bubbles.bosspve.flags.Flag;
import me.bubbles.bosspve.utility.UtilCustomEvents;
import me.bubbles.bosspve.utility.UtilEntity;
import me.bubbles.bosspve.utility.chance.Drop;
import net.minecraft.world.entity.Entity;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_21_R1.entity.CraftEntity;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public interface IEntity {

    Entity spawn(Location location);
    UtilEntity getUtilEntity();
    default void onEvent(Event event) {
        if(event instanceof EntityDeathEvent) {
            UtilCustomEvents uce = new UtilCustomEvents(event);
            uce.customEntityDeathEvent(this);
        }
        if(event instanceof EntityDamageByEntityEvent) {
            UtilCustomEvents uce = new UtilCustomEvents(event);
            uce.customEntityDamageByEntityEvent(this);
        }
    }


    List<Drop> getDrops();

    default List<ItemStack> rollDrops() {
        List<ItemStack> result = new ArrayList<>();
        for(Drop drop : getDrops()) {
            ItemStack itemStack = drop.roll();
            if(itemStack!=null) {
                result.add(itemStack);
            }
        }
        return result;
    }

    default List<ItemStack> forceDrops() {
        List<ItemStack> result = new ArrayList<>();
        for(Drop drop : getDrops()) {
            ItemStack itemStack = drop.roll(true);
            if(itemStack!=null) {
                result.add(itemStack);
            }
        }
        return result;
    }
    HashSet<Flag<EntityFlag, Double>> getFlags();
    String getNBTIdentifier();
    String getShowName();

    @NotNull
    Material getShowMaterial();
    default String getUncoloredName() {
        return ChatColor.stripColor(getShowName());
    }
    default boolean hasSameTagAs(Entity entity) {
        if(entity.getTags().isEmpty()) {
            return false;
        }
        return entity.getTags().contains(getNBTIdentifier());
    }

    default boolean hasSameTagAs(org.bukkit.entity.Entity bukkitEntity) {
        return hasSameTagAs(((CraftEntity) bukkitEntity).getHandle());
    }

}
