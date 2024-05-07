package me.bubbles.bosspve.events;

import me.bubbles.bosspve.BossPVE;
import me.bubbles.bosspve.database.databases.SettingsDB;
import me.bubbles.bosspve.events.manager.Event;
import me.bubbles.bosspve.settings.Settings;
import me.bubbles.bosspve.util.UtilDatabase;
import me.bubbles.bosspve.util.UtilUserData;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.UUID;

public class Join extends Event {

    public Join(BossPVE plugin) {
        super(plugin, PlayerJoinEvent.class);
    }

    @Override
    public void onEvent(org.bukkit.event.Event event) {
        PlayerJoinEvent e = (PlayerJoinEvent) event;
        UtilUserData uud = UtilUserData.getUtilUserData(e.getPlayer().getUniqueId());
        if(uud.getXp()==-1) {
            UUID uuid = e.getPlayer().getUniqueId();
            /*SettingsDB settingsDB = UtilDatabase.SettingsDB();
            settingsDB.getValue(uuid, Settings.KILL_MESSAGES);
            settingsDB.getValue(uuid, Settings.PROCC_MESSAGES);*/
            UtilUserData.save(plugin, new UtilUserData(uuid,0));
        }
    }

}
