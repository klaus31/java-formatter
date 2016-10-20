package javaformatter;

import org.apache.commons.lang3.StringUtils;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

class SourceCodeFormatter {
    
    private final SourceCodeFile sourceCodeFile;
    
    private final CodeActionDecider codeActionDecider;
    
    SourceCodeFormatter(SourceCodeFile sourceCodeFile) {
        this.sourceCodeFile = sourceCodeFile;
        this.codeActionDecider = CodeActionDeciderSimpleFactory.create(sourceCodeFile.getSuffix());
    }
    
    void format() throws IOException {
        List<String> lines = sourceCodeFile.readContentLines();
        lines = prepare(lines);
        lines = addBlankLines(lines);
        lines = addTabs(lines);
        lines = codeActionDecider.postProcessFormattedLines(lines);
        sourceCodeFile.setFormattedLines(lines);
    }
    
    private List<String> prepare(List<String> lines) {
        List<String> resultLines = new ArrayList<>();
        for(int lineNumber=0; lineNumber< lines.size(); lineNumber++) {
            if (!codeActionDecider.killLine(lines, lineNumber)) {
                resultLines.add(lines.get(lineNumber));
            }
        }
        return codeActionDecider.preProcessLines(resultLines);
    }
    
    private List<String> addTabs(List<String> lines) {
        List<String> resultLines = new ArrayList<>();
        int tabLevel = 0;
        
        for(String line : lines) {
            tabLevel += codeActionDecider.tabChangeThisLine(line);
            resultLines.add(StringUtils.repeat(codeActionDecider.getIndent(), tabLevel) + line);
            tabLevel += codeActionDecider.tabChangeNextLine(line);
        }
        return resultLines;
    }
    
    private List<String> addBlankLines(List<String> lines) {
        List<String> resultLines = new ArrayList<>();
        for(int i=0; i< lines.size(); i++) {
            String line = lines.get(i);
            
            // add blank lines
            int blankLinesBefore = codeActionDecider.blankLinesBefore(lines, i);
            int bi = 0;
            while(bi++ < blankLinesBefore) resultLines.add("");
            resultLines.add(line);
            
            // add blank lines
            int blankLinesAfter = codeActionDecider.blankLinesAfter(line);
            int ai = 0;
            while(ai++ < blankLinesAfter) resultLines.add("");
        }
        return resultLines;
    }
    
    void withSource(Consumer<String> consumer) {
        consumer.accept(sourceCodeFile.getFormattedLines().stream().collect(Collectors.joining(codeActionDecider.getEol())));
    }
}