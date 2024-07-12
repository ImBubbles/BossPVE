package me.bubbles.bosspve.utility.chance;

import me.bubbles.bosspve.utility.UtilNumber;

public class Chance<T> {


    private T result;
    private T normal;

    private double max;
    private double min;
    private double below;

    public Chance(T result, T normal, double min, double max, double below) {
        this.result=result;
        this.normal=normal;
        this.min=min;
        this.max=max;
        this.below=below;
    }

    public T roll(boolean force) {
        if(force) {
            return result;
        }
        if(UtilNumber.rollTheDice(min, max, below)) {
            return result;
        }
        return normal;
    }

    public T roll() {
        return roll(false);
    }

    public T getPossible() {
        return result;
    }

    public T getNormal() {
        return normal;
    }

    public double getPercentChance() {
        return ((double) below /(max-min))*100D;
    }

}