package x.java;

import x.format.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

class JavaFormatter implements Formatter {

    private final FormattedSourceCode formattedSourceCode;
    private List<NodeWrapper> cache;
    private CodeLine currentLine;

    JavaFormatter() {
        cache = new ArrayList<>();
        formattedSourceCode = new FormattedSourceCode();
        currentLine = new CodeLine();
    }

    void add(NodeWrapper node) {
        cache.add(node);
    }

    private Integer calculateIndentChange(SourceCodeSnippet snippet) {
        int result = 0;
        if (snippet.getPreviousCodeLine().isPresent() && snippet.getPreviousCodeLine().get().getLastElement().toSourceString().equals("{")) {
            result++;
        }
        if (snippet.getCurrentCodeLine().getFirstElement().toSourceString().equals("}")) {
            result--;
        }
        return result;
    }

    @Override
    public FormattedSourceCode getFormattedSourceCode() {
        return formattedSourceCode;
    }

    public void exitRule(JavaRulePath rulePath) {
        if (isRelevant(rulePath)) {
            String part = cache.stream().map(node -> node.toSourceString()).collect(Collectors.joining(" "));
            currentLine.addPart(new SimpleCodeLinePart(part));
            if (requiresEOL()) {
                formattedSourceCode.addLine(currentLine);
                currentLine = new CodeLine();
            }
            cache = new ArrayList<>();
            if (rulePath.isCurrentRuleA("compilationUnit")) {
                formattedSourceCode.addLine(currentLine);
                currentLine = new CodeLine();
                formattedSourceCode.calculateIndent(this::calculateIndentChange);
            }
        }
        System.out.println(rulePath.toString() + " " + isRelevant(rulePath));
    }

    private boolean isRelevant(JavaRulePath rulePath) {
        return rulePath.matchesCurrentRuleAnyOf("annotation", "packageDeclaration", "importDeclaration", "compilationUnit");
    }

    public boolean requiresEOL() {
        return cache.get(cache.size() - 1).requiresEOL();
    }
}
