package x.java;

import x.format.FormattedSourceCode;
import x.format.Formatter;
import x.java.snippets.CompilationUnit;
import x.java.snippets.JavaCodeSnippet;
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

    private void setMatchingCodeSnippetFor(NodeWrapper node) {
        JavaRulePath rulePath = node.getJavaRulePath();
        Optional<String> newRule = rulePath.calculateLastRuleEqualsAnyOf(RULES_HAVING_A_MATCHING_FORMATTER);

        // TODO maybe, it is not the current rule, but a new block with same rule
        if (newRule.isPresent() && newRuleRequiresNewCodeSnippet(newRule.get(), node)) {
            ruleCurrentJavaCodeSnippetIsFor = newRule.get();
            Optional<JavaCodeSnippet> matchingCodeSnippetFor = getMatchingCodeSnippetFor(ruleCurrentJavaCodeSnippetIsFor);
            if (matchingCodeSnippetFor.isPresent()) {
                if (currentCodeSnippet != null) compilationUnit.add(currentCodeSnippet);
                currentCodeSnippet = matchingCodeSnippetFor.get();
            }
        }
    }

    private boolean newRuleRequiresNewCodeSnippet(String newRule, NodeWrapper node) {
        if (!newRule.equals(ruleCurrentJavaCodeSnippetIsFor)) {
            return true;
        } else if (node.getJavaRulePath().isCurrentRuleA("interfaceMethodModifier")) {
            return true;
        } else if (node.getJavaRulePath().isCurrentRuleA("constructorModifier")) {
            return true;
        } else if (isStartOfANewMethod(newRule, node)) {
            return true;
        }
        return false;
    }

    private boolean isStartOfANewMethod(String newRule, NodeWrapper node) {
        if (node.getJavaRulePath().isCurrentRuleA("methodModifier")) {
            return !nodes.get(nodes.indexOf(node) - 1).getJavaRulePath().isCurrentRuleA("methodModifier");
        } else if ("methodDeclaration".equals(newRule) && "methodDeclaration".equals(ruleCurrentJavaCodeSnippetIsFor)) {
            NodeWrapper previousNode = nodes.get(nodes.indexOf(node) - 1);
            int lastIndexMethodHeader = node.getJavaRulePath().lastIndexOf("methodHeader");
            return lastIndexMethodHeader > 0 && previousNode.getJavaRulePath().lastIndexOf("methodHeader") != lastIndexMethodHeader && previousNode.getJavaRulePath().lastIndexOf("methodModifier") != lastIndexMethodHeader;
        } else {
            return false;
        }
    }

    void add(NodeWrapper node) {
        nodes.add(node);
    }

    @Override
    public FormattedSourceCode getFormattedSourceCode() {
        for (NodeWrapper node : nodes) {
            if (node.isEOF()) continue;
            setMatchingCodeSnippetFor(node);
            currentCodeSnippet.add(node, nodes);
        }
        if (currentCodeSnippet != null) compilationUnit.add(currentCodeSnippet);
        return new FormattedSourceCode(compilationUnit);
    }
}