package com.sliard.aoc.days;

import com.sliard.aoc.Day;
import com.sliard.aoc.incode.InCode;

public class Day02 extends Day<Long> {

    public static void main(String[] args) {
        Day02 d = new Day02();
        d.init("/day02.txt");
        d.printResult();

        // 234713 your answer is too low
        // 2692313 your answer is too low
        // 2692315
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
            inCode.alldata.put(1,12L);
            inCode.alldata.put(2,2L);
            inCode.compute();
            return inCode.alldata.get(0);
        } catch (Exception ex) {
            println("Read file error ("+fileName+") : "+ex.getMessage());
        }
        return 0L;
    }

    public Long part2() {
        try {
            InCode inCode = new InCode();
            inCode.initFromFile(fileName);

            for(int noun = 0; noun < 100; noun++) {
                for(int verb = 0; verb < 100; verb++) {
                    inCode.cleanData();
                    inCode.alldata.put(1,(long)noun);
                    inCode.alldata.put(2,(long)verb);
                    inCode.compute();
                    if(inCode.alldata.get(0) == 19690720) {
                        return 100*noun + (long)verb;
                    }
                }
            }
            return 0L;
        } catch (Exception ex) {
            println("Read file error ("+fileName+") : "+ex.getMessage());
        }
        return 0L;
    }


}
