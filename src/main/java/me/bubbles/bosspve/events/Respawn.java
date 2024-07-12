package me.bubbles.bosspve.events;

import me.bubbles.bosspve.BossPVE;
import me.bubbles.bosspve.events.manager.Event;
import me.bubbles.bosspve.game.GamePlayer;
import me.bubbles.bosspve.stages.Stage;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerRespawnEvent;

public class Respawn extends Event {

    public Respawn() {
        super(PlayerRespawnEvent.class);
    }

    @Override
    public void onEvent(org.bukkit.event.Event event) {
        PlayerRespawnEvent e = (PlayerRespawnEvent) event;
        Player player = e.getPlayer();
        GamePlayer gamePlayer = BossPVE.getInstance().getGameManager().getGamePlayer(player);
        gamePlayer.setHealth(gamePlayer.getMaxHealth());
        Stage stage = BossPVE.getInstance().getStageManager().getStage(player.getLocation());
        if(stage!=null) {
            e.setRespawnLocation(stage.getSpawn());
        }
    }

}
