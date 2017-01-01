package x.java.snippets;

import x.java.IndentService;
import x.java.JavaConfig;
import x.java.NodeWrapper;

import java.util.Arrays;
import java.util.List;

public class ImportDeclaration extends SimpleNodesJavaCodeSnippet {

    private static final List<String> WHITESPACE_WORDS = Arrays.asList("import", "static");

    ImportDeclaration(IndentService indentService) {
        super(indentService);
    }

    @Override
    protected String toSourceString(NodeWrapper node) {
        final StringBuilder result = new StringBuilder();
        result.append(node.toSourceString());
        if (WHITESPACE_WORDS.contains(node.toSourceString())) {
            result.append(" ");
        }
        if (node.isSemicolonAtEnd()) {
            // TODO this code must be replace every other code appending an EOL
            if (node.isNextNodeACommentInSameLine()) {
                result.append(" ");
            } else {
                result.append(JavaConfig.EOL);
            }
        }
        return result.toString();
    }

}
