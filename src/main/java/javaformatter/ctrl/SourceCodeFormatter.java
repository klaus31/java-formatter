package javaformatter.ctrl;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import javaformatter.decider.Decider;
import javaformatter.decider.DeciderSimpleFactory;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

public class SourceCodeFormatter {

    private final SourceCodeFile sourceCodeFile;

    private final Decider decider;

    private SourceCodeFormatter(SourceCodeFile sourceCodeFile) {
        this.sourceCodeFile = sourceCodeFile;
        this.decider = DeciderSimpleFactory.create(sourceCodeFile.getSuffix());
    }

    private void format() throws IOException {
        List<String> lines = sourceCodeFile.readContentLines();
        lines = prepare(lines);
        lines = addBlankLines(lines);
        lines = addTabs(lines);
        lines = decider.postProcessFormattedLines(lines);
        sourceCodeFile.setFormattedLines(lines);
    }

    private List<String> prepare(List<String> lines) {
        List<String> resultLines = new ArrayList<>();
        for (int lineNumber = 0; lineNumber < lines.size(); lineNumber++) {
            if (!decider.killLine(lines, lineNumber)) {
                resultLines.add(lines.get(lineNumber));
            }
        }
        return decider.preProcessLines(resultLines);
    }

    private List<String> addTabs(List<String> lines) {
        List<String> resultLines = new ArrayList<>();
        int tabLevel = 0;
        for (String line : lines) {
            tabLevel += decider.tabChangeThisLine(line);
            resultLines.add(StringUtils.repeat(decider.getIndent(), tabLevel) + line);
            tabLevel += decider.tabChangeNextLine(line);
        }
        return resultLines;
    }

    private List<String> addBlankLines(List<String> lines) {
        List<String> resultLines = new ArrayList<>();
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);

            // add blank lines before
            int blankLinesBefore = decider.blankLinesBefore(lines, i);
            int bi = 0;
            while (bi++ < blankLinesBefore) resultLines.add("");
            resultLines.add(line);

            // add blank lines after
            int blankLinesAfter = decider.blankLinesAfter(line);
            int ai = 0;
            while (ai++ < blankLinesAfter) resultLines.add("");
        }
        return resultLines;
    }

    private void withSource(Consumer<String> consumer) {
        consumer.accept(sourceCodeFile.getFormattedLines().stream().collect(Collectors.joining(decider.getEol())));
    }

    public static void start(Path inputDirectory) throws IOException {
        new SourceCodeFiles(inputDirectory, "java").forEach(process());
    }

    private static Consumer<SourceCodeFile> process() {
        return (sourceCodeFile) -> {
            SourceCodeFormatter formatter = new SourceCodeFormatter(sourceCodeFile);
            try {
                formatter.format();
                formatter.withSource(formattedSource -> {
                    try {
                        FileUtils.writeStringToFile(sourceCodeFile.getPath().toFile(), formattedSource);
                    } catch (IOException e) {
                        System.exit(1610142042);
                    }
                });
            } catch (IOException e) {
                System.exit(1610122032);
            }
        };
    }
}