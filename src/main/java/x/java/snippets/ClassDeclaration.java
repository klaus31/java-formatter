package x.java.snippets;

import x.java.NodeWrapper;

import static x.java.JavaConfig.EOL;
import static x.java.JavaConfig.getIndentService;

public class ClassDeclaration extends SimpleNodesJavaCodeSnippet {

    @Override
    protected String toSourceString(NodeWrapper node) {
        StringBuilder builder = new StringBuilder();
        builder.append(node.toSourceString());
        if (node.isBlockStartOrEnd() || node.isSemicolonAtEnd()) {
            builder.append(EOL);
            builder.append(getIndentService().calculateIndentToAppendTo(node));
        } else {
            builder.append(" ");
        }
        return builder.toString();
    }
}
