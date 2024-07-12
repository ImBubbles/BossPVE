package me.bubbles.bosspve.utility.pagifier;

import me.bubbles.bosspve.utility.pagifier.pager.Amount;
import me.bubbles.bosspve.utility.pagifier.pager.generic.Generic3DArray;

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
        /*System.out.println("total pages: "+getTotalPages());
        System.out.println("rows: "+rows);
        System.out.println("columns: "+columns);*/
        Generic3DArray<T> result = new Generic3DArray<>(type, getTotalPages(), rows, columns);
        int page=0;
        int y=0;
        for(int i=0; i<pagifier.getTotalPages()-1; i++) {
            /*System.out.println("page: "+page);
            System.out.println("y: "+page);*/
            result.set(page, y, pagifier.getPage(i));
            //System.out.println("i="+i);
            y++;
            //System.out.println("y="+y);
            if(y==rows) {
                /*System.out.println("y=rows");
                System.out.println("page="+page);*/
                y=0;
                page++;
            }
        }
        return result;
    }

    public int getTotalPages() {
        return pagifier.getTotalPages()%rows==0 ? pagifier.getTotalPages()/rows : (pagifier.getTotalPages()/rows)+1;
    }

    /*public int getTotalPages() {
        return pages.get().length;
    }*/

}