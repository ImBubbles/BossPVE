package me.bubbles.bosspve.events;

import me.bubbles.bosspve.BossPVE;
import me.bubbles.bosspve.events.manager.Event;
import org.bukkit.event.player.PlayerQuitEvent;

public class Leave extends Event {

    public Leave(BossPVE plugin) {
        super(plugin, PlayerQuitEvent.class);
    }

    @Override
    public void onEvent(org.bukkit.event.Event event) {
        PlayerQuitEvent e = (PlayerQuitEvent) event;
    }

}
