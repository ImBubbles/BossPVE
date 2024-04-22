package me.bubbles.bosspve.settings;

public enum Settings {

    KILL_MESSAGES("Kill Messages", 1),
    PROCC_MESSAGES("Activation Messages", 1);

    private String displayName;
    private int defaultValue;

    Settings(String s, int i) {
        this.displayName=s;
        this.defaultValue=i;
    }

    public String getDisplayName() {
        return displayName;
    }

    public int getDefaultValue() {
        return defaultValue;
    }

}
