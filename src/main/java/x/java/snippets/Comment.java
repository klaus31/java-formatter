package x.java.snippets;

import x.java.IndentService;
import x.java.JavaConfig;
import x.java.NodeWrapper;

import static x.java.JavaConfig.EOL;

public class Comment extends SimpleNodesJavaCodeSnippet {

    @Override
    protected String toSourceString(NodeWrapper node) {
        final StringBuilder result = new StringBuilder();
        result.append(node.toSourceString());
        result.append(EOL);
        result.append(indentCurrent());
        return result.toString();
    }

}
