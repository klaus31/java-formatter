package x.java.snippets;

import x.ctrl.SourceCodeFile;
import x.java.JavaRulePath;
import x.java.NodeWrapper;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import static x.ctrl.MiserableLogger.logDebug;
import static x.java.JavaConfig.getIndentService;
public abstract class SimpleNodesJavaCodeSnippet implements JavaCodeSnippet {
    private final List<NodeWrapper> nodes;
    SimpleNodesJavaCodeSnippet() {
        nodes = new ArrayList<>();
    }
    String indentCurrent(SourceCodeFile file) {
        return getIndentService(file).getCurrentIndent();
    }
    @Override
    public String toSourceString(SourceCodeFile file) {
        if (nodes.isEmpty()) {
            return "";
        }
        final StringBuilder builder = new StringBuilder();
        for (NodeWrapper node : nodes) {
            logDebug(node.toString());
            builder.append(toSourceString(node, file));
        }
        return builder.toString();
    }
    protected abstract String toSourceString(NodeWrapper node, SourceCodeFile file);
    @Override
    public void add(NodeWrapper node) {
        nodes.add(node);
    }
    @Override
    public void enterRule(JavaRulePath rulePath) {
    }
}