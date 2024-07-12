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

    public Config(String name) {
        this(new File(BossPVE.getInstance().getDataFolder(),name));
    }

    public Config(File file) {

        BossPVE.getInstance().saveResource(file.getName(),false);

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
        this.fileConfiguration=YamlConfiguration.loadConfiguration(file);
    }

    public void save() {
        try {
            fileConfiguration.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
