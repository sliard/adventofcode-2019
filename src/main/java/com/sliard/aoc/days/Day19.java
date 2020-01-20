package com.sliard.aoc.days;

import com.sliard.aoc.Day;
import com.sliard.aoc.incode.InCode;

public class Day19 extends Day<Long> {

    public static void main(String[] args) {
        Day19 d = new Day19();
        d.init("/day19.txt");
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

        long result = 0;
        for(int x=0; x<50; x++) {
            for(int y=0; y<50; y++) {
                inCode.cleanData();
                inCode.input.add((long)x);
                inCode.input.add((long)y);
                inCode.compute();
                result += inCode.output.get(0);
            }
        }
        return result;
    }

    public Long part2() {

        int x = 300;
        int y = 0;

        while (true) {
            if(isSet(x, y)) {
                if(isSet(x-99,y+99)) {
                    return (x-99)*10000 + (long)y;
                } else {
                    x++;
                }
            } else {
                y++;
            }
        }


        // 10431282 your answer is too high
    }

    private boolean isSet(int x, int y) {
        inCode.cleanData();
        inCode.input.add((long)x);
        inCode.input.add((long)y);
        inCode.compute();
        return inCode.output.get(0) == 1;
    }

}
