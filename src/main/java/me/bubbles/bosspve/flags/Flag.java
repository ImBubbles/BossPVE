package me.bubbles.bosspve.flags;

public class Flag<T extends Enum<T>, F> {
    private final Enum<T> flag;
    private final F value;
    private final boolean passive;

    public Flag(Enum<T> flag, F value, boolean passive) {
        this.flag=flag;
        this.passive=passive;
        this.value=value;
    }

    public Enum<T> getFlag() {
        return flag;
    }
    public F getValue() {
        return value;
    }

    public boolean isPassive() {
        return false;
    }

}
