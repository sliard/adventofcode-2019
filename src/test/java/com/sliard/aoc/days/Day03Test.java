package com.sliard.aoc.days;


import com.sliard.aoc.incode.InCode;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day03Test {

    @ParameterizedTest
    @CsvSource({
            "/day03-11.txt, 6",
            "/day03-12.txt, 159",
            "/day03-13.txt, 135",
            "/day03-14.txt, 1626"
    })
    void testPart1(String fileName, long result) throws IOException {
        Day03 day = new Day03();
        day.init(fileName);
        assertEquals(result, day.part1());
    }

    @ParameterizedTest
    @CsvSource({
            "/day03-21.txt, 30",
            "/day03-22.txt, 610",
            "/day03-23.txt, 410",
            "/day03-24.txt, 27330"
    })
    void testPart2(String fileName, long result) throws IOException {
        Day03 day = new Day03();
        day.init(fileName);
        assertEquals(result, day.part2());
    }

}
