package com.sliard.aoc.days;


import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day06Test {

    @ParameterizedTest
    @CsvSource({
            "/day06-11.txt, 42",
            "/day06-12.txt, 142497"
    })
    void testPart1(String fileName, int result) {
        Day06 day = new Day06();
        day.init(fileName);
        assertEquals(result, day.part1());
    }

    @ParameterizedTest
    @CsvSource({
            "/day06-21.txt, 4",
            "/day06-12.txt, 301"
    })
    void testPart2(String fileName, int result) {
        Day06 day = new Day06();
        day.init(fileName);
        assertEquals(result, day.part2());
    }

}
