package me.bubbles.bosspve.events.manager;

import me.bubbles.bosspve.BossPVE;
import me.bubbles.bosspve.events.*;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.stream.Collectors;

public class EventManager {

    private BossPVE plugin;
    private HashSet<Event> events = new HashSet<>();

    public EventManager(BossPVE plugin) {
        this.plugin=plugin;
        registerListener(); // REGISTER EVENT LISTENERS
        Collections.addAll(this.events, // REGISTER EVENT HANDLERS
                new Join(plugin),
                new Leave(plugin),
                new AnvilGetItem(plugin),
                new UpdateAnvil(plugin),
                new PlayerDmgOther(plugin),
                new UpdateLore(plugin),
                new PreventSpawning(plugin),
                new MaxFood(plugin),
                //new Respawn(plugin),
                new AnvilNameChange(plugin),
                //new AntiDeathRespawn(plugin),
                new WorldLoad(plugin),
                new UpdateHealthBar(plugin),
                new Respawn(plugin),
                new ServerLoad(plugin)
        );
    }

    public void addEvent(Event... events) {
        Collections.addAll(this.events, events);
    }

    public void removeEvent(Event... events) {
        for(Event event : events) {
            this.events.remove(event);
        }
    }

    public void onEvent(org.bukkit.event.Event event) {
        if(plugin.getStageManager()!=null) {
            plugin.getEntityManager().onEvent(event);
        }
        plugin.getItemManager().onEvent(event);
        events.stream()
                .filter(eventObj -> eventObj.getEvents().contains(event.getClass()))
                .collect(Collectors.toList())
                .forEach(eventObj -> eventObj.onEvent(event));
    }

    public void registerListener() {
        plugin.getServer().getPluginManager().registerEvents(new Listeners(this),plugin);
    }

}
