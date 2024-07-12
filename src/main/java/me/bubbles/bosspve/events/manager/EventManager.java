package me.bubbles.bosspve.events.manager;

import me.bubbles.bosspve.BossPVE;
import me.bubbles.bosspve.events.*;

import java.util.Collections;
import java.util.HashSet;
import java.util.stream.Collectors;

public class EventManager {
    private HashSet<Event> events = new HashSet<>();

    public EventManager() {
        registerListener(); // REGISTER EVENT LISTENERS
        Collections.addAll(this.events, // REGISTER EVENT HANDLERS
                new Join(),
                new Leave(),
                new AnvilGetItem(),
                new UpdateAnvil(),
                new PlayerDmgOther(),
                new PreventSpawning(),
                new MaxFood(),
                //new Respawn(plugin),
                new AnvilNameChange(),
                //new AntiDeathRespawn(plugin),
                new WorldLoad(),
                new UpdateHealthBar(),
                new Respawn(),
                new ServerLoad(),
                //new ArmorPutOn(plugin),
                //new ArmorClickOn(plugin),
                //new UpdateLore(plugin),
                new EntityDeath(),
                new EntityRemove(),
                new UpdateStats(),
                new BlockPlace()
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
        if(BossPVE.getInstance().getStageManager()!=null) {
            BossPVE.getInstance().getEntityManager().onEvent(event);
        }
        BossPVE.getInstance().getItemManager().onEvent(event);
        events.stream()
                .filter(eventObj -> eventObj.getEvents().contains(event.getClass()))
                .collect(Collectors.toList())
                .forEach(eventObj -> eventObj.onEvent(event));
    }

    public HashSet<Event> getEvents() {
        return events;
    }

    public void registerListener() {
        BossPVE.getInstance().getServer().getPluginManager().registerEvents(new Listeners(this), BossPVE.getInstance());
    }

}
