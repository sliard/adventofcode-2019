package com.sliard.aoc.days;


import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day07Test {

    @ParameterizedTest
    @CsvSource({
            "/day07-11.txt, 43210",
            "/day07-12.txt, 54321",
            "/day07-13.txt, 65210",
            "/day07-14.txt, 70597"
    })
    void testPart1(String fileName, long result) {
        Day07 day = new Day07();
        day.init(fileName);
        assertEquals(result, day.part1());
    }

    @ParameterizedTest
    @CsvSource({
            "/day07-21.txt, 139629729",
            "/day07-22.txt, 18216",
            "/day07-14.txt, 30872528"
    })
    void testPart2(String fileName, long result) {
        Day07 day = new Day07();
        day.init(fileName);
        assertEquals(result, day.part2());
    }

}
