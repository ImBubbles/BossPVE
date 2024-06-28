package me.bubbles.bosspve.database.presets;

import me.bubbles.bosspve.database.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.UUID;

public abstract class PlayerBooleanRelation extends Database {

    public PlayerBooleanRelation(String address, int port, String database, String username, String password, String tableName) {
        super(address, port, database, username, password, tableName,
                "CREATE TABLE IF NOT EXISTS " + tableName + " (" +
                        "uuid CHAR(36), " +
                        "val TINYINT, " +
                        "PRIMARY KEY (uuid)" +
                        ")"
        );
    }

    public boolean getValue(UUID player) {
        Boolean result = false;
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "SELECT * FROM " + tableName + " WHERE uuid=?");) {
            statement.setString(1, player.toString());
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                int boolVal = rs.getInt("val");
                if(rs.getInt("val")==1) {
                    result=true;
                }
            }
            rs.close();
        } catch (Exception exc) {
            exc.printStackTrace();
        }
        return result;
    }

    public boolean setRelation(UUID player, boolean value) {
        removeRelation(player);
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement("INSERT INTO " + tableName + " " +
                     "(uuid, val) VALUES (?, ?)");) {
            statement.setString(1, player.toString());
            int boolVal = value==false ? 0 : 1;
            statement.setInt(2, boolVal);
            statement.execute();
        } catch (Exception exc) {
            exc.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean removeRelation(UUID player) {
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement("DELETE FROM " + tableName + " WHERE uuid=?");) {
            statement.setString(1, player.toString());
            statement.execute();
        } catch (Exception exc) {
            exc.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean hasValue(UUID player) {
        boolean result = false;
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "SELECT * FROM " + tableName + " WHERE uuid=?");) {
            statement.setString(1, player.toString());
            ResultSet rs = statement.executeQuery();
            result = rs.next();
            rs.close();
        } catch (Exception exc) {
            exc.printStackTrace();
        }
        return result;
    }

}
