package x.format;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

    public int countLines() {
        return lines.size();
    }
}
