package me.bubbles.bosspve.database.databases;

import me.bubbles.bosspve.database.presets.PlayerStringIntegerRelation;
import me.bubbles.bosspve.settings.Settings;
import me.bubbles.bosspve.utility.UtilDatabase;
import me.bubbles.bosspve.utility.UtilUserData;

import java.util.HashMap;
import java.util.UUID;

public class SettingsDB extends PlayerStringIntegerRelation {

    // TODO

    public SettingsDB(String address, int port, String database, String username, String password) {
        super(address, port, database, username, password, "Player_Settings");
    }

    public int getValue(UUID uuid, Settings setting) {
        if(!hasValue(uuid,setting.toString())) {
            setRelation(uuid, setting.toString(), setting.getIndex(setting.getDefault()));
            return setting.getIndex(setting.getDefault());
        }
        return getEntry(uuid, setting.toString());
    }

    public static int getValue(UtilUserData uud, Settings setting) {
        HashMap<String, Integer> settings = uud.getSettings();
        if(!settings.containsKey(setting.toString())) {
            int result = UtilDatabase.SettingsDB().getValue(uud.getUUID(), setting);
            uud.addSetting(setting.toString(), result);
            return result;
        }
        return settings.get(setting.toString());
    }

}
