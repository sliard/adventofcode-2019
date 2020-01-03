package com.sliard.aoc.incode;

import com.sliard.aoc.utils.ReadTxtFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class InCode {

    public Map<Integer,Long> alldata;
    public String initData;

    public List<Long> input;
    public List<Long> output;

    public int offset;

    public void initFromFile(String fileName) throws IOException {
        List<String> allLines = ReadTxtFile.readFileAsStringList(fileName);
        initFromString(allLines.get(0));
    }

    public void initFromString(String allDataAsString) {
        this.initData = allDataAsString;
        String[] stringData = allDataAsString.split(",");
        alldata = new HashMap<>();
        for(int i = 0; i < stringData.length; i++) {
            alldata.put(i,Long.parseLong(stringData[i]));
        }
        input = new ArrayList<>();
        output = new ArrayList<>();
        offset = 0;
    }

    public void cleanData() {
        initFromString(this.initData);
    }

    public int compute() {
        return compute(0);
    }

    public int compute(int index) {

        while(true) {

            Instruction instruction = new Instruction(alldata.get(index));
            switch (instruction.op) {
                case ADD:
                    long val1 = getValue(index+1,instruction.modes[0]) + getValue(index+2,instruction.modes[1]);
                    setValue(index+3, instruction.modes[2], val1);
                    index += instruction.op.nbElem;
                    break;
                case MUL:
                    long val2 = getValue(index+1,instruction.modes[0]) * getValue(index+2,instruction.modes[1]);
                    setValue(index+3, instruction.modes[2], val2);
                    index += instruction.op.nbElem;
                    break;
                case INPUT:
                    if(input.size() == 0) {
                        return index;
                    }
                    long val3 = input.remove(0);
                    setValue(index+1, instruction.modes[0], val3);
                    index += instruction.op.nbElem;
                    break;
                case OUTPUT:
                    output.add(getValue(index+1,instruction.modes[0]));
                    index += instruction.op.nbElem;
                    break;
                case JUMP_IF_TRUE:
                    if(getValue(index+1,instruction.modes[0]) != 0) {
                        index = (int)getValue(index+2,instruction.modes[1]);
                    } else {
                        index += instruction.op.nbElem;
                    }
                    break;
                case JUMP_IF_FALSE:
                    if(getValue(index+1,instruction.modes[0]) == 0) {
                        index = (int)getValue(index+2,instruction.modes[1]);
                    } else {
                        index += instruction.op.nbElem;
                    }
                    break;
                case LESS_THAN:
                    if(getValue(index+1,instruction.modes[0]) < getValue(index+2,instruction.modes[1])) {
                        setValue(index+3, instruction.modes[2], 1L);
                    } else {
                        setValue(index+3, instruction.modes[2], 0L);
                    }
                    index += instruction.op.nbElem;
                    break;
                case EQUALS:
                    if(getValue(index+1,instruction.modes[0]) == getValue(index+2,instruction.modes[1])) {
                        setValue(index+3, instruction.modes[2], 1L);
                    } else {
                        setValue(index+3, instruction.modes[2], 0L);
                    }
                    index += instruction.op.nbElem;
                    break;
                case OFFSET:
                    offset += getValue(index+1,instruction.modes[0]);
                    index += instruction.op.nbElem;
                    break;
                case END:
                    return index;
            }

        }

    }

    public long getValue(int index, ParameterMode mode) {
        switch (mode) {
            case POSITION:
                return alldata.getOrDefault(alldata.getOrDefault(index,0L).intValue(),0L);
            case IMMEDIATE:
                return alldata.getOrDefault(index,0L);
            case RELATIVE:
                return alldata.getOrDefault(alldata.getOrDefault(index,0L).intValue()+offset,0L);
            default:
                throw new IllegalArgumentException("Bad mode : "+mode);
        }
    }

    public void setValue(int index, ParameterMode mode, long value) {
        switch (mode) {
            case POSITION:
                Long existVal = alldata.get(index);
                if(existVal == null) {
                    throw new IllegalArgumentException("Bad index : "+index);
                }
                alldata.put(existVal.intValue(), value);
                break;
            case RELATIVE:
                Long existValR = alldata.get(index);
                if(existValR == null) {
                    throw new IllegalArgumentException("Bad index : "+index);
                }
                alldata.put(existValR.intValue()+offset, value);
                break;
            default:
                throw new IllegalArgumentException("Bad mode : "+mode);
        }
    }

    public String getAllOutputAsString() {
        return output.stream().map(e -> ""+e).collect(Collectors.joining(","));
    }
    public String getAllDataAsString() {
        return alldata.values().stream().map(e -> ""+e).collect(Collectors.joining(","));
    }

}
