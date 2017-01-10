package x.java.snippets;

import com.sun.xml.internal.bind.v2.runtime.reflect.Lister;
import x.java.JavaConfig;
import x.java.JavaRulePath;
import x.java.NodeWrapper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

import static x.java.JavaConfig.EOL;
import static x.java.JavaConfig.RULES_HAVING_A_MATCHING_FORMATTER;

public class CompilationUnit implements JavaCodeSnippet {
    private static String ruleCurrentJavaCodeSnippetIsFor;
    private Optional<JavaCodeSnippet> currentCodeSnippet;
    private List<JavaCodeSnippet> snippets;

    public CompilationUnit() {
        snippets = new ArrayList<>();
        currentCodeSnippet = Optional.empty();
    }

    private static Optional<JavaCodeSnippet> getMatchingCodeSnippetFor(JavaRulePath rulePath) {
        Optional<String> newRule = rulePath.calculateLastRuleEqualsAnyOf(RULES_HAVING_A_MATCHING_FORMATTER);
        if (!newRule.isPresent() || newRule.get().equals(ruleCurrentJavaCodeSnippetIsFor)) {
            return Optional.empty();
        } else {
            ruleCurrentJavaCodeSnippetIsFor = newRule.get();
            return Optional.of(JavaConfig.getMatchingCodeSnippetFor(ruleCurrentJavaCodeSnippetIsFor));
        }
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
        List<JavaCodeBlock> blocks = createJavaCodeBlocks();
        StringBuilder result = new StringBuilder();
        for (JavaCodeBlock block : blocks) {
            result.append(block.toSourceString());
            if(blocks.indexOf(block) < blocks.size()-1) {
            result.append(EOL);

            }
        }
        return result.toString();
    }

    private List<JavaCodeBlock> createJavaCodeBlocks() {
        List<JavaCodeBlock> blocks = new ArrayList<>();
        JavaCodeBlock block = new JavaCodeBlock();
        for (JavaCodeSnippet snippet : snippets) {
            block.add(snippet);
            // FIXME den klassenname heranzuziehen ist an dieser Stelle bekloppt, weil:
            // 1. Was ist, wenn der Kommentar im Anschluss (in same line) kommt
            // 2. Was ist, wenn man eine andere Klasse konfiguriert
            // fuer 1. benoetige ich die Info "in same line" bzw. koennte man das letzte EOL heranziehen
            // fuer 2. muss so etwas her wie snippet.blankLineAfter() mit enum NONE, FORCE und AS_LAST_IN_BLOCK
            // und dann wäre da noch die sortierung, die vorweg erfolgen muss. Jesus.
            if (snippet instanceof PackageDeclaration) {
                blocks.add(block);
                block = new JavaCodeBlock();
            }
        }
        blocks.add(block);
        return blocks;
    }

    @Override
    public void enterRule(JavaRulePath rulePath) {
        getMatchingCodeSnippetFor(rulePath).ifPresent(this::setCurrentCodeSnippet);
        withCurrentSnippetIfPresent(s -> s.enterRule(rulePath));
    }
}