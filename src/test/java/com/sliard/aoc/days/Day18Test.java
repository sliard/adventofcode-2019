package com.sliard.aoc.days;


import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day18Test {

    @ParameterizedTest
    @CsvSource({
            "/day18-11.txt, 8",
            "/day18-12.txt, 86",
            "/day18-13.txt, 132",
            "/day18-14.txt, 136",
            "/day18-15.txt, 81"
    })
    void testPart1(String fileName, long result) {
        Day18 day = new Day18();
        day.init(fileName);
        assertEquals(result, day.part1());
    }


    @ParameterizedTest
    @CsvSource({
            "/day18-21.txt, 8",
            "/day18-22.txt, 24",
            "/day18-23.txt, 32",
            "/day18-24.txt, 72"
    })
    void testPart2(String fileName, long result) {
        Day18 day = new Day18();
        day.init(fileName);
        assertEquals(result, day.part2());
    }


}
