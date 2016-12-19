package x.java;

import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.ArrayList;
import java.util.List;

public class CodeLine {

    private final List<String> lineParts;
    private final RulePath rulePath;

    public CodeLine(RulePath rulePath) {
        this.rulePath = rulePath;
        lineParts = new ArrayList<>();
    }

    public void addWhitspace() {
lineParts.add(" ");
    }
    public void addPart(TerminalNode node) {
        lineParts.add(node.toString());
    }

    public boolean isEmpty() {
        return lineParts.isEmpty();
    }

    public int countParts() {
        return lineParts.size();
    }

    public String getLine() {
        return String.join("", lineParts);
    }
}
