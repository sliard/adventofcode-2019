package com.sliard.aoc.days;


import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day22Test {

    @ParameterizedTest
    @CsvSource({
            "/day22-11.txt, '0 3 6 9 2 5 8 1 4 7'",
            "/day22-12.txt, '3 0 7 4 1 8 5 2 9 6'",
            "/day22-13.txt, '6 3 0 7 4 1 8 5 2 9'",
            "/day22-14.txt, '9 2 5 8 1 4 7 0 3 6'",
            "/day22-15.txt, '6 7 8 9 0 1 2 3 4 5'"
    })
    void testPart1(String fileName, String result) {
        Day22 day = new Day22();
        day.init(fileName,"10");
        assertEquals(result, day.resultAsString());
    }

}
