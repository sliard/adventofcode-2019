package com.sliard.aoc.days;

import com.sliard.aoc.Day;
import com.sliard.aoc.common.Direction;
import com.sliard.aoc.incode.InCode;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Day15 extends Day<Long> {

    public static void main(String[] args) {
        Day15 d = new Day15();
        d.init("/day15.txt");
        d.printResult();
    }

    InCode inCode;

    public void init(String ...args) {
        // init stuff
        try {
            inCode = new InCode();
            inCode.initFromFile(args[0]);
            readGrid();
            printGrid();
        } catch (Exception ex) {
            println("Read file error ("+args[0]+") : "+ex.getMessage());
        }
    }

    private Map<String, Integer> nbStepPerPosition = new HashMap<>();

    public void readGrid() {

        Direction currentDirection = Direction.NORTH;

        int x = 0;
        int y = 0;
        setStatusAt(x,y, StatusCode.EMPTY);
        int index = 0;

        int nbLoop = 0;
        while(++nbLoop < 8000) {
            int nb = nbStepPerPosition.getOrDefault(x+","+y,0);
            nbStepPerPosition.put(x+","+y, nb+1);

            inCode.input.add((long)currentDirection.getIntVal());
            index = inCode.compute(index);
            StatusCode status = StatusCode.getFromVal(inCode.output.get(0));
            inCode.output.clear();
            switch (status) {
                case EMPTY:
                    x += currentDirection.deltaX;
                    y += currentDirection.deltaY;
                    setStatusAt(x,y, StatusCode.EMPTY);
                    break;
                case WALL:
                    setStatusAt(x+currentDirection.deltaX,y+currentDirection.deltaY, StatusCode.WALL);
                    break;
                case OXYGEN:
                    x += currentDirection.deltaX;
                    y += currentDirection.deltaY;
                    ox = x;
                    oy = y;
                    setStatusAt(x,y, StatusCode.OXYGEN);
            }
            currentDirection = getNewDirection(x,y);
        }

        System.out.println();
    }

    private Direction getNewDirection(int x, int y) {

        int nbMin = Integer.MAX_VALUE;
        Direction result = Direction.NORTH;

        for(Direction d : Direction.values()) {
            int nx = x+d.deltaX;
            int ny = y+d.deltaY;
            StatusCode status = getStatusAt(nx, ny);
            if(status == StatusCode.WALL) {
                continue;
            }
            if(status == StatusCode.NOVISIT) {
                return d;
            }
            if(nx > 50 || nx < -50 || ny > 50 || ny < -50) {
                continue;
            }
            int nb = nbStepPerPosition.getOrDefault(nx+","+ny,0);
            if (nb < nbMin) {
                nbMin = nb;
                result = d;
            }
        }

        return result;
    }

    public enum StatusCode {
        WALL(0), EMPTY(1), OXYGEN(2), NOVISIT(3);

        long val;

        StatusCode(int val) {
            this.val = val;
        }

        public static StatusCode getFromVal(long val) {
            for(StatusCode c : StatusCode.values()) {
                if(c.val == val) {
                    return c;
                }
            }
            return NOVISIT;
        }

    }

    Map<Integer, Map<Integer, StatusCode>> grid = new HashMap<>();
    int ox = 0;
    int oy = 0;

    public StatusCode getStatusAt(int x, int y) {
        Map<Integer, StatusCode> line = grid.getOrDefault(y, new HashMap<>());
        return line.getOrDefault(x, StatusCode.NOVISIT);
    }

    public void setStatusAt(int x, int y, StatusCode c) {
        Map<Integer, StatusCode> line = grid.getOrDefault(y, new HashMap<>());
        line.put(x, c);
        grid.put(y, line);
    }

    @ToString
    @AllArgsConstructor
    @EqualsAndHashCode
    public class State {
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

        LinkedList<State> allState = new LinkedList<>();
        Map<String, Integer> visitedState = new HashMap<>();

        State first = new State(0,0,0);
        allState.add(first);

        while (allState.size() > 0) {
            State currentState = allState.pollFirst();

            if(visitedState.containsKey(currentState.getKey())) {
                int d = visitedState.get(currentState.getKey());
                if(d <= currentState.distance) {
                    continue;
                }
            }
            visitedState.put(currentState.getKey(), currentState.distance);

            for(Direction d : Direction.values()) {
                int nx = currentState.x + d.deltaX;
                int ny = currentState.y + d.deltaY;
                StatusCode status = getStatusAt(nx, ny);
                if(status == StatusCode.WALL) {
                    continue;
                }
                if(status == StatusCode.OXYGEN) {
                    if(result > currentState.distance+1) {
                        result = currentState.distance+1;
                    }
                    continue;
                }
                State nState = new State(nx, ny, currentState.distance+1);
                allState.addFirst(nState);
            }

        }


        return result;
    }

    public void setGrid(List<Long> data) {
        grid = new HashMap<>();
        for(int i=0; i<data.size(); i+=3) {
            setStatusAt(data.get(i).intValue(), data.get(i+1).intValue(), StatusCode.getFromVal(data.get(i+2)));
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
            Map<Integer, StatusCode> line = grid.get(y);
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
                switch (getStatusAt(x, y)) {
                    case OXYGEN:
                        System.out.print("o");
                        break;
                    case WALL:
                        System.out.print("#");
                        break;
                    case EMPTY:
                        if(x == 0 && y == 0) {
                            System.out.print("^");
                        } else {
                            System.out.print(".");
                        }
                        break;
                    case NOVISIT:
                        System.out.print(" ");
                        break;
                }
            }
            System.out.println();
        }
    }


    public Long part2() {


        long result = 0;

        LinkedList<State> allState = new LinkedList<>();
        Map<String, Integer> visitedState = new HashMap<>();

        State first = new State(ox,oy,0);
        allState.add(first);

        while (allState.size() > 0) {
            State currentState = allState.pollFirst();

            if(visitedState.containsKey(currentState.getKey())) {
                int d = visitedState.get(currentState.getKey());
                if(d <= currentState.distance) {
                    continue;
                }
            }
            visitedState.put(currentState.getKey(), currentState.distance);
            if(result < currentState.distance) {
                result = currentState.distance;
            }

            for(Direction d : Direction.values()) {
                int nx = currentState.x + d.deltaX;
                int ny = currentState.y + d.deltaY;
                StatusCode status = getStatusAt(nx, ny);
                if(status == StatusCode.WALL) {
                    continue;
                }
                State nState = new State(nx, ny, currentState.distance+1);
                allState.addFirst(nState);
            }
        }

        return result;
    }

}
