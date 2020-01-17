package com.sliard.aoc.days;


import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day14Test {

    @ParameterizedTest
    @CsvSource({
            "/day14-11.txt, 31",
            "/day14-12.txt, 165",
            "/day14-13.txt, 13312",
            "/day14-14.txt, 180697",
            "/day14-15.txt, 2210736"
    })
    void testPart1(String fileName, long result) {
        Day14 day = new Day14();
        day.init(fileName);
        assertEquals(result, day.part1());
    }


}
