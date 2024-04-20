package me.bubbles.bosspve.database.databases;

import me.bubbles.bosspve.database.presets.PlayerStringIntegerRelation;
import me.bubbles.bosspve.settings.Settings;

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

}
