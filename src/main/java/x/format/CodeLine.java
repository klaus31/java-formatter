package x.format;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.joining;
import static x.java.JavaConfig.INTENT;

public class CodeLine {

    private final List<CodeLinePart> lineParts;
    private int indentSize = 0;

    public CodeLine() {
        lineParts = new ArrayList<>();
    }

    public CodeLinePart getLastElement() {
        return lineParts.get(lineParts.size()-1);
    }
    public CodeLinePart getFirstElement() {
        return lineParts.get(0);
    }

    public void setIndentSize(int indentSize) {
        this.indentSize = indentSize;
    }

    public void addWhitspace() {
        lineParts.add(SimpleCodeLinePart.produceWhitespace());
    }

    public void addPart(CodeLinePart linePart) {
        lineParts.add(linePart);
    }

    String getLine() {
        return StringUtils.repeat(INTENT, indentSize) + lineParts.stream().map(part -> part.toSourceString()).collect(joining());
    }
}
