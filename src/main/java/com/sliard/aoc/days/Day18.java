package com.sliard.aoc.days;

import com.sliard.aoc.Day;
import com.sliard.aoc.common.Direction;
import com.sliard.aoc.utils.CharArrayTools;
import com.sliard.aoc.utils.ReadTxtFile;
import lombok.AllArgsConstructor;
import lombok.ToString;

import java.awt.*;
import java.util.List;
import java.util.*;

public class Day18 extends Day<Long> {

    public static void main(String[] args) {
        Day18 d = new Day18();
        long start = System.currentTimeMillis();
        d.init("/day18.txt");
        d.printResult();
        System.out.println(" - "+(System.currentTimeMillis()-start));
    }


    public void init(String ...args) {
        // init stuff
        if(args == null || args.length == 0) {
            println("No args");
            return;
        }
        try {
            List<String> allLines = ReadTxtFile.readFileAsStringList(args[0]);
            allData = new char[allLines.get(0).length()][allLines.size()];
            for(int y=0; y < allLines.size(); y++) {
                for(int x=0; x < allLines.get(y).length(); x++) {
                    allData[x][y] = allLines.get(y).charAt(x);
                    if(allData[x][y] >= 'a' && allData[x][y] <= 'z') {
                        allKey.put(""+allData[x][y], new Key(""+allData[x][y], new Point(x,y)));
                    }
                }
            }

        } catch (Exception ex) {
            println("Read file error ("+args[0]+") : "+ex.getMessage());
        }
    }

    char allData[][];
    Map<String,Key> allKey = new HashMap<>();

    @ToString
    @AllArgsConstructor
    public class Key {
        String name;
        Point p;
    }

    @ToString
    public class Step {
        Point p;
        int distance;
        String allKeys;

        public Step(Point p, int distance) {
            this.p = new Point(p);
            this.distance = distance;
            allKeys = "";
        }

        public Step(Point p, int distance, String allKeys) {
            this.p = new Point(p);
            this.distance = distance;
            this.allKeys = allKeys;
        }

        public String getKey() {
            StringBuffer result = new StringBuffer();
            return result.append(p.x).append(",").append(p.y).append(",").append(allKeys).toString();
        }

        public boolean haveKey(String keyName) {
            return allKeys.contains(keyName.toLowerCase());
        }

        public void addKey(String keyName) {
            allKeys += keyName;
            allKeys = allKeys.chars().sorted().collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString();
        }
    }

    public Collection<GlobalStep> getNextStep(GlobalStep start) {
        Map<String,GlobalStep> result = new HashMap<>();

        for(int i=0; i<start.robots.length; i++) {

            Step firstStep = new Step(start.robots[i], start.distance, start.allKeys);
            Map<String, Step> visitedStep = new HashMap<>();
            List<Step> allStep = new ArrayList<>();
            allStep.add(firstStep);

            while(allStep.size() > 0) {
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
                    Point np = new Point(currentStep.p.x + d.deltaX, currentStep.p.y + d.deltaY);
                    if(np.x < 0 || np.x >= allData.length || np.y < 0 || np.y >= allData[np.x].length) {
                        continue;
                    }
                    if(allData[np.x][np.y] == '#') {
                        continue;
                    }
                    if((allData[np.x][np.y] >= 'A' && allData[np.x][np.y] <= 'Z') && !currentStep.haveKey(""+allData[np.x][np.y])) {
                        continue;
                    }
                    Step newStep = new Step(np, currentStep.distance+1, currentStep.allKeys);
                    if((allData[np.x][np.y] >= 'a' && allData[np.x][np.y] <= 'z') && !newStep.haveKey(""+allData[np.x][np.y])) {
                        GlobalStep exitStep = result.get(np.x+","+np.y);
                        if(exitStep == null || exitStep.distance > newStep.distance) {
                            exitStep = new GlobalStep(start);
                            exitStep.robots[i] = np;
                            exitStep.addKey(allData[np.x][np.y]+"");
                            exitStep.distance = newStep.distance;
                            result.put(np.x+","+np.y, exitStep);
                        }
                    } else {
                        allStep.add(newStep);
                    }
                }
            }
        }


        return result.values();
    }

    public Long part1() {
        long result = Long.MAX_VALUE;

        Point start = CharArrayTools.findFirstChar(allData, '@');
        Step firstStep = new Step(start, 0);

        Map<String, Step> visitedStep = new HashMap<>();
        List<Step> allStep = new ArrayList<>();
        allStep.add(firstStep);

        while(allStep.size() > 0) {
            Step currentStep = allStep.remove(0);

            Step vStep = visitedStep.get(currentStep.getKey());
            if(vStep == null) {
                visitedStep.put(currentStep.getKey(), currentStep);
            } else if(vStep.distance > currentStep.distance) {
                allStep.remove(vStep);
                visitedStep.put(currentStep.getKey(), currentStep);
            } else {
                continue;
            }

            for(Direction d : Direction.values()) {
                Point np = new Point(currentStep.p.x + d.deltaX, currentStep.p.y + d.deltaY);
                if(np.x < 0 || np.x >= allData.length || np.y < 0 || np.y >= allData[np.x].length) {
                    //out of range
                    continue;
                }
                if(allData[np.x][np.y] == '#') {
                    // wall
                    continue;
                }
                if((allData[np.x][np.y] >= 'A' && allData[np.x][np.y] <= 'Z') && !currentStep.haveKey(""+allData[np.x][np.y])) {
                    // door with no key
                    continue;
                }
                Step newStep = new Step(np, currentStep.distance+1, currentStep.allKeys);
                if((allData[np.x][np.y] >= 'a' && allData[np.x][np.y] <= 'z') && !newStep.haveKey(""+allData[np.x][np.y])) {
                    newStep.addKey(allData[np.x][np.y]+"");
                }
                if(newStep.allKeys.length() == allKey.size()) {
                    if(result > newStep.distance) {
                        result = newStep.distance;
                        System.out.println("res = "+result);

                        // 4796 your answer is too high
                        // 4406 your answer is too high
                        // 4250
                    }
                } else {
                    allStep.add(newStep);
                }
            }


        }
        return result;
    }

    @ToString
    public class GlobalStep {
        Point robots[];
        String allKeys;
        int distance;

        public GlobalStep() {
            robots = new Point[4];
            allKeys = "";
            distance = 0;
        }

        public GlobalStep(GlobalStep gs) {
            allKeys = gs.allKeys;
            distance = gs.distance;
            robots = new Point[gs.robots.length];
            for(int i=0;i<gs.robots.length;i++) {
                robots[i] = new Point(gs.robots[i]);
            }
        }

        public String getKey() {
            StringBuffer result = new StringBuffer();
            for(int i=0; i<robots.length; i++) {
                result.append(robots[i].x);
                result.append(",");
                result.append(robots[i].y);
                result.append(",");
            }
            return result.append(allKeys).toString();
        }

        public boolean haveKey(String keyName) {
            return allKeys.contains(keyName.toLowerCase());
        }

        public void addKey(String keyName) {
            allKeys += keyName;
            allKeys = allKeys.chars().sorted().collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString();
        }
    }

    public Long part2() {
        long result = Long.MAX_VALUE;

        GlobalStep startStep = new GlobalStep();

        Point start = CharArrayTools.findFirstChar(allData, '@');
        startStep.robots[0] = new Point(start.x-1,start.y-1);
        startStep.robots[1] = new Point(start.x+1,start.y-1);
        startStep.robots[2] = new Point(start.x-1,start.y+1);
        startStep.robots[3] = new Point(start.x+1,start.y+1);

        allData[start.x-1][start.y-1] = '@';
        allData[start.x][start.y-1] = '#';
        allData[start.x+1][start.y-1] = '@';

        allData[start.x-1][start.y] = '#';
        allData[start.x][start.y] = '#';
        allData[start.x+1][start.y] = '#';

        allData[start.x-1][start.y+1] = '@';
        allData[start.x][start.y+1] = '#';
        allData[start.x+1][start.y+1] = '@';

        Map<String, GlobalStep> visitedStep = new HashMap<>();
        List<GlobalStep> allStep = new ArrayList<>();
        allStep.add(startStep);

        while(allStep.size() > 0) {
            GlobalStep currentStep = allStep.remove(0);

            GlobalStep vStep = visitedStep.get(currentStep.getKey());
            if(vStep == null) {
                visitedStep.put(currentStep.getKey(), currentStep);
            } else if(vStep.distance > currentStep.distance) {
                allStep.remove(vStep);
                visitedStep.put(currentStep.getKey(), currentStep);
            } else {
                continue;
            }
            Collection<GlobalStep> allNextStep = getNextStep(currentStep);
            for(GlobalStep newStep : allNextStep) {
                if(newStep.allKeys.length() == allKey.size()) {
                    if(result > newStep.distance) {
                        result = newStep.distance;
                        System.out.println("res = "+result);
                    }
                } else {
                    allStep.add(newStep);
                }
            }


        }

        return result;
    }
}
