package me.bubbles.bosspve.utility.pagifier.pager.generic;

import java.lang.reflect.Array;

public class Generic2DArray<T> {

    private T[][] array;

    public Generic2DArray(Class<T> type, int rows, int columns) {
        array = (T[][]) Array.newInstance(type, rows, columns);
    }

    public void set(int i, int j, T value) {
        array[i][j] = value;
    }

    public T[][] get() {
        return array;
    }

}

