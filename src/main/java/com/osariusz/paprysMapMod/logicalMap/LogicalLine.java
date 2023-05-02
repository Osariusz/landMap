package com.osariusz.paprysMapMod.logicalMap;

public class LogicalLine {
    int x1;
    int x2;

    int y;
    int color;

    public int getX1() {
        return x1;
    }

    public int getX2() {
        return x2;
    }

    public int getY() {
        return y;
    }

    public int getColor() {
        return color;
    }

    public LogicalLine(int x1, int x2, int y, int color) {
        this.x1 = x1;
        this.x2 = x2;
        this.y = y;
        this.color = color;
    }

}
