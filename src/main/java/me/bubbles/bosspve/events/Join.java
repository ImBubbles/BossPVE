package me.bubbles.bosspve.events;

import me.bubbles.bosspve.BossPVE;
import me.bubbles.bosspve.events.manager.Event;
import me.bubbles.bosspve.utility.UtilUserData;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.UUID;

public class Join extends Event {

    public Join() {
        super(PlayerJoinEvent.class);
    }

    @Override
    public void onEvent(org.bukkit.event.Event event) {
        PlayerJoinEvent e = (PlayerJoinEvent) event;
        e.setJoinMessage("");
        /*if(Bukkit.getOnlinePlayers().size()==1) {
            for(World world : Bukkit.getWorlds()) {
                world.getEntities().forEach(entity -> {
                    if(!(entity instanceof Player)) {
                        LivingEntity livingEntity = (LivingEntity) entity;
                        livingEntity.setHealth(0);
                    }
                });
            }
        }*/
        UtilUserData uud = UtilUserData.getUtilUserData(e.getPlayer().getUniqueId());
        if(uud.getXp()==-1) {
            UUID uuid = e.getPlayer().getUniqueId();
            /*SettingsDB settingsDB = UtilDatabase.SettingsDB();
            settingsDB.getValue(uuid, Settings.KILL_MESSAGES);
            settingsDB.getValue(uuid, Settings.PROCC_MESSAGES);*/
            UtilUserData.save(new UtilUserData(uuid,0));
        }
    }

}
