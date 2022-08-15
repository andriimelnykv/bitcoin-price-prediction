package org.bpp.model;

public class ChartPoint {
    private final double x;
    private final double y;

    public static ChartPoint of(double x, double y) {
        return new ChartPoint(x, y);
    }

    public ChartPoint(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
}