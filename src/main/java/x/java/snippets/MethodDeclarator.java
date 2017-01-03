package x.java.snippets;

import x.java.IndentService;
import x.java.JavaConfig;
import x.java.NodeWrapper;

import static java.util.Arrays.asList;
import static x.java.JavaConfig.EOL;
import static x.java.JavaConfig.getIndentService;

class MethodDeclarator extends SimpleNodesJavaCodeSnippet {

    @Override
    protected String toSourceString(NodeWrapper node) {
        StringBuilder builder = new StringBuilder();
        builder.append(node.toSourceString());
        if (asList("}", "{", ";").contains(node.toSourceString())) {
            builder.append(EOL);
            builder.append(getIndentService().calculateIndentToAppendTo(node));
        } else if (!asList("(").contains(node.toSourceString()) && !asList("(", ")", "<", ">", ",").contains(node.calculateNext().getText())) {
            builder.append(" ");
        }
        return builder.toString();
    }
}