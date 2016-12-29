package x.java.snippets;

import x.java.JavaConfig;
import x.java.NodeWrapper;

public class Comment extends SimpleNodesJavaCodeSnippet {

    @Override
    public String toSourceString() {
        return super.toSourceString();
    }

    @Override
    protected String toSourceString(NodeWrapper node) {
        final StringBuilder result = new StringBuilder();
        result.append(JavaConfig.EOL);
        result.append(node.toSourceString());
        if(requiresEolAfter(node)) {
        result.append(JavaConfig.EOL);
        }
        return result.toString();
    }

    private boolean requiresEolAfter(NodeWrapper node) {
        String nextNodeText = node.calculateNext().getText();
        return !nextNodeText.matches("\\\\\\*.*\\*\\\\") &&!nextNodeText.matches("//.*");
    }
}
