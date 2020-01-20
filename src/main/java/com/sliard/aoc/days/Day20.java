package com.sliard.aoc.days;

import com.sliard.aoc.Day;
import com.sliard.aoc.common.Direction;
import com.sliard.aoc.utils.ReadTxtFile;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.awt.*;
import java.util.List;
import java.util.*;

public class Day20 extends Day<Long> {

    public static void main(String[] args) {
        Day20 d = new Day20();
        d.init("/day20.txt");
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
            int maxX = allLines.get(2).length()+2;
            int maxY = allLines.size();
            allData = new char[maxX][maxY];

            for(int y=0; y<allLines.size(); y++) {
                for(int x=0; x<allLines.get(y).length(); x++) {
                    allData[x][y] = allLines.get(y).charAt(x);
                }
            }

            for(int x=0; x<allData.length; x++) {
                for(int y=0; y<allData[x].length; y++) {
                    if(isLetter(x,y)) {
                        if(isLetter(x+1,y)) {
                            String pName = "" + allData[x][y] + allData[x+1][y];
                            Portal p = allPortalByName.getOrDefault(pName, new Portal(pName));
                            if(p.ax == 0 && p.ay == 0) {
                                p.ax = isPath(x-1,y) ? x-1 : x+2;
                                p.ay = y;
                                allPortalByPosition.put(p.ax+","+p.ay, p);
                            } else {
                                p.bx = isPath(x-1,y) ? x-1 : x+2;
                                p.by = y;
                                allPortalByPosition.put(p.bx+","+p.by, p);
                            }
                            allPortalByName.put(p.name,p);
                        } else if(isLetter(x,y+1)) {
                            String pName = "" + allData[x][y] + allData[x][y+1];
                            Portal p = allPortalByName.getOrDefault(pName, new Portal(pName));
                            if(p.ax == 0 && p.ay == 0) {
                                p.ax = x;
                                p.ay = isPath(x,y-1) ? y-1 : y+2;
                                allPortalByPosition.put(p.ax+","+p.ay, p);
                            } else {
                                p.bx = x;
                                p.by = isPath(x,y-1) ? y-1 : y+2;
                                allPortalByPosition.put(p.bx+","+p.by, p);
                            }
                            allPortalByName.put(p.name,p);
                        }
                    }
                }
            }

            for(Portal p : allPortalByName.values()) {
                p.nextA = computePath(p, p.ax, p.ay);
                p.nextB = computePath(p, p.bx, p.by);
            }

            System.out.println();

        } catch (Exception ex) {
            ex.printStackTrace();
            println("Read file error ("+args[0]+") : "+ex.getMessage());
        }
    }

    char allData[][];

    Map<String, Portal> allPortalByName = new HashMap<>();
    Map<String, Portal> allPortalByPosition = new HashMap<>();

    public Collection<Path> computePath(Portal start, int x, int y) {
        Map<Portal, Path> findDestination = new HashMap<>();

        List<Step> allStep = new ArrayList<>();
        Map<String, Step> visitedStep = new HashMap<>();
        Step first = new Step(x,y,1);
        allStep.add(first);

        while (allStep.size() > 0) {
            Step currentStep = allStep.remove(0);

            Step vStep = visitedStep.get(currentStep.getKey());
            if(vStep != null) {
                if(vStep.distance < currentStep.distance) {
                    continue;
                }
                allStep.remove(vStep);
            }
            visitedStep.put(currentStep.getKey(), currentStep);

            for(Direction d : Direction.values()) {
                Point np = new Point(currentStep.x + d.deltaX, currentStep.y + d.deltaY);
                if(!inRange(np.x, np.y)) {
                    continue;
                }
                if(allData[np.x][np.y] != '.') {
                    continue;
                }
                Portal p = allPortalByPosition.get(np.x+","+np.y);
                if(p != null && p != start) {
                    Path alreadyfoundDistance = findDestination.get(p);
                    if(alreadyfoundDistance == null || alreadyfoundDistance.distance > currentStep.distance+1) {
                        Path newPath = new Path();
                        newPath.distance = currentStep.distance+1;
                        newPath.fromX = x;
                        newPath.fromY = y;
                        newPath.toX = np.x;
                        newPath.toY = np.y;
                        newPath.destination = p;
                        newPath.inner = !isOut(np.x, np.y);
                        findDestination.put(p,newPath);
                    }
                } else {
                    allStep.add(new Step(np.x, np.y, currentStep.distance+1));
                }
            }
        }

        return findDestination.values();
    }


    @ToString
    @EqualsAndHashCode
    public class Portal {
        String name;
        @EqualsAndHashCode.Exclude
        int ax;
        @EqualsAndHashCode.Exclude
        int ay;
        @EqualsAndHashCode.Exclude
        int bx;
        @EqualsAndHashCode.Exclude
        int by;

        @EqualsAndHashCode.Exclude
        Collection<Path> nextA = new HashSet<>();
        Collection<Path> nextB = new HashSet<>();

        public Portal(String name) {
            this.name = name;
        }
    }

    @ToString
    @NoArgsConstructor
    public class Path {

        int fromX;
        int fromY;

        int toX;
        int toY;

        @ToString.Exclude
        Portal destination;

        int distance;

        boolean inner;

        int level;

        public Path(Path cpy) {
            this.fromX = cpy.fromX;
            this.fromY = cpy.fromY;
            this.toX = cpy.toX;
            this.toY = cpy.toY;
            this.distance = cpy.distance;
            this.destination = cpy.destination;
            this.inner = cpy.inner;
            this.level = cpy.level;
        }

        public String getKey() {
            return toX+","+toY+","+level;
        }
    }

    @EqualsAndHashCode
    @AllArgsConstructor
    @ToString
    public class Step {
        int x;
        int y;
        @EqualsAndHashCode.Exclude
        int distance;

        public String getKey() {
            return x+","+y;
        }
    }

    public Long part1() {

        long result = Long.MAX_VALUE;

        Portal start = allPortalByName.get("AA");

        List<Path> allPath = new ArrayList<>();
        Map<String, Path> visitedPath = new HashMap<>();

        for(Path p : start.nextA) {
            allPath.add(new Path(p));
        }

        while (allPath.size() > 0) {
            Path currentPath = allPath.remove(0);

            Path vPath = visitedPath.get(currentPath.getKey());
            if(vPath != null) {
                if(vPath.distance <= currentPath.distance) {
                    continue;
                }
                allPath.remove(vPath);
            }
            visitedPath.put(currentPath.getKey(), currentPath);

            if(currentPath.destination.name.equals("ZZ")) {
                if(currentPath.distance < result) {
                    result = currentPath.distance;
                }
                continue;
            }

            Collection<Path> nextPath = (currentPath.toX == currentPath.destination.ax)
                    && (currentPath.toY == currentPath.destination.ay) ? currentPath.destination.nextB : currentPath.destination.nextA;
            for(Path np : nextPath) {
                Path cp = new Path(np);
                cp.distance += currentPath.distance;
                allPath.add(cp);
            }
        }

        return result-1;
    }

    public Long part2() {

        long result = Long.MAX_VALUE;

        int minLevel = 0;
        int maxLevel = 100;
        Portal start = allPortalByName.get("AA");

        List<Path> allPath = new ArrayList<>();
        Map<String, Path> visitedPath = new HashMap<>();

        for(Path p : start.nextA) {
            Path cp = new Path(p);
            cp.level = cp.inner ? 1 : -1;
            allPath.add(cp);
        }

        while (allPath.size() > 0) {
            Path currentPath = allPath.remove(0);

            if(currentPath.level > maxLevel || currentPath.level < minLevel) {
                continue;
            }

            Path vPath = visitedPath.get(currentPath.getKey());
            if(vPath != null) {
                if(vPath.distance <= currentPath.distance) {
                    continue;
                }
                allPath.remove(vPath);
            }
            visitedPath.put(currentPath.getKey(), currentPath);

            Collection<Path> nextPath = (currentPath.toX == currentPath.destination.ax)
                    && (currentPath.toY == currentPath.destination.ay) ? currentPath.destination.nextB : currentPath.destination.nextA;
            for(Path np : nextPath) {
                Path cp = new Path(np);
                cp.distance += currentPath.distance;

                if(cp.destination.name.equals("ZZ") && currentPath.level == 0) {
                    if(cp.distance < result) {
                        result = cp.distance;
                    }
                } else {
                    cp.level = currentPath.level + (cp.inner ? 1 : -1);
                    allPath.add(cp);
                }
            }
        }

        return result-1;
    }


    public boolean inRange(int x, int y) {
        return x >=0 && x < allData.length && y >= 0 && y < allData[x].length;
    }

    public boolean isLetter(int x, int y) {
        return inRange(x,y) && allData[x][y] >= 'A' && allData[x][y] <= 'Z';
    }

    public boolean isPath(int x, int y) {
        return inRange(x,y) && allData[x][y] == '.';
    }

    public boolean isOut(int x, int y) {
        return x < 4 || x > (allData.length - 4) || y < 4 || y > (allData[0].length - 4);
    }
}
