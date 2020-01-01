package com.sliard.aoc.days;

import com.sliard.aoc.Day;
import com.sliard.aoc.common.Direction;
import com.sliard.aoc.incode.InCode;
import com.sliard.aoc.utils.ReadTxtFile;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.xml.xpath.XPath;
import java.awt.*;
import java.util.*;
import java.util.List;

public class Day03 extends Day<Long> {

    public static void main(String[] args) {
        Day03 d = new Day03();
        d.init("/day03.txt");
        d.printResult();

    }

    @Data
    public class Path {
        public Direction d;
        public int distance;

        public void initFromData(String data) {
            d = Direction.fromVal(data);
            distance = Integer.parseInt(data.substring(1));
        }
    }

    public class Step {
        public Point p;
        public boolean cross = false;
        public int distance;

        public Step() {

        }

        public String getKey() {
            return p.x+","+p.y;
        }

        public Step(int x, int y) {
            p = new Point(x,y);
        }
    }

    List<Path> wire1;
    List<Path> wire2;

    public void init(String ...args) {
        // init stuff
        try {
            List<String> allLines = ReadTxtFile.readFileAsStringList(args[0]);
            wire1 = initWire(allLines.get(0));
            wire2 = initWire(allLines.get(1));
        } catch (Exception ex) {
            println("Read file error ("+args[0]+") : "+ex.getMessage());
        }
    }

    public Long part1() {

        int x = 0;
        int y = 0;
        int distance = 0;
        Map<String, Step> allStep = new HashMap<>();
        for(Path path : wire1) {
            for(int i = 0; i<path.distance; i++) {
                x += path.d.deltaX;
                y += path.d.deltaY;
                Step s = new Step(x, y);
                s.distance = distance+1;
                distance++;
                allStep.put(s.getKey(), s);
            }
        }

        x=0;
        y=0;

        int minDistance = Integer.MAX_VALUE;

        for(int pi = 0; pi < wire2.size(); pi++) {
            Path path = wire2.get(pi);
            for(int i = 0; i<path.distance; i++) {
                x += path.d.deltaX;
                y += path.d.deltaY;
                Step s = new Step(x, y);
                Step fs = allStep.get(s.getKey());
                if(fs != null) {
                    if(distance(fs.p) < minDistance) {
                        minDistance = distance(fs.p);
                    }
                }
            }
        }

        return (long)minDistance;
    }

    public Long part2() {
        int x = 0;
        int y = 0;
        int distance = 0;
        Map<String, Step> allStep = new HashMap<>();
        for(Path path : wire1) {
            for(int i = 0; i<path.distance; i++) {
                x += path.d.deltaX;
                y += path.d.deltaY;
                Step s = new Step(x, y);
                s.distance = distance+1;
                distance++;
                allStep.put(s.getKey(), s);
            }
        }

        x=0;
        y=0;
        distance=0;

        Map<String, Step> allCrossStep = new HashMap<>();

        for(int pi = 0; pi < wire2.size(); pi++) {
            Path path = wire2.get(pi);
            for(int i = 0; i<path.distance; i++) {
                x += path.d.deltaX;
                y += path.d.deltaY;
                Step s = new Step(x, y);
                Step fs = allStep.get(s.getKey());
                if(fs != null) {
                    Step ns = new Step();
                    ns.p = new Point(fs.p);
                    ns.cross = true;
                    ns.distance = fs.distance + distance + 1;
                    allCrossStep.put(ns.getKey(), ns);
                }
                distance++;
            }
        }

        int minDistance = Integer.MAX_VALUE;
        for(Step s : allCrossStep.values()) {
            if(s.distance < minDistance) {
                minDistance = s.distance;
            }
        }

        return (long)minDistance;
    }

    private List<Path> initWire(String data) {
        List<Path> result = new ArrayList<>();

        for(String oneData : data.split(",")) {
            Path p = new Path();
            p.initFromData(oneData);
            result.add(p);
        }

        return result;
    }


    private int distance(Point p) {
        return Math.abs(p.x) + Math.abs(p.y);
    }

}
