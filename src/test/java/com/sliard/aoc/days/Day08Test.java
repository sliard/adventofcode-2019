package com.sliard.aoc.days;


import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day08Test {

    @ParameterizedTest
    @CsvSource({
            "/day08-11.txt, 2440"
    })
    void testPart1(String fileName, int result) {
        Day08 day = new Day08();
        day.init(fileName, "25", "6");
        assertEquals(result, day.part1());
    }

}
