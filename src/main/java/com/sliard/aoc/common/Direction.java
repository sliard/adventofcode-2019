package com.sliard.aoc.common;

public enum Direction {
    NORTH("U", 0, -1), EAST("R", 1, 0), SOUTH("D", 0, 1), WEST("L", -1, 0);

    String val;
    public int deltaX;
    public int deltaY;

    Direction(String val, int dx, int dy) {
        this.val = val;
        this.deltaX = dx;
        this.deltaY = dy;
    }

    public static Direction fromVal(String val) {
        for(Direction op : Direction.values()) {
            if(op.val.equals(val.substring(0,1))) {
                return op;
            }
        }
        return NORTH;
    }
}
