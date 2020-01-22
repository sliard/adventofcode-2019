package com.sliard.aoc.days;

import com.sliard.aoc.Day;
import com.sliard.aoc.utils.ReadTxtFile;
import lombok.AllArgsConstructor;
import lombok.ToString;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Day22 extends Day<String> {

    public static void main(String[] args) {
        Day22 d = new Day22();
        d.init("/day22.txt", "10007");
        d.printResult();

        // 253 your answer is too low
    }

    public void init(String ...args) {
        // init stuff
        if(args == null || args.length == 0) {
            println("No args");
            return;
        }
        try {
            List<String> allLines = ReadTxtFile.readFileAsStringList(args[0]);
            allMove = new Move[allLines.size()];
            for(int i=0; i<allLines.size(); i++) {
                allMove[i] = new Move(allLines.get(i));
            }

            nbCard = Integer.parseInt(args[1]);


            allData = new int[nbCard];
            for(int i=0; i<nbCard; i++) {
                allData[i] = i;
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            println("Read file error ("+args[0]+") : "+ex.getMessage());
        }
    }

    int nbCard = 0;
    Move allMove[];
    int allData[];

    @AllArgsConstructor
    public enum Action {
        DEAL_WITH_INCREMENT("deal with increment "),
        DEAL_NEW_STACK("deal into new stack"),
        CUT("cut ");

        String name;

        public static Action actionByText(String text) {
            return Arrays.stream(values()).filter(a -> text.startsWith(a.name)).findAny().get();
        }

        public String getName() {
            return name;
        }
    }

    @AllArgsConstructor
    @ToString
    public class Move {
        Action a;
        int value;

        public Move(String data) {
            this.a = Action.actionByText(data);
            if(this.a == Action.CUT || this.a == Action.DEAL_WITH_INCREMENT) {
                this.value = Integer.valueOf(data.substring(this.a.getName().length()));
            }
        }
    }

    public int[] computeOneAction(Move move) {
        int result[] = new int[nbCard];

        switch (move.a) {
            case DEAL_NEW_STACK:
                for(int i=0; i<nbCard;i++) {
                    result[i] = allData[nbCard-1-i];
                }
                break;
            case CUT:
                int delta = move.value > 0 ? move.value : nbCard + move.value;
                for(int i=0; i<nbCard; i++) {
                    result[i] = allData[(i+delta)%nbCard];
                }
                break;
            case DEAL_WITH_INCREMENT:
                for(int i=0; i<nbCard; i++) {
                    result[(i*move.value)%nbCard] = allData[i];
                }
                break;
        }
        return result;
    }

    public int[] computeResult() {
        for(Move m : allMove) {
            this.allData = computeOneAction(m);
        }
        return allData;
    }

    public String part1() {
        int result[] = computeResult();
        if(result.length < 2020) {
            return "-1";
        }
        for(int i=0; i<nbCard; i++) {
            if(result[i] == 2019) {
                return ""+i;
            }
        }

        return "-1";
    }

    public String resultAsString() {
        int result[] = computeResult();
        return Arrays.stream(result).mapToObj(String::valueOf).collect(Collectors.joining(" "));
    }

    public String part2() {
        return seekPosition(num(119315717514047L), num(101741582076661L), 2020).toString();
    }

    private BigInteger seekPosition(BigInteger deckSize, BigInteger timesShuffled, int position) {
        var calc = new BigInteger[] {num(1), num(0)};
        for(Move move : reverseArray(allMove)) {
            switch(move.a) {
                case DEAL_NEW_STACK: {
                    calc[0] = calc[0].multiply(num(-1));
                    calc[1] = calc[1].add(num(1)).multiply(num(-1));
                } break;
                case CUT: calc[1] = calc[1].add(num(move.value)); break;
                case DEAL_WITH_INCREMENT: {
                    var p = num(move.value).modPow(deckSize.subtract(num(2)), deckSize);
                    for(int i = 0; i<calc.length; i++) calc[i] = calc[i].multiply(p);
                } break;
            }
            for(int i = 0; i<calc.length; i++) {
                calc[i] = calc[i].mod(deckSize);
            }
        }
        var pow = calc[0].modPow(timesShuffled, deckSize);
        return pow.multiply(num(position)).add(calc[1].multiply(pow.add(deckSize).subtract(num(1))).multiply(calc[0].subtract(num(1)).modPow(deckSize.subtract(num(2)), deckSize))).mod(deckSize);
    }

    private BigInteger num(long n) {
        return new BigInteger(Long.toString(n));
    }

    private <T> T[] reverseArray(T[] arr) {
        for(int i = 0; i < arr.length / 2; i++) {
            T temp = arr[i];
            arr[i] = arr[arr.length - i - 1];
            arr[arr.length - i - 1] = temp;
        }
        return arr;
    }
}
