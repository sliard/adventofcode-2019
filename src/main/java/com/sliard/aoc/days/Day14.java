package com.sliard.aoc.days;

import com.sliard.aoc.Day;
import com.sliard.aoc.utils.ReadTxtFile;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day14 extends Day<Long> {

    public static void main(String[] args) {
        Day14 d = new Day14();
        d.init("/day14.txt");
        d.printResult();
    }

    public void init(String ...args) {
        // init stuff
        if(args == null || args.length == 0) {
            println("No args");
            return;
        }
        try {
            List<String> allLines = ReadTxtFile.readFileAsStringList(args[0]);

            // 5 VJHF, 7 MNCFX, 9 VPVL, 37 CXFTF => 6 GNMV
            for(String line : allLines) {
                String part[] = line.split("=>");

                String part2[] = part[1].trim().split(" ");
                Element elementTodo = allElementByName.getOrDefault(part2[1].trim(), new Element(part2[1].trim(), Integer.parseInt(part2[0].trim())));
                elementTodo.nbPack = Integer.parseInt(part2[0].trim());
                allElementByName.put(elementTodo.name, elementTodo);

                for(String allNeedElement : part[0].trim().split(",")) {
                    String needPart[] = allNeedElement.trim().split(" ");
                    Element elementNeed = allElementByName.getOrDefault(needPart[1].trim(), new Element(needPart[1].trim(), 0));
                    elementTodo.needs.put(elementNeed, Integer.parseInt(needPart[0].trim()));
                    allElementByName.put(elementNeed.name, elementNeed);

                    int nbNeed = nbLeftUseElement.getOrDefault(elementNeed, 0);
                    nbLeftUseElement.put(elementNeed, nbNeed+1);
                }
            }

        } catch (Exception ex) {
            println("Read file error ("+args[0]+") : "+ex.getMessage());
        }
    }

    Map<String, Element> allElementByName = new HashMap<>();
    Map<Element, Integer> nbLeftUseElement = new HashMap<>();

    @EqualsAndHashCode
    @ToString
    @Data
    public class Element {
        public String name;
        @EqualsAndHashCode.Exclude
        public int nbPack;

        @EqualsAndHashCode.Exclude
        @ToString.Exclude
        public Map<Element, Integer> needs;

        public Element(String name, int nb) {
            this.name = name;
            this.nbPack = nb;
            this.needs = new HashMap<>();
        }

    }

    public Long part1() {
        return countNbelementFor("FUEL", 1);
    }

    public long countNbelementFor(String name, long nb) {
        Element element = allElementByName.get(name);

        Map<Element, Long> needs = new HashMap<>();
        Map<Element, Integer> nbLeftUseElementSave = new HashMap<>(nbLeftUseElement);

        needs.put(element, nb);
        nbLeftUseElementSave.put(element, 0);

        while(true) {
            for(Element currentElement : allElementByName.values()) {
                int nbLeftUse = nbLeftUseElementSave.getOrDefault(currentElement, 0);
                if(nbLeftUse == 0) {
                    long nbNeed = needs.getOrDefault(currentElement,0L);
                    if(currentElement.name.equals("ORE")) {
                        return nbNeed;
                    }
                    // 7 A, 1 E => 1 FUEL
                    long nbToCreate = (nbNeed + currentElement.nbPack - 1) / currentElement.nbPack;

                    for(Element subelement : currentElement.needs.keySet()) {
                        int nbSub = currentElement.needs.get(subelement);
                        long nbNeedSub = needs.getOrDefault(subelement,0L);
                        needs.put(subelement, nbNeedSub + nbSub*nbToCreate);

                        int nbLeftUseSub = nbLeftUseElementSave.get(subelement);
                        nbLeftUseElementSave.put(subelement, nbLeftUseSub-1);
                    }

                    nbLeftUseElementSave.put(currentElement, -1);
                }
            }

        }
    }

    public Long part2() {
        long low = 0;
        long hi = 1000000000000L;
        while (low < hi) {
            long mid = (hi+low+1) / 2;
            long nbOre = countNbelementFor("FUEL", mid);
            if(nbOre <= 1000000000000L) {
                low = mid;
            } else {
                hi = mid - 1;
            }
        }
        return low;
    }

}
