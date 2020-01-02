package com.sliard.aoc.days;


import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day04Test {

    @ParameterizedTest
    @CsvSource({
            "108457,562041, 2779"
    })
    void testPart1(String start, String end, int result) {
        Day04 day = new Day04();
        day.init(start, end);
        assertEquals(result, day.part1());
    }

    @ParameterizedTest
    @CsvSource({
            "111111, true",
            "223450, false",
            "123789, false"
    })
    void testRules1(int data, boolean result) {
        Day04 day = new Day04();
        assertEquals(result, day.rulesPart1(data));
    }

    @ParameterizedTest
    @CsvSource({
            "112233, true",
            "123444, false",
            "111122, true"
    })
    void testRules2(int data, boolean result) {
        Day04 day = new Day04();
        assertEquals(result, day.rulesPart2(data));
    }



    @ParameterizedTest
    @CsvSource({
            "108457,562041, 1972",
    })
    void testPart2(String start, String end, int result) throws IOException {
        Day04 day = new Day04();
        day.init(start, end);
        assertEquals(result, day.part2());
    }

}
