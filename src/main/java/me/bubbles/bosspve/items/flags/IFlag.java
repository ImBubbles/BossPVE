package me.bubbles.bosspve.items.flags;

public interface IFlag<T> {

    Flags getName();
    T getValue();
    boolean isPassive(); // HAVE TO HAVE THE ITEM EQUIPPED

}
