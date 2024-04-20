package me.bubbles.bosspve.util.pagifier.pager;

import me.bubbles.online.util.pagifier.pager.generic.Generic2DArray;

public class Amount<T> implements Pager<T> {

    private int columns;
    private Class<T> type;

    public Amount(Class<T> type, int columns) {
        this.columns=columns;
        this.type=type;
    }

    @Override
    public T[][] pagify(T[] objects) {

        int rows = objects.length%columns==0 ? objects.length/columns : (objects.length/columns)+1;

        Generic2DArray<T> result = new Generic2DArray<>(type, rows, columns);
        int index = 0;
        for(int i = 0; i<rows; i++) {
            for(int j = 0; j<columns; j++) {
                result.set(i, j, objects[index]);
                index++;
            }
        }
        return result.get();
    }

}
