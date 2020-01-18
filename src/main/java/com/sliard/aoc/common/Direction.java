package com.sliard.aoc.common;

public enum Direction {
    NORTH("U", 1, 0, -1), EAST("R", 4,1, 0), SOUTH("D", 2, 0, 1), WEST("L", 3, -1, 0);

    String val;
    int intVal;
    public int deltaX;
    public int deltaY;

    Direction(String val, int intVal, int dx, int dy) {
        this.val = val;
        this.intVal = intVal;
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

    public static Direction fromIntVal(int val) {
        for(Direction op : Direction.values()) {
            if(op.intVal == val) {
                return op;
            }
        }
        return NORTH;
    }

    public int getIntVal() {
        return intVal;
    }
}
