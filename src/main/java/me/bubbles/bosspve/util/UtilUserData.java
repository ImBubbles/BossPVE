package me.bubbles.bosspve.util;

import java.util.UUID;

public class UtilUserData {

    private UUID uuid;
    private int xp;

    public UtilUserData(UUID uuid, int xp) {
        this.uuid=uuid;
        this.xp=xp;
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

    public int getLevel() {
        return UtilNumber.xpToLevel(xp);
    }

    public static void save(UtilUserData uud) {
        UtilDatabase.getXpDB().setRelation(uud.getUUID(), uud.getXp());
    }

    public static UtilUserData getUtilUserData(UUID uuid) {
        return new UtilUserData(
                uuid,
                UtilDatabase.getXpDB().getValue(uuid)
        );
    }

}
