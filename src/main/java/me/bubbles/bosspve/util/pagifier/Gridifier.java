package me.bubbles.bosspve.util.pagifier;

import me.bubbles.bosspve.util.pagifier.pager.Amount;
import me.bubbles.bosspve.util.pagifier.pager.generic.Generic3DArray;

public class Gridifier<T> {

    private Generic3DArray<T> pages;

    private Class<T> type;
    private int rows;
    private int columns;
    private Pagifier<T> pagifier;

    public Gridifier(Class<T> type, T[] list, int rows, int columns) {
        this.type=type;
        this.rows=rows;
        this.columns=columns;
        this.pagifier = new Pagifier<T>(list, new Amount<T>(type, columns));
        this.pages=getPages();
    }

    public T[][] getPage(int num) {
        return pages.get(num);
    }

    private Generic3DArray<T> getPages() {
        Generic3DArray<T> result = new Generic3DArray<>(type, getTotalPages(), rows, columns);
        int page=0;
        int y=0;
        for(int i=0; i<pagifier.getTotalPages(); i++) {
            result.set(page, y, pagifier.getPage(i));
            y++;
            if(y==columns) {
                y=0;
                page++;
            }
        }
        return result;
    }

    public int getTotalPages() {
        return pagifier.getTotalPages()%columns==0 ? pagifier.getTotalPages()/columns : (pagifier.getTotalPages()/columns)+1;
    }

}