package me.bubbles.bosspve.database.databases;

import me.bubbles.bosspve.BossPVE;
import me.bubbles.bosspve.database.presets.PlayerStringIntegerRelation;
import me.bubbles.bosspve.game.GamePlayer;
import me.bubbles.bosspve.settings.Settings;
import me.bubbles.bosspve.util.UtilDatabase;
import me.bubbles.bosspve.util.UtilUserData;

import java.util.HashMap;
import java.util.UUID;

public class SettingsDB extends PlayerStringIntegerRelation {

    // TODO

    public SettingsDB(String address, int port, String database, String username, String password) {
        super(address, port, database, username, password, "Player_Settings");
    }

    public int getValue(UUID uuid, Settings setting) {
        if(!hasValue(uuid,setting.toString())) {
            setRelation(uuid, setting.toString(), setting.getDefaultValue());
        }
        return getEntry(uuid, setting.toString());
    }

    public static int getValue(UtilUserData uud, Settings setting) {
        HashMap<String, Integer> settings = uud.getSettings();
        if(!settings.containsKey(setting.toString())) {
            return UtilDatabase.SettingsDB().getValue(uud.getUUID(), setting);
        }
        return settings.get(setting.toString());
    }

}
