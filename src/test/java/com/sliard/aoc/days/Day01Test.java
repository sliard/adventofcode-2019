package com.sliard.aoc.days;


import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day01Test {

    @ParameterizedTest
    @CsvSource({
            "/day01-01.txt, 2",
            "/day01-02.txt, 2",
            "/day01-03.txt, 654",
            "/day01-04.txt, 33583"
    })
    void testPart1(String fileName, int result) {
        Day01 day = new Day01();
        day.init(fileName);
        assertEquals(result, day.part1());
    }

    @ParameterizedTest
    @CsvSource({
            "/day01-21.txt, 2",
            "/day01-22.txt, 966",
            "/day01-23.txt, 50346"
    })
    void testPart2(String fileName, int result) {
        Day01 day = new Day01();
        day.init(fileName);
        assertEquals(result, day.part2());
    }

}
