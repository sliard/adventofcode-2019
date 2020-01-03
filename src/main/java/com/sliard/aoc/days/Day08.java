package com.sliard.aoc.days;

import com.sliard.aoc.Day;
import com.sliard.aoc.utils.ReadTxtFile;

import java.util.ArrayList;
import java.util.List;

public class Day08 extends Day<Integer> {

    public static void main(String[] args) {
        Day08 d = new Day08();
        d.init("/day08.txt", "25", "6");
        d.printResult();
    }

    List<Layer> allLayers;
    int width;
    int height;

    public void init(String ...args) {
        // init stuff
        if(args == null || args.length == 0) {
            println("No args");
            return;
        }
        try {
            List<String> allLines = ReadTxtFile.readFileAsStringList(args[0]);
            width = Integer.parseInt(args[1]);
            height = Integer.parseInt(args[2]);

            allLayers = new ArrayList<>();
            int index = 0;
            while(index < allLines.get(0).length()) {
                Layer l = new Layer();
                l.data = new int[width][height];
                for(int h = 0; h < height; h++) {
                    for(int w = 0; w < width; w++) {
                        char c = allLines.get(0).charAt(index);
                        l.data[w][h] = Integer.parseInt(""+c);
                        index++;
                    }
                }
                allLayers.add(l);
            }

            System.out.print("");

        } catch (Exception ex) {
            println("Read file error ("+args[0]+") : "+ex.getMessage());
        }
    }

    public class Layer {
        public int[][] data;
    }

    public Integer part1() {

        int layerMinIndex = 0;
        int nbMinZero = Integer.MAX_VALUE;
        for(int i = 0; i < allLayers.size(); i++) {
            Layer currentLayer = allLayers.get(i);
            int nbZero = 0;
            for(int x = 0; x < currentLayer.data.length; x++) {
                for(int y = 0; y < currentLayer.data[x].length; y++) {
                    nbZero += currentLayer.data[x][y] == 0 ? 1 : 0;
                }
            }
            if(nbZero < nbMinZero) {
                nbMinZero = nbZero;
                layerMinIndex = i;
            }
        }

        Layer currentLayer = allLayers.get(layerMinIndex);
        int nb1 = 0;
        int nb2 = 0;
        for(int x = 0; x < currentLayer.data.length; x++) {
            for(int y = 0; y < currentLayer.data[x].length; y++) {
                nb1 += currentLayer.data[x][y] == 1 ? 1 : 0;
                nb2 += currentLayer.data[x][y] == 2 ? 1 : 0;
            }
        }

        return nb1 * nb2;
    }

    public Integer part2() {

        int[][] finalData = allLayers.get(0).data;

        for(int i = 1; i < allLayers.size(); i++) {
            Layer currentLayer = allLayers.get(i);
            for(int x = 0; x < currentLayer.data.length; x++) {
                for(int y = 0; y < currentLayer.data[x].length; y++) {
                    if(finalData[x][y] == 2) {
                        finalData[x][y] = currentLayer.data[x][y];
                    }
                }
            }
        }

        System.out.println("");
        for(int y = 0; y < finalData[0].length; y++) {
            for(int x = 0; x < finalData.length; x++) {
                System.out.print(finalData[x][y] == 1 ? '#' : ' ');
            }
            System.out.println("");
        }

        return 0;
    }

}
