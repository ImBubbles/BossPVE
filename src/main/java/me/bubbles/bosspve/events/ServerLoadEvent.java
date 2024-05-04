package me.bubbles.bosspve.events;

import me.bubbles.bosspve.BossPVE;
import me.bubbles.bosspve.events.manager.Event;

public class ServerLoadEvent extends Event {

    public ServerLoadEvent(BossPVE plugin) {
        super(plugin, ServerLoadEvent.class);
    }

    @Override
    public void onEvent(org.bukkit.event.Event event) {
        plugin.initStageManager();
    }

}
