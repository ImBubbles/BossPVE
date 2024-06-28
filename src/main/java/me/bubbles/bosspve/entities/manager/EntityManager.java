package me.bubbles.bosspve.entities.manager;

import me.bubbles.bosspve.BossPVE;
import me.bubbles.bosspve.entities.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.Event;

import java.util.Arrays;
import java.util.HashSet;
import java.util.function.Supplier;

public class EntityManager {

    private HashSet<Supplier<IEntity>> entities;
    private HashSet<IEntity> bases;
    private BossPVE plugin;

    public EntityManager(BossPVE plugin) {
        this.plugin=plugin;
        this.entities=new HashSet<>();
        registerEntities(
                () -> new Ogre(plugin),
                () -> new Hellbringer(plugin),
                () -> new Ferrum(plugin),
                () -> new Goblin(plugin),
                () -> new Simpleton(plugin),
                () -> new AngryBee(plugin),
                () -> new Ninja(plugin),
                () -> new Volcono(plugin),
                () -> new Protector(plugin),
                () -> new Vampire(plugin)
        );
    }

    private void registerEntities(Supplier<IEntity>... entity) {
        entities.addAll(Arrays.asList(entity));
    }

    public void onEvent(Event event) {
        getEntities().forEach(iEntity -> iEntity.onEvent(plugin,event));
    }

    public HashSet<IEntity> getEntities() {
        if(bases!=null) {
            return bases;
        }
        if(!(Bukkit.getWorlds().size()>=0)) {
            return new HashSet<>();
        }
        HashSet<IEntity> result = new HashSet<>();
        for(Supplier<IEntity> iEntitySupplier : entities) {
            result.add(iEntitySupplier.get());
        }
        this.bases=result;
        return result;
    }

    public HashSet<Supplier<IEntity>> getSupplierEntities() {
        return entities;
    }

    public IEntity getEntityByName(String name) {
        for(IEntity entity : getEntities()) {
            if(ChatColor.stripColor(entity.getUncoloredName()).replace(" ","_").equalsIgnoreCase(name)) {
                return entity;
            }
        }
        return null;
    }

}
