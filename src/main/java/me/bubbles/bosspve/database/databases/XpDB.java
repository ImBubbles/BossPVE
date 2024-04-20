package me.bubbles.bosspve.database.databases;

import me.bubbles.bosspve.database.presets.PlayerIntegerRelation;
import me.bubbles.bosspve.util.UtilNumber;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.UUID;

public class XpDB extends PlayerIntegerRelation {

    public XpDB(String address, int port, String database, String username, String password) {
        super(address, port, database, username, password, "Player_XP");
    }

    public ArrayList<UUID> sortByXP() {
        ArrayList<UUID> result = new ArrayList<>();
        try {
            Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM "+tableName+" ORDER BY xp DESC");
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()) {
                result.add(UUID.fromString(resultSet.getString("uuid")));
            }
        } catch (Exception exc) {
            exc.printStackTrace();
        }
        return result;
    }

    public int getLevel(UUID uuid) {
        return UtilNumber.xpToLevel(getValue(uuid));
    }

    public int getPosition(UUID uuid) {
        ArrayList<UUID> list = sortByXP();
        if(list.isEmpty()) {
            return -1;
        }
        int i=1;
        while(!list.get(i-1).equals(uuid)) {
            i++;
        }
        return i;
    }

}
