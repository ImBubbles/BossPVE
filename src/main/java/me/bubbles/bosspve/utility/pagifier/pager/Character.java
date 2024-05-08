package me.bubbles.bosspve.utility.pagifier.pager;

public class Character implements Pager<String> {

    int limit;

    public Character(int limit) {
        this.limit=limit;
    }

    @Override
    public String[][] pagify(String[] strings) {
        String[][] result = new String[getYLim(strings)][getXLim(strings)];
        int y = 0;
        int x = 0;
        int currentLength=0;
        for(int i = 0; i<strings.length; i++) {
            if(currentLength>limit) {
                x=0;
                y++;
                currentLength=0;
                continue;
            }
            result[y][x] = strings[i];
            currentLength+=strings[i].length();
            x++;
        }
        return result;
    }

    private int getYLim(String[] strings) {
        int y = 0;
        int currentLength=0;
        for(int i = 0; i<strings.length; i++) {
            if(currentLength>limit) {
                y++;
                currentLength=0;
                continue;
            }
            currentLength+=strings[i].length();
        }
        return y+1;
    }

    private int getXLim(String[] strings) {
        int maxX = 0;
        int x = 0;
        int currentLength=0;
        for(int i = 0; i<strings.length; i++) {
            if(currentLength>limit) {
                x=0;
                currentLength=0;
                continue;
            }
            currentLength+=strings[i].length();
            x++;
            maxX = Math.max(maxX, x);
        }
        return maxX;
    }

    private boolean isDivisible(int num, int by) {
        return num%by==0;
    }

}
