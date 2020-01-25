package com.sliard.aoc.days;

import com.sliard.aoc.Day;
import com.sliard.aoc.incode.InCode;
import lombok.AllArgsConstructor;
import lombok.ToString;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day23 extends Day<Long> {

    public static void main(String[] args) {
        Day23 d = new Day23();
        d.init("/day23.txt");
        d.printResult();
    }

    public void init(String ...args) {
        // init stuff
        try {
            for(int i=0; i< 50; i++) {
                allComputer.put(i, new Computer(args[0], i));
            }
        } catch (Exception ex) {
            println("Read file error ("+args[0]+") : "+ex.getMessage());
        }
    }

    public Map<Integer, Computer> allComputer = new HashMap<>();

    @ToString
    public class Computer {
        InCode inCode;
        int index;
        int address;

        public Computer(String fileName, int address) throws IOException {
            this.address = address;
            inCode = new InCode();
            inCode.initFromFile(fileName);
            inCode.input.add((long)address);
            index = inCode.compute(0);
        }

        public void cleanData() {
            inCode.cleanData();
            inCode.input.add((long)address);
            index = inCode.compute(0);
        }

        public void addData(Packet p) {
            inCode.input.add(p.x);
            inCode.input.add(p.y);
        }

        public List<Packet> compute() {
            List<Packet> result = new ArrayList<>();
            if(inCode.input.size() == 0) {
                inCode.input.add(-1L);
            }
            index = inCode.compute(index);
            for(int i=0; i<inCode.output.size(); i+=3) {
                result.add(new Packet(inCode.output.get(i).intValue(),inCode.output.get(i+1),inCode.output.get(i+2)));
            }
            inCode.output.clear();
            return result;
        }


    }

    @ToString
    @AllArgsConstructor
    public class Packet {
        int address;
        long x;
        long y;
    }

    public Long part1() {
        while(true) {
            for(Computer c : allComputer.values()) {
                List<Packet> next = c.compute();
                for(Packet p : next) {
                    if(p.address == 255) {
                        return p.y;
                    } else {
                        allComputer.get(p.address).addData(p);
                    }
                }
            }
        }
    }

    public Long part2() {

        for(int i=0; i< 50; i++) {
            allComputer.get(i).cleanData();
        }

        Packet lastNatPacket = null;
        long lastIdleYValue = Long.MAX_VALUE;

        while(true) {
            boolean idle = true;
            for(Computer c : allComputer.values()) {
                List<Packet> next = c.compute();
                for(Packet p : next) {
                    if(p.address == 255) {
                        lastNatPacket = p;
                    } else {
                        allComputer.get(p.address).addData(p);
                    }
                    idle = false;
                }
            }

            if(idle) {
                allComputer.get(0).addData(lastNatPacket);
                if(lastNatPacket.y == lastIdleYValue) {
                    return lastIdleYValue;
                }
                lastIdleYValue = lastNatPacket.y;
            }
        }
    }

}
