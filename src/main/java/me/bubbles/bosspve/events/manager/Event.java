package me.bubbles.bosspve.events.manager;

import me.bubbles.bosspve.BossPVE;
import org.bukkit.event.Listener;

public class Event implements Listener {

    public BossPVE plugin;
    private Class event;

    public Event(BossPVE plugin, Class event) {
        this.plugin=plugin;
        this.event=event;
    }

    public void onEvent(org.bukkit.event.Event event) {

    }

    public Class getEvent() {
        return event;
    }

}
