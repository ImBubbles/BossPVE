package me.bubbles.bosspve.items.flags;

public class Flag<T> implements IFlag {

    Flags name;
    T value;
    boolean passive;

    public Flag(Flags name, T value, boolean passive) {
        this.name=name;
        this.value=value;
        this.passive=passive;
    }

    @Override
    public T getValue() {
        return value;
    }

    @Override
    public Flags getName() {
        return name;
    }

    @Override
    public boolean isPassive() {
        return false;
    }

}
