package com.sliard.aoc.days;

import com.sliard.aoc.Day;
import com.sliard.aoc.utils.ReadTxtFile;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

public class Day12 extends Day<Long> {

    public static void main(String[] args) {
        Day12 d = new Day12();
        d.init("/day12.txt", "1000");
        d.printResult();
    }

    public void init(String ...args) {
        // init stuff
        if(args == null || args.length == 0) {
            println("No args");
            return;
        }
        try {
            List<String> allLines = ReadTxtFile.readFileAsStringList(args[0]);

            allPlanets = new ArrayList<>();
            for(String line : allLines) {
                line = line.substring(1, line.length()-1);
                Planet p = new Planet();
                String[] allVal = line.split(",");
                p.x = Integer.parseInt(allVal[0].split("=")[1]);
                p.y = Integer.parseInt(allVal[1].split("=")[1]);
                p.z = Integer.parseInt(allVal[2].split("=")[1]);
                p.vx = p.vy = p.vz = 0;
                allPlanets.add(p);
            }

            nbTotalStep = Integer.parseInt(args[1]);
        } catch (Exception ex) {
            println("Read file error ("+args[0]+") : "+ex.getMessage());
        }
    }

    List<Planet> allPlanets;

    int nbTotalStep;

    @AllArgsConstructor
    @NoArgsConstructor
    @ToString
    @EqualsAndHashCode
    public class Planet {
        int x;
        int y;
        int z;

        int vx;
        int vy;
        int vz;

        public Planet(Planet p) {
            this.x = p.x;
            this.y = p.y;
            this.z = p.z;
        }
    }

    public void runStep() {
        for(Planet from : allPlanets) {
            for(Planet to : allPlanets) {
                if(from != to) {
                    from.vx += Integer.compare(to.x,from.x);
                    from.vy += Integer.compare(to.y,from.y);
                    from.vz += Integer.compare(to.z,from.z);
                }
            }
        }
        for(Planet from : allPlanets) {
            from.x += from.vx;
            from.y += from.vy;
            from.z += from.vz;
        }
    }

    public Long part1() {

        List<Planet> firstState = new ArrayList<>();
        for (Planet p : allPlanets) {
            Planet f = new Planet(p);
            firstState.add(f);
        }

        for(int i=0; i<nbTotalStep; i++) {
            runStep();
        }

        long result = 0;

        for(Planet from : allPlanets) {
            result += (Math.abs(from.x) + Math.abs(from.y) + Math.abs(from.z)) * (Math.abs(from.vx) + Math.abs(from.vy) + Math.abs(from.vz));
        }

        allPlanets = firstState;

        return result;
    }

    public Long part2() {

        List<Planet> firstState = new ArrayList<>();
        for (Planet p : allPlanets) {
            Planet f = new Planet(p);
            firstState.add(f);
        }

        boolean sameX = false;
        boolean sameY = false;
        boolean sameZ = false;
        int indexX = 0;
        int indexY = 0;
        int indexZ = 0;
        int nbStep = 1;
        while(!(sameX && sameY && sameZ)) {
            runStep();
            if(!sameX) {
                sameX = true;
                for (int i=0; i < allPlanets.size(); i++) {
                    Planet f = firstState.get(i);
                    Planet n = allPlanets.get(i);
                    sameX &= f.x == n.x && f.vx == n.vx;
                }
                if(sameX) {
                    indexX = nbStep;
                }
            }
            if(!sameY) {
                sameY = true;
                for (int i=0; i < allPlanets.size(); i++) {
                    Planet f = firstState.get(i);
                    Planet n = allPlanets.get(i);
                    sameY &= f.y == n.y && f.vy == n.vy;
                }
                if(sameY) {
                    indexY = nbStep;
                }
            }
            if(!sameZ) {
                sameZ = true;
                for (int i=0; i < allPlanets.size(); i++) {
                    Planet f = firstState.get(i);
                    Planet n = allPlanets.get(i);
                    sameZ &= f.z == n.z && f.vz == n.vz;
                }
                if(sameZ) {
                    indexZ = nbStep;
                }
            }
            nbStep++;
        }

        long resulX = indexX;
        long resulY = indexY;
        long resulZ = indexZ;

        boolean allEquals = (resulX == resulZ) && (resulY == resulZ);
        while(!allEquals) {

            while (resulX < resulY || resulX < resulZ) {
                resulX += indexX;
            }
            while (resulY < resulX || resulY < resulZ) {
                resulY += indexY;
            }
            while (resulZ < resulY || resulZ < resulX) {
                resulZ += indexZ;
            }
            allEquals = (resulX == resulZ) && (resulY == resulZ);
        }

        return resulZ;
    }

}
