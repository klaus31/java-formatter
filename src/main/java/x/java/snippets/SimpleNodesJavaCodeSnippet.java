package x.java.snippets;

import x.java.IndentService;
import x.java.JavaRulePath;
import x.java.NodeWrapper;

import java.util.ArrayList;
import java.util.List;

abstract class SimpleNodesJavaCodeSnippet implements JavaCodeSnippet {

    private final List<NodeWrapper> nodes;
    private final IndentService indentService;

    SimpleNodesJavaCodeSnippet(IndentService indentService) {
        nodes = new ArrayList<>();
        this.indentService = indentService;
    }

    protected String indentCurrent() {
        return indentService.getCurrentIndent();
    }

    @Override
    public String toSourceString() {
        final StringBuilder builder = new StringBuilder();
        for (NodeWrapper node : nodes) {
            if (node.matchesRulePath(rp->rp.isCurrentRuleA("comment"))) {
                builder.append(new Comment(indentService).toSourceString(node));
            }else{
            builder.append(toSourceString(node));
        }
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