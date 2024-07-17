package me.bubbles.bosspve.settings;

import me.bubbles.bosspve.utility.UtilNumber;
import org.bukkit.Material;

import java.util.List;

public abstract class Setting<T> implements ISetting<T> {

    private final String key;
    private final String displayName;

    private final List<T> options;
    private final T normal;

    public Setting(String displayName, T normal, List<T> options) {
        this(
                displayName.replace("-", "").replace(" ", "_").toUpperCase(),
                displayName,
                normal,
                options
        );
    }

    public Setting(String key, String displayName, T normal, List<T> options) {
        this.key=prepareKey(key);
        this.displayName=displayName;
        this.normal=normal;
        this.options=options;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getKey() {
        return key;
    }

    public int getIndex(T option) {
        return options.indexOf(option);
    }

    public T getOption(int index) {
        return options.get(index);
    }

    public T getDefault() {
        return normal;
    }

    public T getNext(T option) {
        int current = options.indexOf(option);
        int next = (int) UtilNumber.clampLoop(options.size()-1, 0, current+1);
        return options.get(next);
    }

    private String prepareKey(String string) {
        return string.replace("-", "").replace(" ", "_").toUpperCase();
    }

}