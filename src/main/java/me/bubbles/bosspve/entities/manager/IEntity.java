package me.bubbles.bosspve.entities.manager;

import me.bubbles.bosspve.flags.EntityFlag;
import me.bubbles.bosspve.flags.Flag;
import me.bubbles.bosspve.utility.CustomEntityEventHandler;
import me.bubbles.bosspve.utility.CustomEntityData;
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
    CustomEntityData getCustomEntityData();
    void onEvent(Event event);

    List<Drop> getDrops();

    HashSet<Flag<EntityFlag, Double>> getFlags();
    String getNBTIdentifier();
    String getShowName();

    @NotNull
    Material getShowMaterial();

}
