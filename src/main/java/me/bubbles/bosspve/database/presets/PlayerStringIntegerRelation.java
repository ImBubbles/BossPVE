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
                        "id INT AUTO_INCREMENT," +
                        "uuid CHAR(36)," +
                        "key CHAR(36)," +
                        "value INT," +
                        "PRIMARY KEY (uuid)" +
                        ")"
        );
    }

    public HashMap<String, Integer> getEntries(UUID player) {
        HashMap<String, Integer> result = new HashMap<>();
        try (Connection connection = getConnection()) {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM " + tableName + " WHERE uuid=?");
            statement.setString(1, player.toString());
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                result.put(rs.getString("key"),rs.getInt("value"));
            }
            rs.close();
            statement.close();
        } catch (Exception exc) {
            exc.printStackTrace();
        }
        return result;
    }

    public int getEntry(UUID player, String key) {
        int result = -1;
        try (Connection connection = getConnection()) {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM " + tableName + " WHERE uuid=? AND key=?");
            statement.setString(1, player.toString());
            statement.setString(2, key);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                result = rs.getInt("value");
            }
            rs.close();
            statement.close();
        } catch (Exception exc) {
            exc.printStackTrace();
        }
        return result;
    }

    public boolean setRelation(UUID player, String string, int value) {
        removeRelation(player, string);
        try (Connection connection = getConnection()) {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO " + tableName + " " +
                    "(uuid, key, value) VALUES (?, ?, ?)");

            statement.setString(1, player.toString());
            statement.setString(2, string);
            statement.setInt(3, value);
            statement.execute();
            statement.close();
        } catch (Exception exc) {
            exc.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean removeRelation(UUID player, String string) {
        try (Connection connection = getConnection()) {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM " + tableName + " WHERE uuid=? AND key=?");
            statement.setString(1, player.toString());
            statement.setString(2, string);
            statement.execute();
            statement.close();
        } catch (Exception exc) {
            exc.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean hasValue(UUID player, String string) {
        boolean result = false;
        try (Connection connection = getConnection()) {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM " + tableName + " WHERE uuid=? AND key=?");
            statement.setString(1, player.toString());
            statement.setString(2, string);
            ResultSet rs = statement.executeQuery();
            result = rs.next();
            rs.close();
            statement.close();
        } catch (Exception exc) {
            exc.printStackTrace();
        }
        return result;
    }

}
