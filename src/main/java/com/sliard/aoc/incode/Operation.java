package com.sliard.aoc.incode;

public enum Operation {
    ADD(1), MUL(2), END(99);

    int val;

    Operation(int val) {
        this.val = val;
    }

    public static Operation getFromVal(long val) {
        for(Operation op : Operation.values()) {
            if(op.val == (int)val) {
                return op;
            }
        }
        return END;
    }
}
