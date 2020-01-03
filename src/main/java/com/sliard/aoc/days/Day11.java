package com.sliard.aoc.days;

import com.sliard.aoc.Day;
import com.sliard.aoc.common.Direction;
import com.sliard.aoc.incode.InCode;

import java.util.HashMap;
import java.util.Map;

public class Day11 extends Day<Integer> {

    public static void main(String[] args) {
        Day11 d = new Day11();
        d.init("/day11.txt");
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

    public enum Color {
        WHITE(1), BLACK(0);

        long val;

        Color(long val) {
            this.val = val;
        }

        public static Color getFromVal(long val) {
            for(Color c : Color.values()) {
                if(c.val == val) {
                    return c;
                }
            }
            return BLACK;
        }

    }


    public enum Turn {
        LEFT(0), RIGHT(1);

        long val;

        Turn(long val) {
            this.val = val;
        }
        public static Turn getFromVal(long val) {
            for(Turn c : Turn.values()) {
                if(c.val == val) {
                    return c;
                }
            }
            return LEFT;
        }
    }

    Map<Integer, Map<Integer, Color>> grid = new HashMap<>();

    public Color getColorAt(int x, int y) {
        Map<Integer, Color> line = grid.getOrDefault(y, new HashMap<>());
        return line.getOrDefault(x, Color.BLACK);
    }

    public void setColorAt(int x, int y, Color c) {
        Map<Integer, Color> line = grid.getOrDefault(y, new HashMap<>());
        line.put(x, c);
        grid.put(y, line);
    }

    public Integer part1() {
        grid = new HashMap<>();
        setColorAt(0,0,Color.BLACK);
        inCode.cleanData();
        run();
        return getNbPaint();
    }

    public void run() {

        int index = 0;
        int x = 0;
        int y = 0;
        Direction currentDirection = Direction.NORTH;

        while(true) {
            inCode.input.add(getColorAt(x,y).val);
            index = inCode.compute(index);
            if(inCode.output.size() == 0) {
                return;
            }
            setColorAt(x,y,Color.getFromVal(inCode.output.get(0)));
            Turn t = Turn.getFromVal(inCode.output.get(1));
            inCode.output.clear();
            switch (currentDirection) {
                case NORTH:
                    currentDirection = t == Turn.LEFT ? Direction.WEST : Direction.EAST;
                    break;
                case EAST:
                    currentDirection = t == Turn.LEFT ? Direction.NORTH : Direction.SOUTH;
                    break;
                case SOUTH:
                    currentDirection = t == Turn.LEFT ? Direction.EAST : Direction.WEST;
                    break;
                case WEST:
                    currentDirection = t == Turn.LEFT ? Direction.SOUTH : Direction.NORTH;
                    break;
            }
            x += currentDirection.deltaX;
            y += currentDirection.deltaY;
        }
    }

    public void printGrid() {
        int xMin = Integer.MAX_VALUE;
        int xMax = Integer.MIN_VALUE;
        int yMin = Integer.MAX_VALUE;
        int yMax = Integer.MIN_VALUE;

        for(int y : grid.keySet()) {
            if(y > yMax) {
                yMax = y;
            }
            if(y < yMin) {
                yMin = y;
            }
            Map<Integer, Color> line = grid.get(y);
            for(int x : line.keySet()) {
                if(x > xMax) {
                    xMax = x;
                }
                if(x < xMin) {
                    xMin = x;
                }
            }
        }

        for(int y = yMin; y <= yMax; y++) {
            for(int x = xMin; x <= xMax; x++) {
                System.out.print(getColorAt(x, y) == Color.WHITE ? '#' : ' ');
            }
            System.out.println("");
        }
    }


    public int getNbPaint() {
        int result = 0;

        for(int y : grid.keySet()) {
            Map<Integer, Color> line = grid.get(y);
            for(int x : line.keySet()) {
                result++;
            }
        }
        return result;
    }


    public Integer part2() {
        grid = new HashMap<>();
        setColorAt(0,0,Color.WHITE);
        inCode.cleanData();
        run();
        printGrid();
        return 1;
    }

}
