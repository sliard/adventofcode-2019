package com.sliard.aoc.days;


import com.sliard.aoc.incode.InCode;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day02Test {

    @ParameterizedTest
    @CsvSource({
            "/day02-11.txt, '2,0,0,0,99'",
            "/day02-12.txt, '2,3,0,6,99'",
            "/day02-13.txt, '2,4,4,5,99,9801'",
            "/day02-14.txt, '30,1,1,4,2,5,6,0,99'"
    })
    void testPart1(String fileName, String result) throws IOException {
        InCode inCode = new InCode();
        inCode.initFromFile(fileName);
        inCode.compute();
        String afterCompute = Arrays.stream(inCode.alldata).mapToObj(String::valueOf).collect(Collectors.joining(","));
        assertEquals(result, afterCompute);
    }

}
