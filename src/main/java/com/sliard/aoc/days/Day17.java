package com.sliard.aoc.days;

import com.sliard.aoc.Day;
import com.sliard.aoc.common.Direction;
import com.sliard.aoc.incode.InCode;
import com.sliard.aoc.utils.CharArrayTools;
import com.sliard.aoc.utils.ReadTxtFile;
import lombok.AllArgsConstructor;
import lombok.ToString;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Day17 extends Day<Long> {

    public static void main(String[] args) {
        Day17 d = new Day17();
        d.init("/day17.txt");
        d.printResult();
    }

    InCode inCode;

    public void init(String ...args) {
        // init stuff
        try {
            inCode = new InCode();
            inCode.initFromFile(args[0]);
            inCode.compute();
            List<String> allLine = inCode.getAsciiOutput();
            allData = new char[allLine.get(0).length()][allLine.size()-1];


            for(int y=0; y<allLine.size()-1; y++) {
                for(int x=0; x<allLine.get(y).length(); x++) {
                    allData[x][y] = allLine.get(y).charAt(x);
                }
            }

        } catch (Exception ex) {
            println("Read file error ("+args[0]+") : "+ex.getMessage());
        }
    }

    public void initFromFile(String fileName) {
        try {
            List<String> allLine = ReadTxtFile.readFileAsStringList(fileName);
            allData = new char[allLine.get(0).length()][allLine.size()];
            for(int y=0; y<allLine.size(); y++) {
                for(int x=0; x<allLine.get(y).length(); x++) {
                    allData[x][y] = allLine.get(y).charAt(x);
                }
            }

            CharArrayTools.printData(allData);
        } catch (Exception ex) {
            println("Read file error ("+fileName+") : "+ex.getMessage());
        }
    }

    char[][] allData;

    public Long part1() {
        long result = 0;
        for(int x=0; x<allData.length; x++) {
            for(int y=0; y<allData[x].length; y++) {
                if(allData[x][y] != '#') {
                    continue;
                }
                boolean inter = true;
                for(Direction d : Direction.values()) {
                    int nx = x+d.deltaX;
                    int ny = y+d.deltaY;
                    inter &= CharArrayTools.inRange(allData, new Point(nx,ny)) && allData[nx][ny] == '#';
                }
                if(inter) {
                    result += x*y;
                }
            }
        }
        return result;
    }

    public enum Rotation {
        LEFT ("L"), RIGHT("R");
        String val;

        Rotation(String val) {
            this.val = val;
        }

        public String getVal() {
            return val;
        }

        public static Rotation getRotation(Direction lastDirection, Direction newDirection) {
            switch (lastDirection) {
                case NORTH:
                    return newDirection == Direction.EAST ? Rotation.RIGHT : Rotation.LEFT;
                case SOUTH:
                    return newDirection == Direction.EAST ? Rotation.LEFT : Rotation.RIGHT;
                case EAST:
                    return newDirection == Direction.NORTH ? Rotation.LEFT : Rotation.RIGHT;
                case WEST:
                    return newDirection == Direction.NORTH ? Rotation.RIGHT : Rotation.LEFT;
            }
            throw new IllegalArgumentException("Bad direction");
        }

    }

    @ToString
    @AllArgsConstructor
    public class PathElement {
        Rotation r;
        int distance;
    }

    public List<PathElement> getPathElement() {
        List<PathElement> result = new ArrayList<>();
        Point currentPoint = CharArrayTools.findFirstChar(allData, '^');
        Direction direction = Direction.NORTH;
        Direction newDirection = Direction.NORTH;
        int distance = 0;
        Rotation rotation;
        PathElement current = null;

        while(newDirection != null) {
            Point np = new Point(currentPoint.x+direction.deltaX, currentPoint.y+direction.deltaY);
            if(!CharArrayTools.inRange(allData, np) || allData[np.x][np.y] != '#') {
                newDirection = getNextDirection(currentPoint,direction);
                if(newDirection == null) {
                    continue;
                }
                if(newDirection != direction) {
                    if(current != null) {
                        current.distance = distance;
                        result.add(current);
                    }
                    distance = 0;
                    current = new PathElement(Rotation.getRotation(direction, newDirection),distance);
                    direction = newDirection;
                }
            }
            currentPoint.x += direction.deltaX;
            currentPoint.y += direction.deltaY;
            distance++;
        }
        if(current != null) {
            current.distance = distance;
            result.add(current);
        }

        return result;
    }

    public Direction getNextDirection(Point p, Direction lastDirection) {
        for (Direction d : Direction.values()) {
            if(d == lastDirection.opposite()) {
                continue;
            }
            Point np = new Point(p.x+d.deltaX, p.y+d.deltaY);
            if(CharArrayTools.inRange(allData, np) && allData[np.x][np.y] == '#') {
                return d;
            }
        }
        return null;
    }

    public String pathElementString(List<PathElement> elements) {
        List<String> result = new ArrayList<>();
        for(PathElement pe : elements) {
            result.add(pe.r.getVal());
            result.add(""+pe.distance);
        }
        return result.stream().collect(Collectors.joining(","));
    }

    public String getPath() {
        String result = pathElementString(getPathElement());
        return result;
    }

    public Long part2() {
        // L,6,R,8,R,12,L,6,L,8,L,10,L,8,R,12,L,6,R,8,R,12,L,6,L,8,L,8,L,10,L,6,L,6,L,10,L,8,R,12,L,8,L,10,L,6,L,6,
        // L,10,L,8,R,12,L,6,R,8,R,12,L,6,L,8,L,8,L,10,L,6,L,6,L,10,L,8,R,12
        // A,B,A,C,B,C,B,A,C,B
        // L,6,R,8,R,12,L,6,L,8
        // L,10,L,8,R,12
        // L,8,L,10,L,6,L,6

        String pathData = getPath();
        inCode.cleanData();
        inCode.alldata.put(0,2L);
        int index = inCode.compute(0);
        inCode.getAsciiOutput();
        inCode.output.clear();
        inCode.setInput("A,B,A,C,B,C,B,A,C,B");
        index = inCode.compute(index);
        inCode.getAsciiOutput();
        inCode.output.clear();
        inCode.setInput("L,6,R,8,R,12,L,6,L,8");
        index = inCode.compute(index);
        inCode.getAsciiOutput();
        inCode.output.clear();
        inCode.setInput("L,10,L,8,R,12");
        index = inCode.compute(index);
        inCode.getAsciiOutput();
        inCode.output.clear();
        inCode.setInput("L,8,L,10,L,6,L,6");
        index = inCode.compute(index);
        inCode.getAsciiOutput();
        inCode.output.clear();
        inCode.setInput("y");
        index = inCode.compute(index);
        inCode.getAsciiOutput();
        return inCode.getNotAsciiOutput();
    }
}
