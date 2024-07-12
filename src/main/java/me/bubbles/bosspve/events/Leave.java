package me.bubbles.bosspve.events;

import me.bubbles.bosspve.BossPVE;
import me.bubbles.bosspve.events.manager.Event;
import me.bubbles.bosspve.game.GamePlayer;
import me.bubbles.bosspve.utility.UtilUserData;
import org.bukkit.event.player.PlayerQuitEvent;

public class Leave extends Event {

    public Leave() {
        super(PlayerQuitEvent.class);
    }

    @Override
    public void onEvent(org.bukkit.event.Event event) {
        PlayerQuitEvent e = (PlayerQuitEvent) event;
        e.setQuitMessage("");
        GamePlayer gamePlayer = BossPVE.getInstance().getGameManager().getGamePlayer(e.getPlayer().getUniqueId());
        UtilUserData uud = gamePlayer.getCache();
        UtilUserData.save(uud);
        BossPVE.getInstance().getGameManager().delete(gamePlayer);
    }

}
