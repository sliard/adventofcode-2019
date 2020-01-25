package com.sliard.aoc.days;

import com.google.common.collect.Sets;
import com.sliard.aoc.Day;
import com.sliard.aoc.incode.InCode;

import java.util.List;
import java.util.Set;

public class Day25 extends Day<Long> {

    public static void main(String[] args) {
        Day25 d = new Day25();
        d.init("/day25.txt");
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

        inCode.setInput("south");
        inCode.setInput("take spool of cat6");
        inCode.setInput("west");
        inCode.setInput("take space heater");
        inCode.setInput("south");
        inCode.setInput("take shell");
        inCode.setInput("north");
        inCode.setInput("north");
        inCode.setInput("take weather machine");
        inCode.setInput("north");
        inCode.setInput("west");
        inCode.setInput("west");
        inCode.setInput("take whirled peas");
        inCode.setInput("east");
        inCode.setInput("east");
        inCode.setInput("south");
        inCode.setInput("west");
        inCode.setInput("south");
        inCode.setInput("south");
        inCode.setInput("take space law space brochure");
        inCode.setInput("north");
        inCode.setInput("east");
        inCode.setInput("take candy cane");
        inCode.setInput("west");
        inCode.setInput("north");
        inCode.setInput("east");
        inCode.setInput("south");
        inCode.setInput("east");
        inCode.setInput("east");
        inCode.setInput("south");
        inCode.setInput("take hypercube");
        inCode.setInput("south");
        inCode.setInput("south");

        Set<String> allObject = Sets.newHashSet("spool of cat6", "space heater",
                "shell", "weather machine", "whirled peas", "space law space brochure",
                "candy cane", "hypercube");

        for(String o : allObject) {
            inCode.setInput("drop "+ o);
        }

        Set<Set<String>> combinations = Sets.combinations(allObject,4);

        int index = 0;

        index = inCode.compute(index);
        inCode.output.clear();
        for(Set<String> combi : combinations) {
            index = testOne(index, combi);
        }

        return 0L;
    }

    public int testOne(int index, Set<String> objects) {
        int result = index;
        for(String o : objects) {
            inCode.setInput("take "+o);
        }
        inCode.setInput("east");
        result = inCode.compute(result);

        List<String> allOut = inCode.getAsciiOutput();
        boolean bad = false;
        for(String line : allOut) {
            if(line.contains("heavier")) {
                bad = true;
            } else if(line.contains("lighter")) {
                bad = true;
            }
        }
        if(!bad) {
            inCode.getAsciiOutput(true);
        } else {
            for(String o : objects) {
                inCode.setInput("drop "+ o);
            }
        }
        inCode.output.clear();
        return result;
    }

    public Long part2() {
        return 0L;
    }
/*

Arcade(whirled peas)   -   Fountain(!!!escape pod)  -    Science Lab                      *
                                                             |                            |
                Navigation(!!giant electromagnet)     -  Hallway(weather machine)   *- Hull Breach
                              |                              |                            |
                              |                   Kitchen(space heater)   -   Passages (spool of cat6)    -       Storage(!!!molten lava)
                              |                              |                                                              |
                              |                           Corridor(shell)                                            Stables(hypercube)
                              |                                                                                             |
                              |                                                                                      Observatory
                     Sick Bay(!!photons)         -  Gift Wrapping Center(candy cane)                                        |
                              |                                                                                    Security Checkpoint   -  !!!
                *- Warp Drive Maintenance(space law space brochure)
 */

}
