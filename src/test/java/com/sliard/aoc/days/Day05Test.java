package com.sliard.aoc.days;


import com.sliard.aoc.incode.InCode;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day05Test {

    @ParameterizedTest
    @CsvSource({
            "'3,9,8,9,10,9,4,9,99,-1,8', 8, 1",
            "'3,9,8,9,10,9,4,9,99,-1,8', 1, 0",
            "'3,9,8,9,10,9,4,9,99,-1,8', 9, 0"
    })
    void testEquals(String data, long input, long output) throws IOException {
        InCode inCode = new InCode();
        inCode.initFromString(data);
        inCode.input.add(input);
        inCode.compute();
        assertEquals(output, inCode.output.get(0));
    }

    @ParameterizedTest
    @CsvSource({
            "'3,9,7,9,10,9,4,9,99,-1,8', 8, 0",
            "'3,9,7,9,10,9,4,9,99,-1,8', 1, 1",
            "'3,9,7,9,10,9,4,9,99,-1,8', 9, 0"
    })
    void testLessThan(String data, long input, long output) throws IOException {
        InCode inCode = new InCode();
        inCode.initFromString(data);
        inCode.input.add(input);
        inCode.compute();
        assertEquals(output, inCode.output.get(0));
    }


    @ParameterizedTest
    @CsvSource({
            "'3,3,1108,-1,8,3,4,3,99', 8, 1",
            "'3,3,1108,-1,8,3,4,3,99', 1, 0",
            "'3,3,1108,-1,8,3,4,3,99', 9, 0"
    })
    void testEqualsImmediate(String data, long input, long output) throws IOException {
        InCode inCode = new InCode();
        inCode.initFromString(data);
        inCode.input.add(input);
        inCode.compute();
        assertEquals(output, inCode.output.get(0));
    }

    @ParameterizedTest
    @CsvSource({
            "'3,3,1107,-1,8,3,4,3,99', 8, 0",
            "'3,3,1107,-1,8,3,4,3,99', 1, 1",
            "'3,3,1107,-1,8,3,4,3,99', 9, 0"
    })
    void testLessThanImmediate(String data, long input, long output) throws IOException {
        InCode inCode = new InCode();
        inCode.initFromString(data);
        inCode.input.add(input);
        inCode.compute();
        assertEquals(output, inCode.output.get(0));
    }

    @ParameterizedTest
    @CsvSource({
            "'3,12,6,12,15,1,13,14,13,4,13,99,-1,0,1,9', 0, 0",
            "'3,12,6,12,15,1,13,14,13,4,13,99,-1,0,1,9', 10, 1",
            "'3,12,6,12,15,1,13,14,13,4,13,99,-1,0,1,9', 10, 1",
            "'3,12,6,12,15,1,13,14,13,4,13,99,-1,0,1,9', 9, 1"
    })
    void testJump(String data, long input, long output) throws IOException {
        InCode inCode = new InCode();
        inCode.initFromString(data);
        inCode.input.add(input);
        inCode.compute();
        assertEquals(output, inCode.output.get(0));
    }

    @ParameterizedTest
    @CsvSource({
            "'3,3,1105,-1,9,1101,0,0,12,4,12,99,1', 0, 0",
            "'3,3,1105,-1,9,1101,0,0,12,4,12,99,1', 10, 1",
            "'3,3,1105,-1,9,1101,0,0,12,4,12,99,1', 10, 1",
            "'3,3,1105,-1,9,1101,0,0,12,4,12,99,1', 9, 1"
    })
    void testJumpImmediate(String data, long input, long output) throws IOException {
        InCode inCode = new InCode();
        inCode.initFromString(data);
        inCode.input.add(input);
        inCode.compute();
        assertEquals(output, inCode.output.get(0));
    }

}
