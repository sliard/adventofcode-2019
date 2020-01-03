package com.sliard.aoc.days;


import com.sliard.aoc.incode.InCode;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day09Test {

    @ParameterizedTest
    @CsvSource({
            "'109,1,204,-1,1001,100,1,100,1008,100,16,101,1006,101,0,99', '109,1,204,-1,1001,100,1,100,1008,100,16,101,1006,101,0,99'",
            "'104,1125899906842624,99', '1125899906842624'",
            "'1102,34915192,34915192,7,4,7,99,0', '1219070632396864'"
    })
    void testEquals(String data, String output) throws IOException {
        InCode inCode = new InCode();
        inCode.initFromString(data);
        inCode.compute();
        assertEquals(output, inCode.getAllOutputAsString());
    }

    @ParameterizedTest
    @CsvSource({
            "'/day09.txt', 1, 3063082071",
            "'/day09.txt', 2, 81348",
    })
    void testEquals(String fileName, long input, long output) throws IOException {
        InCode inCode = new InCode();
        inCode.initFromFile(fileName);
        inCode.input.add(input);
        inCode.compute();
        assertEquals(output, inCode.output.get(0));
    }
}
