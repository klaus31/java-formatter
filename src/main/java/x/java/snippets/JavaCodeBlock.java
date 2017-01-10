package x.java.snippets;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class JavaCodeBlock {
    List<JavaCodeSnippet> snippets;

    public JavaCodeBlock() {
        snippets = new ArrayList<>();
    }

    public void add(JavaCodeSnippet snippet) {
        snippets.add(snippet);
    }

    public String toSourceString() {
        return snippets.stream().map(JavaCodeSnippet::toSourceString).collect(Collectors.joining());
    }
}
