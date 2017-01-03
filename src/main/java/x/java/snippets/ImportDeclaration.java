package x.java.snippets;

import x.java.IndentService;
import x.java.NodeWrapper;

import java.util.Arrays;
import java.util.List;

import static x.java.JavaConfig.EOL;

public class ImportDeclaration extends SimpleNodesJavaCodeSnippet {

    private static final List<String> WHITESPACE_WORDS = Arrays.asList("import", "static");

    @Override
    protected String toSourceString(NodeWrapper node) {
        final StringBuilder result = new StringBuilder();
        result.append(node.toSourceString());
        if (WHITESPACE_WORDS.contains(node.toSourceString())) {
            result.append(" ");
        }
        if (node.isSemicolonAtEnd()) {
            result.append(node.isNextNodeACommentInSameLine() ? " " : EOL);
        }
        return result.toString();
    }

}
