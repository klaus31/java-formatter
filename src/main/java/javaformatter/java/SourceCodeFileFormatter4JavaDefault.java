package javaformatter.java;

import javaformatter.ctrl.SourceCodeFileFormatter;

import java.util.List;

public class SourceCodeFileFormatter4JavaDefault implements SourceCodeFileFormatter {
    @Override
    public List<String> createOutputLines(List<String> input) {
        return input;
    }
}
