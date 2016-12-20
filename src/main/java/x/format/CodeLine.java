package x.format;

import x.java.NodeWrapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.joining;

public class CodeLine {

    private final List<CodeLinePart> lineParts;

    public CodeLine() {
        lineParts = new ArrayList<>();
    }

    public void addWhitspace() {
        lineParts.add(SimpleCodeLinePart.produceWhitespace());
    }

    public void addPart(CodeLinePart linePart) {
        lineParts.add(linePart);
    }

    public boolean isEmpty() {
        return lineParts.isEmpty();
    }

    public int countParts() {
        return lineParts.size();
    }

    String getLine() {
        return lineParts.stream().map(part -> part.toSourceString()).collect(joining());
    }

    void clear() {
        lineParts.clear();
    }
}
