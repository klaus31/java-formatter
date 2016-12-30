package x.java.snippets;

import x.java.IndentService;
import x.java.JavaConfig;
import x.java.NodeWrapper;

public class Annotation extends SimpleNodesJavaCodeSnippet {

    Annotation(IndentService indentService) {
        super(indentService);
    }

    @Override
    public String toSourceString() {
        return super.toSourceString() + JavaConfig.EOL + indentCurrent();
    }

    @Override
    protected String toSourceString(NodeWrapper node) {
        final StringBuilder result = new StringBuilder();
        result.append(node.toSourceString());
        if (node.toSourceString().equals(",")) {
            result.append(" ");
        }
        return result.toString();
    }
}
