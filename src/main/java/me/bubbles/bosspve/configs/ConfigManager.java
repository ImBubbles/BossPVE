package me.bubbles.bosspve.configs;

import me.bubbles.bosspve.BossPVE;

import java.util.HashSet;

public class ConfigManager {

    private HashSet<Config> configList = new HashSet<>();

    public void addConfig(String... names) {
        for(String name : names) {
            configList.add(new Config(name));
        }
    }

    public Config getConfig(String name) {
        for(Config config : configList) {
            if(config.getName().equals(name)) {
                return config;
            }
        }
        return null;
    }

    public void reloadAll() {
        configList.forEach(Config::reload);
    }

    public void saveAll() {
        configList.forEach(Config::save);
    }

}
