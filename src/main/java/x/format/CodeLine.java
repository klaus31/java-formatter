package x.format;

import x.java.NodeWrapper;

import java.util.ArrayList;
import java.util.List;

public class CodeLine {

    private final List<String> lineParts;

    public CodeLine() {
        lineParts = new ArrayList<>();
    }

    public void addWhitspace() {
        lineParts.add(" ");
    }

    public void addPart(NodeWrapper node) {
        lineParts.add(node.getNode().toString());
    }

    public boolean isEmpty() {
        return lineParts.isEmpty();
    }

    public int countParts() {
        return lineParts.size();
    }

    String getLine() {
        return String.join("", lineParts);
    }

    void clear() {
        lineParts.clear();
    }
}
