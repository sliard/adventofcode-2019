package com.sliard.aoc.days;

import com.sliard.aoc.Day;
import com.sliard.aoc.incode.InCode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day07 extends Day<Long> {

    public static void main(String[] args) {
        Day07 d = new Day07();
        d.init("/day07.txt");
        d.printResult();

    }

    public class Amplifier {
        public InCode incode;
        public int index;

        public Amplifier(String fileName) throws IOException {
            incode = new InCode();
            incode.initFromFile(fileName);
        }

        public void init(long phase) {
            incode.cleanData();
            incode.input.add(phase);
            index = 0;
        }

        public void compute() {
            index = incode.compute(index);
        }
    }

    Amplifier[] allAmplifiers;

    public void init(String ...args) {
        // init stuff
        try {
            allAmplifiers = new Amplifier[5];
            for(int i = 0; i<5; i++) {
                allAmplifiers[i] = new Amplifier(args[0]);
            }
        } catch (Exception ex) {
            println("Read file error ("+args[0]+") : "+ex.getMessage());
        }
    }

    public <E> List<List<E>> generatePerm(List<E> original) {
        if (original.isEmpty()) {
            List<List<E>> result = new ArrayList<>();
            result.add(new ArrayList<>());
            return result;
        }
        E firstElement = original.remove(0);
        List<List<E>> returnValue = new ArrayList<>();
        List<List<E>> permutations = generatePerm(original);
        for (List<E> smallerPermutated : permutations) {
            for (int index=0; index <= smallerPermutated.size(); index++) {
                List<E> temp = new ArrayList<>(smallerPermutated);
                temp.add(index, firstElement);
                returnValue.add(temp);
            }
        }
        return returnValue;
    }

    public Long part1() {
        List<List<Integer>> allPermutation = generatePerm(new ArrayList<>(Arrays.asList(0,1,2,3,4)));

        long maxOutput = 0;

        for(List<Integer> onePermut : allPermutation) {
            long input = 0;
            for(int i=0; i<allAmplifiers.length; i++) {
                allAmplifiers[i].incode.input.add((long)onePermut.get(i));
                allAmplifiers[i].incode.input.add(input);
                allAmplifiers[i].incode.compute();
                input = allAmplifiers[i].incode.output.get(0);
                allAmplifiers[i].incode.cleanData();
            }
            if(input > maxOutput) {
                maxOutput = input;
            }
        }
        return maxOutput;
    }

    public Long part2() {

        List<List<Integer>> allPermutation = generatePerm(new ArrayList<>(Arrays.asList(5,6,7,8,9)));

        long maxOutput = 0;

        for(List<Integer> onePermut : allPermutation) {

            for(int i=0; i<allAmplifiers.length; i++) {
                allAmplifiers[i].init((long)onePermut.get(i));
            }

            long input = 0;
            boolean stop = false;
            while(!stop) {
                for(int i=0; i<allAmplifiers.length && !stop; i++) {
                    allAmplifiers[i].incode.input.add(input);
                    allAmplifiers[i].compute();
                    stop = allAmplifiers[i].incode.output.size() == 0;
                    if(!stop) {
                        input = allAmplifiers[i].incode.output.get(0);
                        allAmplifiers[i].incode.output.clear();
                    }
                }
                if(!stop) {
                    if(input > maxOutput) {
                        maxOutput = input;
                    }
                }
            }
        }

        return maxOutput;
    }

}
