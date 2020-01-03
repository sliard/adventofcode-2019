package com.sliard.aoc.incode;

public enum Operation {
    ADD(1, 4),
    MUL(2, 4),
    INPUT(3,2),
    OUTPUT(4,2),
    JUMP_IF_TRUE(5, 3),
    JUMP_IF_FALSE(6, 3),
    LESS_THAN(7, 4),
    EQUALS(8, 4),
    OFFSET(9, 2),
    END(99,1);

    int val;
    int nbElem;

    Operation(int val, int nbElem) {
        this.val = val;
        this.nbElem = nbElem;
    }

    public static Operation getFromVal(long val) {
        for(Operation op : Operation.values()) {
            if(op.val == (int)val) {
                return op;
            }
        }
        throw new IllegalArgumentException("Operation : bad value "+val);
    }
}
