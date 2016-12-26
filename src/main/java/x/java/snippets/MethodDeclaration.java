package x.java.snippets;

import x.java.JavaConfig;
import x.java.NodeWrapper;

public class MethodDeclaration extends SimpleNodesJavaCodeSnippet {

    @Override
    public String toSourceString() {
        return super.toSourceString() + JavaConfig.EOL;
    }

    @Override
    protected String toSourceString(NodeWrapper node) {
        final StringBuilder result = new StringBuilder();
        result.append(node.toSourceString());
        if (!node.toSourceString().equals("}"))
            result.append(" ");
        return result.toString();
    }

}
