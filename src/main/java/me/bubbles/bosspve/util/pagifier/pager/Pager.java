package me.bubbles.bosspve.util.pagifier.pager;

public interface Pager<T> {

    T[][] pagify(T[] objects);

}
