package com.sliard.aoc.days;

import com.sliard.aoc.Day;
import com.sliard.aoc.incode.InCode;

public class Day21 extends Day<Long> {

    public static void main(String[] args) {
        Day21 d = new Day21();
        d.init("/day21.txt");
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

        // D && !(A && B && C)

        inCode.setInput("OR A T");
        inCode.setInput("AND B T");
        inCode.setInput("AND C T");
        inCode.setInput("NOT T T");
        inCode.setInput("AND D T");
        inCode.setInput("OR T J");
        inCode.setInput("WALK");
        inCode.compute();

        inCode.getAsciiOutput(true);

        return inCode.getNotAsciiOutput();
    }

    public Long part2() {
        inCode.cleanData();
        //   ---*---*-
        //   ABCDEFGHI

        // D && (!A || (!B && H) || (!C && H))

        inCode.setInput("NOT C T");
        inCode.setInput("AND H T");
        inCode.setInput("NOT B J");
        inCode.setInput("AND H J");
        inCode.setInput("OR J T");
        inCode.setInput("NOT A J");
        inCode.setInput("OR T J");
        inCode.setInput("AND D J");
        inCode.setInput("RUN");
        inCode.compute();

        inCode.getAsciiOutput(true);

        return inCode.getNotAsciiOutput();
    }

}
