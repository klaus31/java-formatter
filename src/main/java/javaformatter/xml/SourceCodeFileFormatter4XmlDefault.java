package javaformatter.xml;

import javaformatter.ctrl.SourceCodeFileFormatter;

import java.util.List;

public class SourceCodeFileFormatter4XmlDefault implements SourceCodeFileFormatter {
    @Override
    public List<String> createOutputLines(List<String> input) {
        return input;
    }
}
