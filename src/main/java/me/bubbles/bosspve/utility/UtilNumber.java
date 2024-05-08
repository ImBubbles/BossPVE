package me.bubbles.bosspve.utility;

public class UtilNumber {

    public static boolean rollTheDice(double min, double max, double below) {
        return Math.random() * (max - min)<=below;
    }

    public static int xpToLevel(double xp) {
        return ((int) Math.sqrt(xp/10));
    }

    public static double clampBorder(double max, double min, double now) {
        if(Math.max(max,now)>max) {
            return max;
        }
        if(Math.min(min,now)<min) {
            return min;
        }
        return now;
    }

    public static double clampLoop(double max, double min, double now) {
        if(Math.max(max,now)>max) {
            return min+((now-1)-max);
        }
        if(Math.min(min,now)<min) {
            return max-(Math.abs(min+now));
        }
        return now;
    }

    public static double clampToPrevious(double max, double min, double now, double prev) {
        if(Math.max(max,now)>max) {
            return prev;
        }
        if(Math.min(min,now)<min) {
            return prev;
        }
        return now;
    }

}
