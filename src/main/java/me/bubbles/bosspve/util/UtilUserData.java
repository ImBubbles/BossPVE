package me.bubbles.bosspve.util;

import me.bubbles.bosspve.BossPVE;
import me.bubbles.bosspve.database.databases.SettingsDB;

import java.util.HashMap;
import java.util.UUID;

public class UtilUserData {

    private BossPVE plugin;
    private UUID uuid;
    private int xp;
    private HashMap<String, Integer> settings;

    public UtilUserData(UUID uuid, int xp) {
        this.uuid=uuid;
        this.xp=xp;
        SettingsDB db = UtilDatabase.SettingsDB();
        settings=db.getEntries(uuid);
    }

    public UUID getUUID() {
        return uuid;
    }

    public int getXp() {
        return xp;
    }

    public void setXp(int xp) {
        this.xp=xp;
    }

    public HashMap<String, Integer> getSettings() {
        return settings;
    }

    public int getLevel() {
        return UtilNumber.xpToLevel(xp);
    }

    public static void save(BossPVE plugin, UtilUserData uud) {
        UtilDatabase.getXpDB().setRelation(uud.getUUID(), uud.getXp());
        plugin.getGameManager().getGamePlayer(uud.getUUID()).updateCache(uud);
    }

    public static UtilUserData getUtilUserData(UUID uuid) {
        return new UtilUserData(
                uuid,
                UtilDatabase.getXpDB().getValue(uuid)
        );
    }

}
