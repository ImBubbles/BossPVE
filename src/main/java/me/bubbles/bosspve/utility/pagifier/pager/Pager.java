package me.bubbles.bosspve.utility.pagifier.pager;

public interface Pager<T> {

    T[][] pagify(T[] objects);

}
