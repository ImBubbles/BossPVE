package me.bubbles.bosspve.entities.manager;

import me.bubbles.bosspve.BossPVE;
import me.bubbles.bosspve.entities.*;
import org.bukkit.ChatColor;
import org.bukkit.event.Event;

import java.util.Arrays;
import java.util.HashSet;

public class EntityManager {

    private HashSet<IEntity> entities;
    private BossPVE plugin;

    public EntityManager(BossPVE plugin) {
        this.plugin=plugin;
        this.entities=new HashSet<>();
        registerEntities(
                new Simpleton(plugin)
        );
    }

    private void registerEntities(IEntity... entity) {
        entities.addAll(Arrays.asList(entity));
    }

    public void onEvent(Event event) {
        entities.forEach(iEntityBase -> iEntityBase.onEvent(plugin,event));
    }

    public HashSet<IEntity> getEntities() {
        return entities;
    }

    public IEntity getEntityByName(String name) {
        for(IEntity entity : entities) {
            if(ChatColor.stripColor(entity.getUncoloredName()).replace(" ","_").equalsIgnoreCase(name)) {
                return entity;
            }
        }
        return null;
    }

}
