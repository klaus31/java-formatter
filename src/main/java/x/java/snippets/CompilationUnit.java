package x.java.snippets;

import x.ctrl.SourceCodeFile;
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

public class CompilationUnit {

    private List<JavaCodeSnippet> snippets;

    public CompilationUnit() {
        snippets = new ArrayList<>();
    }

    public void add(JavaCodeSnippet snippet) {
        snippets.add(snippet);
    }

    public String toSourceString(SourceCodeFile file) {
        StringBuilder result = new StringBuilder();
        for (JavaCodeSnippet snippet : snippets) {
            result.append(snippet.toSourceString(file));
        }
        result.append(JavaConfig.END_OF_FILE);
        return result.toString();
    }
}