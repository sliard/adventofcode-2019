package com.sliard.aoc.days;

import com.sliard.aoc.Day;
import com.sliard.aoc.utils.ReadTxtFile;
import lombok.ToString;

import java.awt.*;
import java.util.List;
import java.util.*;

public class Day10 extends Day<Integer> {

    public static void main(String[] args) {
        Day10 d = new Day10();
        d.init("/day10.txt");
        d.printResult();
    }

    List<Point> allStars;
    Point goodStation = new Point();

    public void init(String ...args) {
        // init stuff
        if(args == null || args.length == 0) {
            println("No args");
            return;
        }
        try {
            List<String> allLines = ReadTxtFile.readFileAsStringList(args[0]);
            allStars = new ArrayList<>();

            int y = 0;
            for(String line : allLines) {
                for(int x = 0; x < line.length(); x++) {
                    if(line.charAt(x) == '#') {
                        allStars.add(new Point(x,y));
                    }
                }
                y++;
            }

        } catch (Exception ex) {
            println("Read file error ("+args[0]+") : "+ex.getMessage());
        }
    }

    @ToString
    public static class Asteroid implements Comparable<Asteroid> {
        public Point p;
        public double angle;
        public int distance;
        public boolean destroy;

        public Asteroid(Point station, Point star) {
            this.distance = Math.abs(station.x - star.x) + Math.abs(station.y - star.y);
            this.p = new Point(star);
            destroy = false;

            if(station.x == star.x) {
                this.angle = station.y < star.y ? Math.PI : 0;
            } else if(station.y == star.y) {
                this.angle = station.x < star.x ? Math.PI/2 : 3*Math.PI/2;
            } else {
                this.angle = Math.atan(Math.abs(station.x - star.x) / (double)Math.abs(station.y - star.y));
                if((station.x < star.x) && (station.y < star.y)) {
                    this.angle = (Math.PI/2 - this.angle) + Math.PI/2;
                } else if((station.x > star.x) && (station.y < star.y)) {
                    this.angle = (this.angle) + Math.PI;
                } else if((station.x > star.x) && (station.y > star.y)) {
                    this.angle = (Math.PI/2 - this.angle) + 3*Math.PI/2;
                }
            }

        }

        @Override
        public int compareTo(Asteroid otherStar) {
            if(this.angle == otherStar.angle) {
                return Integer.compare(this.distance, otherStar.distance);
            }
            return Double.compare(this.angle, otherStar.angle);
        }
    }

    public Integer part1() {

        int nbMaxStarVisible = 0;
        goodStation = new Point();

        for(Point station : allStars) {
            Map<Double, Asteroid> asteroidByAngle = new HashMap<>();
            for(Point star : allStars) {
                if(!station.equals(star)) {
                    Asteroid a = new Asteroid(station, star);
                    asteroidByAngle.put(a.angle,a);
                }
            }

            if(nbMaxStarVisible < asteroidByAngle.size()) {
                nbMaxStarVisible = asteroidByAngle.size();
                goodStation = new Point(station);
            }
        }

        System.out.println();

        return nbMaxStarVisible;
    }

    public Integer part2() {

        SortedSet<Asteroid> allAsteroid = new TreeSet<>();
        for(Point star : allStars) {
            if(!goodStation.equals(star)) {
                Asteroid a = new Asteroid(goodStation, star);
                allAsteroid.add(a);
            }
        }

        int i=0;
        while(i<200) {
            double lastAngle = -1;
            for(Asteroid a : allAsteroid) {
                if(a.angle != lastAngle && !a.destroy) {
                    a.destroy = true;
                    lastAngle = a.angle;
                    i++;
                }
                if(i==200) {
                    return a.p.x * 100 + a.p.y;
                }
            }
        }

        return 0;
    }

}
