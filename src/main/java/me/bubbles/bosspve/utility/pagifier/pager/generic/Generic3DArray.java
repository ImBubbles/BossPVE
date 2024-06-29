package me.bubbles.bosspve.utility.pagifier.pager.generic;

import java.lang.reflect.Array;

public class Generic3DArray<T> {

    private T[][][] array;

    public Generic3DArray(Class<T> type, int depth, int rows, int columns) {
        array = (T[][][]) Array.newInstance(type, depth, rows, columns);
    }

    public void set(int i, int j, int k, T value) {
        array[i][j][k] = value;
    }

    public void set(int i, int j, T[] value) {
        for(int k = 0; k<value.length; k++) {
            /*System.out.println("i: "+i);
            System.out.println("j: "+j);
            System.out.println("k: "+k);
            System.out.println("val k: "+value[k]);*/
            array[i][j][k]=value[k];
        }
    }

    public T get(int i, int j, int k) {
        return array[i][j][k];
    }

    public T[] get(int i, int j) {
        return array[i][j];
    }

    public T[][] get(int i) {
        return array[i];
    }

    public T[][][] get() {
        return array;
    }

}
