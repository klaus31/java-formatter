package x.java.snippets;

import x.ctrl.SourceCodeFile;
import java.nio.file.Path;
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
    public String toSourceString(SourceCodeFile file) {
        return snippets.stream().map((javaCodeSnippet) -> javaCodeSnippet.toSourceString(file)).collect(Collectors.joining());
    }
}