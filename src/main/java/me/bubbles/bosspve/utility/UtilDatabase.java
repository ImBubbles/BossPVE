package me.bubbles.bosspve.utility;

import me.bubbles.bosspve.BossPVE;
import me.bubbles.bosspve.configs.Config;
import me.bubbles.bosspve.configs.ConfigManager;
import me.bubbles.bosspve.database.databases.SettingsDB;
import me.bubbles.bosspve.database.databases.XpDB;
import org.bukkit.configuration.file.FileConfiguration;

public class UtilDatabase {
    private static String ADDRESS;
    private static int PORT = 3306;
    private static String DATABASE;
    private static String USERNAME;
    private static String PASSWORD;
    private static boolean initialized = false;

    public UtilDatabase() {
        if (initialized) throw new IllegalStateException("Already initialized");
        final ConfigManager configManager = BossPVE.getInstance().getConfigManager();
        Config config = configManager.getConfig("config.yml");
        FileConfiguration fileConfig = config.getFileConfiguration();
        ADDRESS = fileConfig.getString("database.address");
        DATABASE = fileConfig.getString("database.database");
        USERNAME = fileConfig.getString("database.username");
        PASSWORD = fileConfig.getString("database.password");
        PORT = fileConfig.getInt("database.port");
        initialized = true;
    }

    public static XpDB getXpDB() {
        return new XpDB(ADDRESS, PORT, DATABASE, USERNAME, PASSWORD);
    }

    public static SettingsDB SettingsDB() {
        return new SettingsDB(ADDRESS, PORT, DATABASE, USERNAME, PASSWORD);
    }

}
