package com.sliard.aoc.days;


import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day12Test {

    @ParameterizedTest
    @CsvSource({
            "/day12-11.txt, 10, 179",
            "/day12-12.txt, 100, 1940"
    })
    void testPart1(String fileName, String nbStep, long result) {
        Day12 day = new Day12();
        day.init(fileName, nbStep);
        assertEquals(result, day.part1());
    }


    @ParameterizedTest
    @CsvSource({
            "/day12-11.txt, 0, 2772",
            "/day12-21.txt, 0, 4686774924"
    })
    void testPart2(String fileName, String nbStep, long result) {
        Day12 day = new Day12();
        day.init(fileName, nbStep);
        assertEquals(result, day.part2());
    }

}
