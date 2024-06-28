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
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.MagmaCube;
import net.minecraft.world.entity.player.Player;
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

public class Volcono extends MagmaCube implements IEntity {

    private final String customName = ChatColor.translateAlternateColorCodes('&',"&6&lVolcono");
    private BossPVE plugin;
    private UtilEntity utilEntity;

    public Volcono(BossPVE plugin) {
        this(plugin, ((CraftWorld) Bukkit.getWorlds().get(0)).getHandle().getWorld().getHandle(), null);
    }

    public Volcono(BossPVE plugin, Level level, Location location) {
        super(EntityType.MAGMA_CUBE, level);
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
        setSize(5,false);
        goalSelector.addGoal(0, new NearestAttackableTargetGoal<>(
                this, Player.class, false
        ));
        goalSelector.addGoal(4, new RandomLookAroundGoal(
                this
        ));
        if(getDrops()!=null) {
            drops.clear();
            drops.addAll(getDrops());
        }
        addTag(getNBTIdentifier());
    }

    @Override
    public Entity spawn(Location location) {
        Entity entity = new Volcono(plugin, ((CraftWorld) location.getWorld()).getHandle(), location);
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
        if(UtilNumber.rollTheDice(1,600,1)) {
            result.add(plugin.getItemManager().getItemByName("volcanictear").nmsAsItemStack());
        }
        if(UtilNumber.rollTheDice(1,300,1)) {
            result.add(plugin.getItemManager().getItemByName("volcanicHelmet").nmsAsItemStack());
        }
        if(UtilNumber.rollTheDice(1,400,1)) {
            result.add(plugin.getItemManager().getItemByName("volcanicChestplate").nmsAsItemStack());
        }
        if(UtilNumber.rollTheDice(1,350,1)) {
            result.add(plugin.getItemManager().getItemByName("volcanicPants").nmsAsItemStack());
        }
        if(UtilNumber.rollTheDice(1,300,1)) {
            result.add(plugin.getItemManager().getItemByName("volcanicBoots").nmsAsItemStack());
        }
        return result;
    }

    @Override
    public HashSet<Flag<EntityFlag, Double>> getFlags() {
        HashSet<Flag<EntityFlag, Double>> result = new HashSet<>();
        /*result.add(new Flag<>(EntityFlag.MAX_HEALTH, 30D, false));
        result.add(new Flag<>(EntityFlag.MONEY, 15D, false));
        result.add(new Flag<>(EntityFlag.XP, 10D, false));
        result.add(new Flag<>(EntityFlag.DAMAGE, 60D, false));*/

        result.add(new Flag<>(EntityFlag.MAX_HEALTH, 50D, false));
        result.add(new Flag<>(EntityFlag.MONEY, 40D, false));
        result.add(new Flag<>(EntityFlag.XP, 10D, false));
        result.add(new Flag<>(EntityFlag.DAMAGE, 35D, false));
        return result;
    }

    @Override
    public String getShowName() {
        return customName;
    }

    @Override
    public String getNBTIdentifier() {
        return "volcono";
    }

}