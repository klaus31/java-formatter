package x.java.snippets;

import x.java.IndentService;
import x.java.JavaConfig;
import x.java.JavaRulePath;
import x.java.NodeWrapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.joining;

public class ImportDeclaration extends SimpleNodesJavaCodeSnippet {

    private static final List<String> WHITESPACE_WORDS = Arrays.asList("import", "static");

    ImportDeclaration(IndentService indentService) {
        super(indentService);
    }

    @Override
    protected String toSourceString(NodeWrapper node) {
        final StringBuilder result = new StringBuilder();
        result.append(node.toSourceString());
        if(WHITESPACE_WORDS.contains(node.toSourceString())) {
            result.append(" ");
        }
        if(node.isSemicolonAtEnd()) {
            result.append(JavaConfig.EOL);
        }
        return result.toString();
    }

}
