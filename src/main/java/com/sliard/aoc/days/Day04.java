package com.sliard.aoc.days;

import com.sliard.aoc.Day;

public class Day04 extends Day<Integer> {

    public static void main(String[] args) {
        Day04 d = new Day04();
        d.init("123257","647015");
        d.printResult();

    }

    int start;
    int end;

    public void init(String ...args) {
        // init stuff
        try {
            start = Integer.parseInt(args[0]);
            end = Integer.parseInt(args[1]);
        } catch (Exception ex) {
            println("Read args error ("+args[0]+") : "+ex.getMessage());
        }
    }

    public Integer part1() {

        int result = 0;

        for(int i = start; i <= end; i++) {
            result += rulesPart1(i) ? 1 : 0;
        }

        return result;
    }

    public boolean rulesPart1(int i) {
        boolean hasDouble = false;

        int val = i;
        int before = val % 10;
        val = val / 10;
        int digit;
        while(val > 0) {
            digit = val % 10;
            if(before < digit) {
                return false;
            }
            hasDouble |= digit == before;
            before = val % 10;
            val = val / 10;
        }

        return hasDouble;
    }


    public boolean rulesPart2(int input) {
        int[] nbMultiple = new int[10];

        int val = input;
        int before = val % 10;
        val = val / 10;
        nbMultiple[before]++;
        int digit;
        while(val > 0) {
            digit = val % 10;
            if(before < digit) {
                return false;
            }
            nbMultiple[digit]++;
            before = val % 10;
            val = val / 10;
        }

        for(int j=0; j<nbMultiple.length; j++) {
            if(nbMultiple[j] == 2) {
                return true;
            }
        }

        return false;
    }

    public Integer part2() {

        int result = 0;

        for(int i = start; i <= end; i++) {
            result += rulesPart2(i) ? 1 : 0;
        }

        return result;

    }

}
