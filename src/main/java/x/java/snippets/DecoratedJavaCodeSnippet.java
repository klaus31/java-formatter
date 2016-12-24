package x.java.snippets;

import x.format.CodeSnippet;
import x.java.NodeWrapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

import static java.util.stream.Collectors.joining;

abstract class DecoratedJavaCodeSnippet implements JavaCodeSnippet {

    private Optional<JavaCodeSnippet> currentCodeSnippet;
    private List<JavaCodeSnippet> snippets;


    void setCurrentCodeSnippet(JavaCodeSnippet javaCodeSnippet) {
        flushCurrentSnippetIfPresent();
        currentCodeSnippet = Optional.ofNullable(javaCodeSnippet);
    }

    private void flushCurrentSnippetIfPresent() {
        withCurrentSnippetIfPresent(snippets::add);
    }

    void withCurrentSnippetIfPresent(Consumer<JavaCodeSnippet> consumer) {
        currentCodeSnippet.ifPresent(consumer);
    }

    public DecoratedJavaCodeSnippet() {
        snippets = new ArrayList<>();
        currentCodeSnippet = Optional.empty();
    }

    @Override
    public void add(NodeWrapper node) {
        currentCodeSnippet.orElseThrow(() -> new AssertionError("unexpected node: " + node)).add(node);
    }

    @Override
    public String toSourceString() {
        flushCurrentSnippetIfPresent();
        return snippets.stream().map(CodeSnippet::toSourceString).collect(joining());
    }

}
