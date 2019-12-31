package com.sliard.aoc.days;

import com.sliard.aoc.Day;
import com.sliard.aoc.utils.ReadTxtFile;

import java.util.List;

public class Day01 extends Day<Long> {

    public static void main(String[] args) {
        Day01 d = new Day01();
        d.init("/day01.txt");
        d.printResult();

        // 37099 your answer is too low
    }

    List<String> allLines;

    public void init(String ...args) {
        // init stuff
        if(args == null || args.length == 0) {
            println("No args");
            return;
        }
        try {
            allLines = ReadTxtFile.readFileAsStringList(args[0]);
        } catch (Exception ex) {
            println("Read file error ("+args[0]+") : "+ex.getMessage());
        }
    }

    public Long part1() {
        long result = 0;
        for(String line : allLines) {
            long val = Long.parseLong(line);
            result += (val/3)-2;
        }
        return result;
    }

    public Long part2() {
        long result = 0;
        for(String line : allLines) {
            long val = Long.parseLong(line);
            result += getRequiresFuel(val);
        }
        return result;
    }

    public Long getRequiresFuel(long mass) {
        long result = (mass/3-2);
        if(result <= 0 ){
            return 0L;
        }
        return result + getRequiresFuel(result);
    }

}
