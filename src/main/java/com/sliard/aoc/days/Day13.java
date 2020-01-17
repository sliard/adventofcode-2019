package com.sliard.aoc.days;

import com.sliard.aoc.Day;
import com.sliard.aoc.incode.InCode;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day13 extends Day<Long> {

    public static void main(String[] args) {
        Day13 d = new Day13();
        d.init("/day13.txt");
        d.printResult();
    }

    InCode inCode;

    public void init(String ...args) {
        // init stuff
        try {
            inCode = new InCode();
            inCode.initFromFile(args[0]);
            inCode.compute();
            setGrid(inCode.output);
            printGrid();
        } catch (Exception ex) {
            println("Read file error ("+args[0]+") : "+ex.getMessage());
        }
    }

    public enum TileId {
        EMPTY(0), WALL(1), BLOCK(2), PADDLE(3), BALL(4), SCORE(9);

        long val;

        TileId(int val) {
            this.val = val;
        }

        public static TileId getFromVal(long val) {
            for(TileId c : TileId.values()) {
                if(c.val == val) {
                    return c;
                }
            }
            return SCORE;
        }

    }

    Map<Integer, Map<Integer, TileId>> grid = new HashMap<>();

    public TileId getTileIdAt(int x, int y) {
        Map<Integer, TileId> line = grid.getOrDefault(y, new HashMap<>());
        return line.getOrDefault(x, TileId.EMPTY);
    }

    public void setTileIdAt(int x, int y, TileId c) {
        Map<Integer, TileId> line = grid.getOrDefault(y, new HashMap<>());
        line.put(x, c);
        grid.put(y, line);
    }

    public Long part1() {
        return (long)countBock();
    }

    public int countBock() {
        int nbBlock = 0;
        for(int y : grid.keySet()) {
            Map<Integer, TileId> line = grid.get(y);
            for(int x : line.keySet()) {
                nbBlock += getTileIdAt(x,y) == TileId.BLOCK ? 1 : 0;
            }
        }
        return nbBlock;
    }

    public void setGrid(List<Long> data) {
        grid = new HashMap<>();
        for(int i=0; i<data.size(); i+=3) {
            setTileIdAt(data.get(i).intValue(), data.get(i+1).intValue(), TileId.getFromVal(data.get(i+2)));
        }
    }

    public void printGrid() {
        int xMin = 0;
        int xMax = Integer.MIN_VALUE;
        int yMin = 0;
        int yMax = Integer.MIN_VALUE;

        for(int y : grid.keySet()) {
            if(y > yMax) {
                yMax = y;
            }
            Map<Integer, TileId> line = grid.get(y);
            for(int x : line.keySet()) {
                if(x > xMax) {
                    xMax = x;
                }
            }
        }

        for(int y = yMin; y <= yMax; y++) {
            for(int x = xMin; x <= xMax; x++) {
                switch (getTileIdAt(x, y)) {
                    case BALL:
                        System.out.print("o");
                        break;
                    case WALL:
                        System.out.print("#");
                        break;
                    case BLOCK:
                        System.out.print("m");
                        break;
                    case PADDLE:
                        System.out.print("-");
                        break;
                    case EMPTY:
                        System.out.print(" ");
                        break;
                }
            }
            System.out.println();
        }
    }


    public Long part2() {

        int index = 0;

        inCode.cleanData();
        inCode.alldata.put(0,2L);
        index = inCode.compute(index);

        int nbBlock = 1;
        while(nbBlock > 0) {
            int paddleX = 0;
            int ballX = 0;

            for(int i=0; i<inCode.output.size(); i+=3) {
                TileId current = TileId.getFromVal(inCode.output.get(i+2));
                if(current == TileId.BALL) {
                    ballX = inCode.output.get(i).intValue();
                } else if(current == TileId.PADDLE) {
                    paddleX = inCode.output.get(i).intValue();
                }
            }

            if(ballX == paddleX) {
                inCode.input.add(0L);
            } else if(ballX < paddleX) {
                inCode.input.add(-1L);
            } else {
                inCode.input.add(1L);
            }
//            printGrid();

            index = inCode.compute(index);
            setGrid(inCode.output);
            nbBlock = countBock();
        }

        long score = 0;
        for(int i=0; i<inCode.output.size(); i+=3) {
            if(inCode.output.get(i) == -1) {
                score = inCode.output.get(i+2);
            }
        }

        return score;
    }

}
