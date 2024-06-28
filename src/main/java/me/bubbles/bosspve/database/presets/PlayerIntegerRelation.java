package me.bubbles.bosspve.database.presets;

import me.bubbles.bosspve.database.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.UUID;

public abstract class PlayerIntegerRelation extends Database {

    public PlayerIntegerRelation(String address, int port, String database, String username, String password, String tableName) {
        super(address, port, database, username, password, tableName,
                "CREATE TABLE IF NOT EXISTS " + tableName + " (" +
                        "uuid CHAR(36), " +
                        "val INT, " +
                        "PRIMARY KEY (uuid)" +
                        ")"
        );
    }

    public int getValue(UUID player) {
        int result = -1;
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "SELECT * FROM " + tableName + " WHERE uuid=?");) {
            statement.setString(1, player.toString());
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                result=rs.getInt("val");
            }
            rs.close();
        } catch (Exception exc) {
            exc.printStackTrace();
        }
        return result;
    }

    public boolean setRelation(UUID player, int value) {
        removeRelation(player);
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement("INSERT INTO " + tableName + " " +
                     "(uuid, val) VALUES (?, ?)");) {
            statement.setString(1, player.toString());
            statement.setInt(2, value);
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
