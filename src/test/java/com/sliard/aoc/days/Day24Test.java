package com.sliard.aoc.days;


import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day24Test {

    @ParameterizedTest
    @CsvSource({
            "/day24-11.txt, 2129920"
    })
    void testPart1(String fileName, long result) {
        Day24 day = new Day24();
        day.init(fileName, "200");
        assertEquals(result, day.biodiversity());
    }

    @ParameterizedTest
    @CsvSource({
            "/day24-21.txt, 10, 99"
    })
    void testPart2(String fileName, String nbLoop, long result) {
        Day24 day = new Day24();
        day.init(fileName, nbLoop);
        assertEquals(result, day.part2());
    }

}
