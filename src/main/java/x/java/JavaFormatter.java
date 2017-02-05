package x.java;

import x.format.*;
import x.java.snippets.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static x.java.JavaConfig.RULES_HAVING_A_MATCHING_FORMATTER;
import static x.java.JavaConfig.getMatchingCodeSnippetFor;
class JavaFormatter implements Formatter {

    private final CompilationUnit compilationUnit;
    private final List<NodeWrapper> nodes;
    private String ruleCurrentJavaCodeSnippetIsFor;
    private JavaCodeSnippet currentCodeSnippet;

    JavaFormatter() {
        compilationUnit = JavaConfig.createCompilationUnit();
        nodes = new ArrayList<>();
    }

    private void setMatchingCodeSnippetFor(JavaRulePath rulePath) {
        Optional<String> newRule = rulePath.calculateLastRuleEqualsAnyOf(RULES_HAVING_A_MATCHING_FORMATTER);

        // TODO maybe, it is not the current rule, but a new block with same rule
        if (newRule.isPresent() && !newRule.get().equals(ruleCurrentJavaCodeSnippetIsFor)) {
            ruleCurrentJavaCodeSnippetIsFor = newRule.get();
            Optional<JavaCodeSnippet> matchingCodeSnippetFor = getMatchingCodeSnippetFor(ruleCurrentJavaCodeSnippetIsFor);
            if (matchingCodeSnippetFor.isPresent()) {
                if (currentCodeSnippet != null) compilationUnit.add(currentCodeSnippet);
                currentCodeSnippet = matchingCodeSnippetFor.get();
            }
        }
    }
    void add(NodeWrapper node) {
        nodes.add(node);
    }

    @Override
    public FormattedSourceCode getFormattedSourceCode() {
        for (NodeWrapper node : nodes) {
            if (node.isEOF()) continue;
            setMatchingCodeSnippetFor(node.getJavaRulePath());
            currentCodeSnippet.add(node, nodes);
        }
        if (currentCodeSnippet != null) compilationUnit.add(currentCodeSnippet);
        return new FormattedSourceCode(compilationUnit);
    }
}