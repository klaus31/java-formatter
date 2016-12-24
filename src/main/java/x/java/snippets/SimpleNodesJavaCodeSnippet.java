package x.java.snippets;

import x.java.JavaRulePath;
import x.java.NodeWrapper;

import java.util.ArrayList;
import java.util.List;

abstract class SimpleNodesJavaCodeSnippet implements JavaCodeSnippet {

    private final List<NodeWrapper> nodes;

    SimpleNodesJavaCodeSnippet() {
        nodes = new ArrayList<>();
    }

    @Override
    public String toSourceString() {
        final StringBuilder builder = new StringBuilder();
        for (NodeWrapper node : nodes) {
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