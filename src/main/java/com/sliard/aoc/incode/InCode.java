package com.sliard.aoc.incode;

import com.sliard.aoc.utils.ReadTxtFile;

import java.io.IOException;
import java.util.List;

public class InCode {

    public long[] alldata;
    public String initData;

    public void initFromFile(String fileName) throws IOException {
        List<String> allLines = ReadTxtFile.readFileAsStringList(fileName);
        initFromString(allLines.get(0));
    }

    public void initFromString(String allDataAsString) {
        this.initData = allDataAsString;
        String[] stringData = allDataAsString.split(",");
        alldata = new long[stringData.length];
        for(int i = 0; i < stringData.length; i++) {
            alldata[i] = Long.parseLong(stringData[i]);
        }
    }

    public void cleanData() {
        initFromString(this.initData);
    }

    public void compute() {

        for(int i=0; i < alldata.length; i+=4) {

            switch (Operation.getFromVal(alldata[i])) {
                case ADD:
                    alldata[(int)alldata[i+3]] = alldata[(int)alldata[i+1]] + alldata[(int)alldata[i+2]];
                    break;
                case MUL:
                    alldata[(int)alldata[i+3]] = alldata[(int)alldata[i+1]] * alldata[(int)alldata[i+2]];
                    break;
                case END:
                    return;
            }

        }

    }
}
