package me.bubbles.bosspve.events;

import me.bubbles.bosspve.BossPVE;
import me.bubbles.bosspve.events.manager.Event;
import me.bubbles.bosspve.util.UtilUserData;
import org.bukkit.event.player.PlayerJoinEvent;

public class Join extends Event {

    public Join(BossPVE plugin) {
        super(plugin, PlayerJoinEvent.class);
    }

    @Override
    public void onEvent(org.bukkit.event.Event event) {
        PlayerJoinEvent e = (PlayerJoinEvent) event;
        UtilUserData uud = UtilUserData.getUtilUserData(e.getPlayer().getUniqueId());
        if(uud.getXp()==-1) {
            UtilUserData.save(new UtilUserData(e.getPlayer().getUniqueId(),0));
        }

    }

}
