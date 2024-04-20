package me.bubbles.bosspve.util.pagifier;

import me.bubbles.online.util.pagifier.pager.Pager;

public class Pagifier<T> {
    private T[][] pagified;

    public Pagifier(T[] list, Pager<T> pagerType) {
        this.pagified=pagerType.pagify(list);
    }

    public T[][] getPages() {
        return pagified;
    }

    public T[] getPage(int i) {
        return pagified[i];
    }

    public int getTotalPages() {
        return pagified.length;
    }

}
