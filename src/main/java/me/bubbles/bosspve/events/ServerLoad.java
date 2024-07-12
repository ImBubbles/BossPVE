package me.bubbles.bosspve.events;

import me.bubbles.bosspve.BossPVE;
import me.bubbles.bosspve.events.manager.Event;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.server.ServerLoadEvent;

public class ServerLoad extends Event {

    public ServerLoad() {
        super(ServerLoadEvent.class);
    }

    @Override
    public void onEvent(org.bukkit.event.Event event) {
        BossPVE.getInstance().initStageManager();
    }

}
