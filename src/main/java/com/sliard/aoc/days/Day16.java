package com.sliard.aoc.days;

import com.sliard.aoc.Day;

public class Day16 extends Day<String> {

    public static void main(String[] args) {
        Day16 d = new Day16();
        d.init("59704176224151213770484189932636989396016853707543672704688031159981571127975101449262562108536062222616286393177775420275833561490214618092338108958319534766917790598728831388012618201701341130599267905059417956666371111749252733037090364984971914108277005170417001289652084308389839318318592713462923155468396822247189750655575623017333088246364350280299985979331660143758996484413769438651303748536351772868104792161361952505811489060546839032499706132682563962136170941039904873411038529684473891392104152677551989278815089949043159200373061921992851799948057507078358356630228490883482290389217471790233756775862302710944760078623023456856105493");
        d.printResult();
    }

    public void init(String ...args) {
        // init stuff
        if(args == null || args.length == 0) {
            println("No args");
            return;
        }
        input = args[0];
    }

    String input = "";

    int pattern[] = new int[] {0, 1, 0, -1};

    public String part1() {
        int inputVal[] = new int[input.length()];
        for(int i=0; i<input.length(); i++) {
            inputVal[i] = Integer.parseInt(input.substring(i,i+1));
        }
        for(int i=0; i<100; i++) {
            inputVal = computePhase(inputVal);
        }
        StringBuffer result = new StringBuffer();
        for(int i=0; i<8; i++) {
            result.append(inputVal[i]);
        }
        return result.toString();
    }

    private int[] computePhase(int[] phaseInput) {
        int[] result = new int[phaseInput.length];

        for(int i=0; i<phaseInput.length; i++) {
            int val = 0;
            int index = 0;
            int patternIndex = 0;
            // 0 1 0 -1 0 1 0 -1
            // 0 0 1 1 0 0 -1 -1
            // 0 0 0 1 1 1 0  0
            while(index < phaseInput.length+1) {
                for(int a=0; a<i+1 && index < (phaseInput.length+1); a++) {
                    if(index > 0) {
                        val += phaseInput[index-1] * pattern[patternIndex%pattern.length];
                    }
                    index++;
                }
                patternIndex++;
            }
            result[i] = Math.abs(val % 10);
        }
        return result;
    }

    public String part2() {

        // 1  0 -1  0
        // 0  1  1  0
        // 0  0  1  1
        // 0  0  0  1

        // 1  0 -1  0  1
        // 0  1  1  0  0
        // 0  0  1  1  1
        // 0  0  0  1  1
        // 0  0  0  0  1

        // 1  0 -1  0  1  0
        // 0  1  1  0  0 -1
        // 0  0  1  1  1  0
        // 0  0  0  1  1  1
        // 0  0  0  0  1  1
        // 0  0  0  0  0  1

        int mid = (input.length()*10000)/2;
        int offeset = Integer.parseInt(input.substring(0,7)) - mid;

        int inputVal[] = new int[mid];
        for(int i=0; i<inputVal.length; i++) {
            inputVal[i] = Integer.parseInt(input.substring(((i+mid)%input.length()),((i+mid)%input.length())+1));
        }

        for(int i=0; i<100; i++) {
            inputVal = computePhaseMid(inputVal);
        }

        StringBuffer result = new StringBuffer();
        for(int i=offeset; i<offeset+8; i++) {
            result.append(inputVal[i]);
        }
        return result.toString();

    }


    private int[] computePhaseMid(int[] phaseInput) {
        int[] result = new int[phaseInput.length];

        // 0  0  0  1  1  1
        // 0  0  0  0  1  1
        // 0  0  0  0  0  1
        result[phaseInput.length - 1] = phaseInput[phaseInput.length - 1];
        for(int i=phaseInput.length-2; i>0; i--) {

            int r = result[i+1];
            int p = phaseInput[i];

            result[i] = Math.abs((r+p) % 10);
        }
        return result;
    }
}
