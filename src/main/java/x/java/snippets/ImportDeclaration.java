package x.java.snippets;

import org.antlr.v4.runtime.tree.ParseTree;
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
        // TODO this code must be replace every other code appending an EOL
        if(node.isSemicolonAtEnd()) {
            if(node.isNextNodeAComment()) {
                ParseTree nextNode = node.calculateNext();
                if(node.occursOnSameLineAs(nextNode)) {
                    result.append(" ");
                } else {
                    result.append(JavaConfig.EOL);
                }
            } else {
                result.append(JavaConfig.EOL);
            }
        }
        return result.toString();
    }

}
