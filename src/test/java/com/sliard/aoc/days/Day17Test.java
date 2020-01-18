package com.sliard.aoc.days;


import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day17Test {

    @ParameterizedTest
    @CsvSource({
            "/day17-11.txt, 76"
    })
    void testPart1(String fileName, long result) {
        Day17 day = new Day17();
        day.initFromFile(fileName);
        assertEquals(result, day.part1());
    }


    @ParameterizedTest
    @CsvSource({
            "/day17-21.txt, 'R,8,R,8,R,4,R,4,R,8,L,6,L,2,R,4,R,4,R,8,R,8,R,8,L,6,L,2'"
    })
    void testPart2Path(String fileName, String result) {
        Day17 day = new Day17();
        day.initFromFile(fileName);
        assertEquals(result, day.getPath());
    }

}
