package x.java.snippets;

import x.format.CodeSnippet;
import x.java.JavaConfig;
import x.java.JavaRulePath;
import x.java.NodeWrapper;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import static java.util.stream.Collectors.joining;
import static x.java.JavaConfig.EOL;
import static x.java.JavaConfig.RULES_HAVING_A_MATCHING_FORMATTER;
public class CompilationUnit implements JavaCodeSnippet {
    private Optional<JavaCodeSnippet> currentCodeSnippet;
    private List<JavaCodeSnippet> snippets;
    public CompilationUnit() {
        snippets = new ArrayList<>();
        currentCodeSnippet = Optional.empty();
    }
    private void setCurrentCodeSnippet(JavaCodeSnippet javaCodeSnippet) {
        flushCurrentSnippetIfPresent();
        currentCodeSnippet = Optional.ofNullable(javaCodeSnippet);
    }
    private void flushCurrentSnippetIfPresent() {
        withCurrentSnippetIfPresent(snippets::add);
    }
    private void withCurrentSnippetIfPresent(Consumer<JavaCodeSnippet> consumer) {
        currentCodeSnippet.ifPresent(consumer);
    }
    @Override
    public void add(NodeWrapper node) {
        currentCodeSnippet.orElseThrow(() -> new AssertionError("Sorry for X-Formatter is unable to format valid but unexpected: " + node)).add(node);
    }
    @Override
    public String toSourceString() {
        flushCurrentSnippetIfPresent();
        Collections.sort(snippets, JavaConfig.getSnippetComparator());
        StringBuilder result = new StringBuilder();
        for (JavaCodeSnippet snippet : snippets) {
            result.append(snippet.toSourceString());
        }
        return result.toString();
    }
    @Override
    public void enterRule(JavaRulePath rulePath) {
        getMatchingCodeSnippetFor(rulePath).ifPresent(this::setCurrentCodeSnippet);
        withCurrentSnippetIfPresent(s -> s.enterRule(rulePath));
    }
    private static String ruleCurrentJavaCodeSnippetIsFor;
    private static Optional<JavaCodeSnippet> getMatchingCodeSnippetFor(JavaRulePath rulePath) {
        Optional<String> newRule = rulePath.calculateLastRuleEqualsAnyOf(RULES_HAVING_A_MATCHING_FORMATTER);
        if (!newRule.isPresent() || newRule.get().equals(ruleCurrentJavaCodeSnippetIsFor)) {
            return Optional.empty();
        } else {
            ruleCurrentJavaCodeSnippetIsFor = newRule.get();
            return Optional.of(JavaConfig.getMatchingCodeSnippetFor(ruleCurrentJavaCodeSnippetIsFor));
        }
    }
}