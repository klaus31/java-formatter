package javaformatter.decider.java;

import javaformatter.decider.Decider;
import java.util.List;
import static java.util.stream.Collectors.toList;

abstract class JavaDecider implements Decider {

    public List<String> preProcessLines(List<String> lines) {

        return lines.stream()
        .map(String::trim)
        .map(this::killDoubleSpaces)
        .map(this::putInSingleSpaces)
        .collect(toList());
    }
    abstract String putInSingleSpaces(String line);
    abstract String killDoubleSpaces(String line);
}