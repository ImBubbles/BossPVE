package me.bubbles.bosspve.events;

import me.bubbles.bosspve.BossPVE;
import me.bubbles.bosspve.events.manager.Event;
import me.bubbles.bosspve.stages.Stage;
import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.util.Vector;

import java.util.Optional;

public class Respawn extends Event {

    public Respawn(BossPVE plugin) {
        super(plugin, PlayerRespawnEvent.class);
    }

    @Override
    public void onEvent(org.bukkit.event.Event event) {
        PlayerRespawnEvent e = (PlayerRespawnEvent) event;
        Player player = e.getPlayer();
        Stage stage = plugin.getStageManager().getStage(player.getLocation());
        if(stage!=null) {
            e.setRespawnLocation(stage.getSpawn());
        }
    }

}
