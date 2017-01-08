package x.java.snippets;

import x.java.JavaRulePath;
import x.java.NodeWrapper;
import java.util.ArrayList;
import java.util.List;
import static x.ctrl.MiserableLogger.logDebug;
import static x.java.JavaConfig.getIndentService;
public abstract class SimpleNodesJavaCodeSnippet implements JavaCodeSnippet {
    private final List<NodeWrapper> nodes;
    SimpleNodesJavaCodeSnippet() {
        nodes = new ArrayList<>();
    }
    String indentCurrent() {
        return getIndentService().getCurrentIndent();
    }
    @Override
    public String toSourceString() {
        if (nodes.isEmpty()) {
            return "";
        }
        final StringBuilder builder = new StringBuilder();
        for (NodeWrapper node : nodes) {
            logDebug(node.toString());
            builder.append(toSourceString(node));
        }
        return builder.toString();
    }
    protected abstract String toSourceString(NodeWrapper node);
    @Override
    public void add(NodeWrapper node) {
        nodes.add(node);
    }
    @Override
    public void enterRule(JavaRulePath rulePath) {
    }
}