package javaformatter;

import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

class JavaSourceFormatter {

    private final JavaSource javaSource;
    private final SourceConfig sourceConfig;

    JavaSourceFormatter(JavaSource javaSource, SourceConfig sourceConfig) {
        this.javaSource = javaSource;
        this.sourceConfig = sourceConfig;
    }

    void format() throws IOException {
        List<String> lines = javaSource.readContentLines().stream()
                .map(String::trim)
                .filter(str -> !str.isEmpty())
                .collect(Collectors.toList());
        lines = addBlankLines(lines);
        lines = addTabs(lines);
        javaSource.setFormattedLines(lines);
    }

    private List<String> addTabs(List<String> lines) {
        List<String> resultLines = new ArrayList<>();
        int tabLevel = 0;
        for(String line : lines) {
            tabLevel += sourceConfig.tabChangeThisLine(line);
            resultLines.add(StringUtils.repeat(sourceConfig.getTab(), tabLevel) + line);
            tabLevel += sourceConfig.tabChangeNextLine(line);
        }
        return resultLines;
    }

    private List<String> addBlankLines(List<String> lines) {
        List<String> resultLines = new ArrayList<>();
        for(int i=0; i< lines.size(); i++) {
            String line = lines.get(i);
            if(line.isEmpty()) continue;
            // add blank lines
            String prevLine = i == 0 ? null : lines.get(i-1);
            int blankLinesBefore = sourceConfig.blankLinesBefore(line, prevLine);
            int bi = 0;
            while(bi++ < blankLinesBefore) resultLines.add("");
            resultLines.add(line);
            // add blank lines
            int blankLinesAfter = sourceConfig.blankLinesAfter(line);
            int ai = 0;
            while(ai++ < blankLinesAfter) resultLines.add("");
        }
        return resultLines;
    }

    void withSource(Consumer<String> consumer) {
        consumer.accept(javaSource.getFormattedLines().stream().collect(Collectors.joining(sourceConfig.getEol())));
    }
}
