package me.bubbles.bosspve.entities.manager;

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

    public EntityManager() {
        this.entities=new HashSet<>();
        registerEntities(
                Ogre::new,
                Hellbringer::new,
                Ferrum::new,
                Goblin::new,
                Simpleton::new,
                AngryBee::new,
                Ninja::new,
                Volcono::new,
                Protector::new,
                Vampire::new,
                Cyclone::new
        );
    }

    private void registerEntities(Supplier<IEntity>... entity) {
        entities.addAll(Arrays.asList(entity));
    }

    public void onEvent(Event event) {
        getEntities().forEach(iEntity -> iEntity.onEvent(event));
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

    public IEntity getEntityByIdentifier(String id) {
        for(IEntity entity : getEntities()) {
            if(entity.getNBTIdentifier().equalsIgnoreCase(id)) {
                return entity;
            }
        }
        return null;
    }

}
