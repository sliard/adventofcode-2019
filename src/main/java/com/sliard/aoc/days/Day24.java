package com.sliard.aoc.days;

import com.sliard.aoc.Day;
import com.sliard.aoc.common.Direction;
import com.sliard.aoc.utils.ReadTxtFile;

import java.util.*;

public class Day24 extends Day<Long> {

    public static void main(String[] args) {
        Day24 d = new Day24();
        d.init("/day24.txt", "200");
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
            allData = new char[size][size];
            for(int y=0; y<allLines.size(); y++) {
                for(int x=0; x<allLines.get(y).length(); x++) {
                    allData[x][y] = allLines.get(y).charAt(x);
                }
            }
            nbLoop = Integer.parseInt(args[1]);
        } catch (Exception ex) {
            ex.printStackTrace();
            println("Read file error ("+args[0]+") : "+ex.getMessage());
        }
    }

    int nbLoop = 200;
    int size = 5;
    char allData[][];

    public void life() {
        char newData[][] = new char[size][size];
        for(int x=0; x<size; x++) {
            for(int y=0; y<size; y++) {
                int nbBug = countBugArround(x,y);
                if(allData[x][y] == '#' && (nbBug != 1)) {
                    newData[x][y] = '.';
                } else if (allData[x][y] == '.' && (nbBug == 1 || nbBug == 2)) {
                    newData[x][y] = '#';
                } else {
                    newData[x][y] = allData[x][y];
                }
            }
        }
        for(int x=0; x<size; x++) {
            for (int y = 0; y < size; y++) {
                allData[x][y] = newData[x][y];
            }
        }
    }

    public int countBugArround(int x, int y) {
        int result = 0;
        for(Direction d : Direction.values()) {
            int nx = x + d.deltaX;
            int ny = y + d.deltaY;
            result += (nx<0 || nx>=size || ny<0 || ny>=size || allData[nx][ny] == '.') ? 0 : 1;
        }
        return result;
    }


    public void printData() {
        for(int y=0; y<size; y++) {
            StringBuffer sb = new StringBuffer();
            for(int x=0; x<size; x++) {
                sb.append(allData[x][y]);
            }
            System.out.println(sb.toString());
        }
    }

    public Long part1() {

        char saveData[][] = new char[size][size];
        for(int x=0; x<size; x++) {
            for (int y = 0; y < size; y++) {
                saveData[x][y] = allData[x][y];
            }
        }

        Set<Long> allBio = new HashSet<>();
        long bio = biodiversity();
        allBio.add(bio);
        boolean find = false;
        while(!find) {
            life();
            bio = biodiversity();
            if(allBio.contains(bio)) {
                find = true;
            }
            allBio.add(bio);
        }

        for(int x=0; x<size; x++) {
            for (int y = 0; y < size; y++) {
                allData[x][y] = saveData[x][y];
            }
        }
        return bio;
    }

    public long biodiversity() {
        long result = 0;

        for(int y=0; y<size; y++) {
            for(int x=0; x<size; x++) {
                if(allData[x][y] == '#') {
                    result += Math.pow(2,(x+size*y));
                }
            }
        }
        return result;
    }

    Map<Integer, char[][]> layers = new HashMap<>();

    public Long part2() {
        for(int i=-nbLoop; i<=nbLoop; i++) {
            layers.put(i, initData());
        }
        layers.put(0, allData);

        for(int n=0; n<nbLoop; n++) {
            Map<Integer, char[][]> newLayers = new HashMap<>();
            for(int i=-nbLoop; i<=nbLoop; i++) {
                char layer[][] = lifeLayer(layers.get(i), i);
                newLayers.put(i,layer);
            }
            layers = newLayers;
        }

        long result = 0L;

        for(int i=-nbLoop; i<=nbLoop; i++) {
            char data[][] = layers.get(i);
            for(int x=0; x<size; x++) {
                for (int y = 0; y < size; y++) {
                    if(!(x==2 && y==2)) {
                        result += data[x][y] == '#' ? 1 : 0;
                    }
                }
            }
        }

        // 674400 your answer is too high

        return result;
    }


    public char[][] lifeLayer(char data[][], int level) {
        char newData[][] = new char[size][size];
        for(int x=0; x<size; x++) {
            for(int y=0; y<size; y++) {
                int nbBug = countBugArroundLayer(data,x,y,level);
                if(data[x][y] == '#' && (nbBug != 1)) {
                    newData[x][y] = '.';
                } else if (data[x][y] == '.' && (nbBug == 1 || nbBug == 2)) {
                    newData[x][y] = '#';
                } else {
                    newData[x][y] = data[x][y];
                }
            }
        }
        return newData;
    }


    public int countBugArroundLayer(char data[][], int x, int y, int level) {
        int result = 0;
        for(Direction d : Direction.values()) {
            int nx = x + d.deltaX;
            int ny = y + d.deltaY;
            if(nx == 2 && ny == 2) {
                continue;
            }
            result += (nx<0 || nx>=size || ny<0 || ny>=size || data[nx][ny] == '.') ? 0 : 1;
        }

        char dataOut[][] = layers.getOrDefault(level+1, initData());
        char dataIn[][] = layers.getOrDefault(level-1, initData());

        if(x == 0) {
            result += dataOut[1][2] == '#' ? 1 : 0;
        }
        if(y == 0) {
            result += dataOut[2][1] == '#' ? 1 : 0;
        }
        if(x == size-1) {
            result += dataOut[3][2] == '#' ? 1 : 0;
        }
        if(y == size-1) {
            result += dataOut[2][3] == '#' ? 1 : 0;
        }

        if(x == 2 && y == 1) {
            for(int a=0; a<size; a++) {
                result += dataIn[a][0] == '#' ? 1 : 0;
            }
        } else if(x == 3 && y == 2) {
            for(int a=0; a<size; a++) {
                result += dataIn[size-1][a] == '#' ? 1 : 0;
            }
        } else if(x == 2 && y == 3) {
            for(int a=0; a<size; a++) {
                result += dataIn[a][size-1] == '#' ? 1 : 0;
            }
        } else if(x == 1 && y == 2) {
            for(int a=0; a<size; a++) {
                result += dataIn[0][a] == '#' ? 1 : 0;
            }
        }

        return result;
    }

    public char[][] initData() {
        char result[][] = new char[size][size];
        for(int y=0; y<size; y++) {
            for(int x=0; x<size; x++) {
                result[x][y] = '.';
            }
        }
        return result;
    }
}
