package com.sliard.aoc.days;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day10Test {

    @ParameterizedTest
    @CsvSource({
            "/day10-11.txt, 8",
            "/day10-12.txt, 33",
            "/day10-13.txt, 35",
            "/day10-14.txt, 41",
            "/day10-15.txt, 210"
    })
    void testPart1(String fileName, int result) {
        Day10 day = new Day10();
        day.init(fileName);
        assertEquals(result, day.part1());
    }

    @Test
    void testAngle() {
        Point a = new Point(0,0);
        Point b = new Point(10,0);
        Point c = new Point(0,10);
        Point d = new Point(-10,0);
        Point e = new Point(0,-10);
        Point f1 = new Point(10,-10);
        Point f2 = new Point(10,10);


        Day10.Asteroid a1 = new Day10.Asteroid(a,b);
        Day10.Asteroid a2 = new Day10.Asteroid(a,c);
        Day10.Asteroid a3 = new Day10.Asteroid(a,d);
        Day10.Asteroid a4 = new Day10.Asteroid(a,e);
        Day10.Asteroid a5 = new Day10.Asteroid(a,f1);
        Day10.Asteroid a6 = new Day10.Asteroid(a,f2);

        assertEquals(Math.PI/2, a1.angle);
        assertEquals(Math.PI, a2.angle);
        assertEquals(3*Math.PI/2, a3.angle);
        assertEquals(0.0, a4.angle);

        assertEquals(Math.PI/4, a5.angle);
        assertEquals(3*Math.PI/4, a6.angle);

    }


    @ParameterizedTest
    @CsvSource({
            "/day10-21.txt, 210, 802"
    })
    void testPart2(String fileName, int result1, int result2) {
        Day10 day = new Day10();
        day.init(fileName);
        assertEquals(result1, day.part1());
        assertEquals(result2, day.part2());
    }

}
