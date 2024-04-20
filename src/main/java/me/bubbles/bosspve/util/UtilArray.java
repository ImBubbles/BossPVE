package me.bubbles.bosspve.util;

public class UtilArray {

    public static <T> boolean contains(T[] array, T value) {
        for (T item : array) {
            if(item.equals(value)) {
                return true;
            }
        }
        return false;
    }

}
