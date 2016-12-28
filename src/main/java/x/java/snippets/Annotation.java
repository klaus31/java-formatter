package x.java.snippets;

import x.java.IndentService;
import x.java.JavaConfig;
import x.java.NodeWrapper;

public class Annotation extends SimpleNodesJavaCodeSnippet {

    private final IndentService indentService;

    public Annotation(IndentService indentService) {
        this.indentService = indentService;
    }

    @Override
    public String toSourceString() {
        return super.toSourceString() + JavaConfig.EOL + indentService.indentCurrent();
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
