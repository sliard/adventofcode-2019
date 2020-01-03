package com.sliard.aoc.days;

import com.sliard.aoc.Day;
import com.sliard.aoc.incode.InCode;

public class Day09 extends Day<Long> {

    public static void main(String[] args) {
        Day09 d = new Day09();
        d.init("/day09.txt");
        d.printResult();

    }

    InCode inCode;

    public void init(String ...args) {
        // init stuff
        try {
            inCode = new InCode();
            inCode.initFromFile(args[0]);
        } catch (Exception ex) {
            println("Read file error ("+args[0]+") : "+ex.getMessage());
        }
    }

    public Long part1() {
        inCode.input.add(1L);
        inCode.compute();
        return inCode.output.get(0);
    }

    public Long part2() {
        inCode.cleanData();
        inCode.input.add(2L);
        inCode.compute();
        return inCode.output.get(0);
    }

}
