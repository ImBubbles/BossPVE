package me.bubbles.bosspve.utility.location;

import com.sk89q.worldguard.WorldGuard;
import me.bubbles.bosspve.BossPVE;
import me.bubbles.bosspve.configs.Config;
import me.bubbles.bosspve.entities.manager.IEntity;
import me.bubbles.bosspve.stages.Stage;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.HashMap;
import java.util.UUID;

public class MapCreator {

    private static final HashMap<UUID, ConfigurationSection> selectedStage = new HashMap<>();

    public static boolean selectStage(UUID uuid, int stage) {
        ConfigurationSection section = getStage(stage);
        if(section==null) {
            return false;
        }
        return selectStage(uuid, section);
    }

    public static boolean selectStage(UUID uuid, ConfigurationSection section) {
        if(section==null) {
            return false;
        }
        selectedStage.remove(uuid);
        selectedStage.put(uuid, section);
        return true;
    }

    public static boolean createStage(int stage, Location spawn, Location pos1, Location pos2, double xpMultiplier, double moneyMultiplier, int killAll, int maxEntities) {
        Config config = BossPVE.getInstance().getConfigManager().getConfig("stages.yml");
        FileConfiguration fileConfiguration = config.getFileConfiguration();
        if(fileConfiguration.contains(String.valueOf(stage))) {
            return false;
        }
        ConfigurationSection section = fileConfiguration.createSection(String.valueOf(stage));

        // SPAWN

        setSpawn(section, spawn);

        // REGION

        setPos1(section, pos1);
        setPos2(section, pos2);

        // MULTIPLIERS
        setXpMultiplier(section , xpMultiplier);
        setMoneyMultiplier(section, moneyMultiplier);

        // KILL ALL

        setKillAll(section, killAll);

        // MAX ENTITIES

        setMaxEntities(section, maxEntities);

        // MAKE REGION

        // TODO: Regionfy plugin

        return true;
    }

    public static void setSpawn(ConfigurationSection section, Location spawn) {
        section.set("spawn", UtilLocation.asLocationString(spawn));
    }

    public static void setPos1(ConfigurationSection section, Location pos1) {
        section.set("pos1", pos1);
    }

    public static void setPos2(ConfigurationSection section, Location pos2) {
        section.set("pos2", pos2);
    }

    public static void setXpMultiplier(ConfigurationSection section, double xpMultiplier) {
        section.set("xpMultiplier", xpMultiplier);
    }

    public static void setMoneyMultiplier(ConfigurationSection section, double moneyMultiplier) {
        section.set("moneyMultiplier", moneyMultiplier);
    }

    public static void setKillAll(ConfigurationSection section, int killAll) {
        section.set("killAll", killAll);
    }

    public static void setMaxEntities(ConfigurationSection section, int maxEntities) {
        section.set("maxEntities", maxEntities);
    }

    public static void addEntity(ConfigurationSection section, IEntity iEntity, Location location, int interval) {
        addEntity(section, iEntity.getNBTIdentifier(), location, interval);
    }

    public static void addEntity(ConfigurationSection section, String value, Location location, int interval) {
        ConfigurationSection entities = section.getConfigurationSection("entities");
        if(entities==null) {
            entities=section.createSection("entities");
        }
        int newKey = entities.getKeys(false).size()+1;
        ConfigurationSection entity = entities.createSection(String.valueOf(newKey));
        entity.set("entity", value);
        entity.set("pos", UtilLocation.asLocationString(location));
        entity.set("interval", interval);
    }

    public static ConfigurationSection getStage(int stage) {
        Config config = BossPVE.getInstance().getConfigManager().getConfig("stages.yml");
        FileConfiguration fileConfiguration = config.getFileConfiguration();
        return fileConfiguration.getConfigurationSection(String.valueOf(stage));
    }

    public static ConfigurationSection getStage(Stage stage) {
        return getStage(stage.getLevelRequirement());
    }

    public static void save() {
        Config config = BossPVE.getInstance().getConfigManager().getConfig("stages.yml");
        config.save();
    }

}