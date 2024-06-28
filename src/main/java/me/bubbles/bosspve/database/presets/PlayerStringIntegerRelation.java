package me.bubbles.bosspve.database.presets;

import me.bubbles.bosspve.database.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.UUID;

public abstract class PlayerStringIntegerRelation extends Database {

    public PlayerStringIntegerRelation(String address, int port, String database, String username, String password, String tableName) {
        super(address, port, database, username, password, tableName,
                "CREATE TABLE IF NOT EXISTS " + tableName + " (" +
                        "id INT AUTO_INCREMENT, " +
                        "uuid CHAR(36), " +
                        "str VARCHAR(36), " +
                        "val INT, " +
                        "PRIMARY KEY (id)" +
                        ")"
        );
    }

    public HashMap<String, Integer> getEntries(UUID player) {
        HashMap<String, Integer> result = new HashMap<>();
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "SELECT * FROM " + tableName + " WHERE uuid=?");) {
            statement.setString(1, player.toString());
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                result.put(rs.getString("str"),rs.getInt("val"));
            }
            rs.close();
        } catch (Exception exc) {
            exc.printStackTrace();
        }
        return result;
    }

    public int getEntry(UUID player, String str) {
        int result = -1;
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "SELECT * FROM " + tableName + " WHERE uuid=? AND val=?");) {
            statement.setString(1, player.toString());
            statement.setString(2, str);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                result = rs.getInt("val");
            }
            rs.close();
        } catch (Exception exc) {
            exc.printStackTrace();
        }
        return result;
    }

    public boolean setRelation(UUID player, String string, int value) {
        removeRelation(player, string);
        try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement("INSERT INTO " + tableName + " " +
                "(uuid, str, val) VALUES (?, ?, ?)");) {
            statement.setString(1, player.toString());
            statement.setString(2, string);
            statement.setInt(3, value);
            statement.execute();
        } catch (Exception exc) {
            exc.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean removeRelation(UUID player, String string) {
        try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement("DELETE FROM " + tableName + " WHERE uuid=? AND str=?");) {
            statement.setString(1, player.toString());
            statement.setString(2, string);
            statement.execute();
        } catch (Exception exc) {
            exc.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean hasValue(UUID player, String string) {
        boolean result = false;
        try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(
                "SELECT * FROM " + tableName + " WHERE uuid=? AND str=?");) {
            statement.setString(1, player.toString());
            statement.setString(2, string);
            ResultSet rs = statement.executeQuery();
            result = rs.next();
            rs.close();
        } catch (Exception exc) {
            exc.printStackTrace();
        }
        return result;
    }

}
