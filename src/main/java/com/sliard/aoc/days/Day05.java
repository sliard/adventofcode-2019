package com.sliard.aoc.days;

import com.sliard.aoc.Day;
import com.sliard.aoc.incode.InCode;

public class Day05 extends Day<Long> {

    public static void main(String[] args) {
        Day05 d = new Day05();
        d.init("/day05.txt");
        d.printResult();
    }

     String fileName;

    public void init(String ...args) {
        // init stuff
        if(args == null || args.length == 0) {
            println("No args");
            return;
        }
        fileName = args[0];
    }

    public Long part1() {
        try {
            InCode inCode = new InCode();
            inCode.initFromFile(fileName);
            inCode.input.add(1L);
            inCode.compute();
            for(long val : inCode.output) {
                if(val != 0) {
                    return val;
                }
            }
        } catch (Exception ex) {
            println("Read file error ("+fileName+") : "+ex.getMessage());
        }
        return 0L;
    }

    public Long part2() {

        try {
            InCode inCode = new InCode();
            inCode.initFromFile(fileName);
            inCode.input.add(5L);
            inCode.compute();
            for(long val : inCode.output) {
                if(val != 0) {
                    return val;
                }
            }
        } catch (Exception ex) {
            println("Read file error ("+fileName+") : "+ex.getMessage());
        }
        return 0L;
    }


}
