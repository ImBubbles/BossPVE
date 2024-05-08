package me.bubbles.bosspve.events;

import me.bubbles.bosspve.BossPVE;
import me.bubbles.bosspve.events.manager.Event;
import me.bubbles.bosspve.game.GamePlayer;
import me.bubbles.bosspve.utility.UtilUserData;
import org.bukkit.event.player.PlayerQuitEvent;

public class Leave extends Event {

    public Leave(BossPVE plugin) {
        super(plugin, PlayerQuitEvent.class);
    }

    @Override
    public void onEvent(org.bukkit.event.Event event) {
        PlayerQuitEvent e = (PlayerQuitEvent) event;
        GamePlayer gamePlayer = plugin.getGameManager().getGamePlayer(e.getPlayer().getUniqueId());
        UtilUserData uud = gamePlayer.getCache();
        UtilUserData.save(plugin, uud);
        plugin.getGameManager().delete(gamePlayer);
    }

}
