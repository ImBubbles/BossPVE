package me.bubbles.bosspve.configs;

import me.bubbles.bosspve.BossPVE;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class Config {

    private File file;
    private String name;
    private FileConfiguration fileConfiguration;
    private BossPVE plugin;

    public Config(BossPVE plugin, String name) {
        this(plugin,new File(plugin.getDataFolder(),name));
    }

    public Config(BossPVE plugin, File file) {

        this.plugin=plugin;

        plugin.saveResource(file.getName(),false);

        fileConfiguration = YamlConfiguration.loadConfiguration(file);
        try {
            fileConfiguration.save(file);
        } catch (IOException e) {
            // poop
        }

        this.file=file;
        this.name=file.getName();

    }

    public FileConfiguration getFileConfiguration() {
        return fileConfiguration;
    }
    public String getName() {
        return name;
    }
    public File getFile() {
        return file;
    }

    public void reload() {
        this.file=new File(file.getPath());
    }

    public void save() {
        try {
            fileConfiguration.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
