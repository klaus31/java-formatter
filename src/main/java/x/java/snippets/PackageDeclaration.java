package x.java.snippets;

import x.java.NodeWrapper;
import static x.java.JavaConfig.EOL;
public class PackageDeclaration extends SimpleNodesJavaCodeSnippet {
    @Override
    public String toSourceString() {
        return super.toSourceString() + EOL + EOL;
    }
    @Override
    protected String toSourceString(NodeWrapper node) {
        final StringBuilder result = new StringBuilder();
        result.append(node.getText());
        if (node.getText().equals("package")) {
            result.append(" ");
        }
        return result.toString();
    }
}