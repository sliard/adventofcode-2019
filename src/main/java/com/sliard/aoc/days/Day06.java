package com.sliard.aoc.days;

import com.sliard.aoc.Day;
import com.sliard.aoc.utils.ReadTxtFile;
import lombok.AllArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day06 extends Day<Integer> {

    public static void main(String[] args) {
        Day06 d = new Day06();
        d.init("/day06.txt");
        d.printResult();
    }

    @AllArgsConstructor
    @ToString
    public class Planet {
        String name;
        int distance;
        @ToString.Exclude
        List<Planet> orbits;
        @ToString.Exclude
        Planet parent;
    }

    Map<String, Planet> allPlanets;

    public void init(String ...args) {
        // init stuff
        try {
            List<String> allLines = ReadTxtFile.readFileAsStringList(args[0]);

            allPlanets = new HashMap<>();
            for(String line : allLines) {
                String[] infos = line.split("\\)");
                Planet from = allPlanets.get(infos[0]);
                Planet to = allPlanets.get(infos[1]);
                if(to == null) {
                    to = new Planet(infos[1],0, new ArrayList<>(), null);
                }
                if(from == null) {
                    from = new Planet(infos[0],0, new ArrayList<>(), null);
                }
                from.orbits.add(to);
                to.parent = from;
                allPlanets.put(from.name, from);
                allPlanets.put(to.name, to);
            }

            Planet planetCOM = allPlanets.get("COM");
            computeDistance(planetCOM,0);

        } catch (Exception ex) {
            println("Read file error ("+args[0]+") : "+ex.getMessage());
        }
    }

    public void computeDistance(Planet p, int distance) {
        p.distance = distance;
        for(Planet o : p.orbits) {
            computeDistance(o, distance+1);
        }
    }

    public Integer part1() {

        int result = 0;
        for(Planet p : allPlanets.values()) {
            result += p.distance;
        }

        return result;
    }

    public Integer part2() {

        Planet planetYOU = allPlanets.get("YOU");
        Planet planetSAN = allPlanets.get("SAN");

        Planet p1 = planetYOU;
        Planet p2 = planetSAN;

        while (p1 != p2) {
            if(p1.distance > p2.distance) {
                p1 = p1.parent;
            } else {
                p2 = p2.parent;
            }
        }

        return (planetYOU.distance - p1.distance) + (planetSAN.distance - p1.distance) - 2;
    }

}
