package me.bubbles.bosspve.events.manager;

import me.bubbles.bosspve.BossPVE;
import org.bukkit.event.Listener;

import java.util.ArrayList;
import java.util.List;

public class Event implements Listener {

    public BossPVE plugin;
    private List<Class> event;

    public Event(BossPVE plugin, List<Class> event) {
        this.plugin=plugin;
        this.event=event;
    }

    public Event(BossPVE plugin, Class event) {
        this.plugin=plugin;
        List<Class> classes = new ArrayList<>();
        classes.add(event);
        this.event=classes;
    }

    public void onEvent(org.bukkit.event.Event event) {

    }

    public List<Class> getEvents() {
        return event;
    }

}
