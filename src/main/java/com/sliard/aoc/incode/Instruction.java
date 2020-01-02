package com.sliard.aoc.incode;

import java.util.Arrays;

public class Instruction {
    public Operation op;
    public ParameterMode[] modes;

    public Instruction(long data) {
        this.op = Operation.getFromVal(data%100);
        modes = new ParameterMode[3];
        modes[0] = ParameterMode.getFromVal((data/100)%10);
        modes[1] = ParameterMode.getFromVal((data/1000)%10);
        modes[2] = ParameterMode.getFromVal((data/10000)%10);
    }

    @Override
    public String toString() {
        return "Instruction{" +
                "op=" + op +
                ", modes=" + Arrays.toString(modes) +
                '}';
    }
}
