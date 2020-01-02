package com.sliard.aoc.incode;

public enum ParameterMode {
    POSITION(0), IMMEDIATE(1);

    int val;

    ParameterMode(int val) {
        this.val = val;
    }

    public static ParameterMode getFromVal(long val) {
        for(ParameterMode op : ParameterMode.values()) {
            if(op.val == (int)val) {
                return op;
            }
        }
        throw new IllegalArgumentException("ParameterMode : bad value "+val);
    }
}
