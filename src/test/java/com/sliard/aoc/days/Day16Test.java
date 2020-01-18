package com.sliard.aoc.days;


import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day16Test {

    @ParameterizedTest
    @CsvSource({
            "80871224585914546619083218645595, 24176176",
            "19617804207202209144916044189917, 73745418",
            "69317163492948606335995924319873, 52432133"
    })
    void testPart1(String input, String result) {
        Day16 day = new Day16();
        day.init(input);
        assertEquals(result, day.part1());
    }


    @ParameterizedTest
    @CsvSource({
            "03036732577212944063491565474664, 84462026",
            "02935109699940807407585447034323, 78725270",
            "03081770884921959731165446850517, 53553731"
    })
    void testPart2(String input, String result) {
        Day16 day = new Day16();
        day.init(input);
        assertEquals(result, day.part2());
    }


}
