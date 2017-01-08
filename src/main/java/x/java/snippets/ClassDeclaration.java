package x.java.snippets;

import x.java.JavaConfig;
import x.java.NodeWrapper;
import static java.util.Arrays.asList;
import static x.java.JavaConfig.EOL;
import static x.java.JavaConfig.getBlankService;
import static x.java.JavaConfig.getIndentService;
public class ClassDeclaration extends SimpleNodesJavaCodeSnippet {
    @Override
    protected String toSourceString(NodeWrapper node) {
        StringBuilder builder = new StringBuilder();
        builder.append(node.toSourceString());
        if (node.isBlockEnd() && asList(")", ";").contains(node.calculateNext().getText())) {
            return builder.toString();
        }
        if (node.isBlockStartOrEnd() || node.isSemicolonAtEnd()) {
            builder.append(EOL);
            builder.append(getIndentService().calculateIndentToAppendTo(node));
        } else if(getBlankService().requiresSingleBlankAfterNodeInAnyCase(node).orElse(true)) {
            builder.append(" ");
        }
        return builder.toString();
    }
}