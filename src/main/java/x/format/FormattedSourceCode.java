package x.format;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static java.util.stream.Collectors.toList;

public class FormattedSourceCode {

    private final List<CodeLine> lines;

    private Map<Object, Object> code;

    public FormattedSourceCode() {
        this.lines = new ArrayList<>();
    }

    public void addLine(CodeLine line) {
        lines.add(line);
    }

    public List<String> getCode() {
        return lines.stream().map(CodeLine::getLine).collect(toList());
    }

    public void calculateIndent(Function<SourceCodeSnippet, Integer> calculateIndentSize) {
        int totalIndentSize = 0;
        for (int i = 0; i < lines.size(); i++) {
            CodeLine previousCodeLine = i == 0 ? null : lines.get(i - 1);
            totalIndentSize += calculateIndentSize.apply(new SourceCodeSnippet(lines.get(i), previousCodeLine));
            lines.get(i).setIndentSize(totalIndentSize);
        }
    }
}
