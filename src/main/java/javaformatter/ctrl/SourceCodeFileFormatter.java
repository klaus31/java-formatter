package javaformatter.ctrl;

import java.util.List;

public interface SourceCodeFileFormatter {

    List<String> createOutputLines(List<String> input);

}