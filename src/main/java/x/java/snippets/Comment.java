package x.java.snippets;

import x.java.IndentService;
import x.java.JavaConfig;
import x.java.NodeWrapper;

public class Comment extends SimpleNodesJavaCodeSnippet {

    Comment(IndentService indentService) {
        super(indentService);
    }

    @Override
    protected String toSourceString(NodeWrapper node) {
        final StringBuilder result = new StringBuilder();
        result.append(node.toSourceString());
        result.append(JavaConfig.EOL);
        result.append(indentCurrent());
        return result.toString();
    }

}
