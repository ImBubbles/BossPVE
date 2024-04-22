package me.bubbles.bosspve.flags;

public class Flag<T extends Enum<T>, F> {
    Enum<T> flag;
    F value;
    boolean passive;

    public Flag(Enum<T> flag, F value, boolean passive) {
        this.flag=flag;
        this.passive=passive;
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
