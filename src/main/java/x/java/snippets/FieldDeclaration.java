package x.java.snippets;

import x.java.NodeWrapper;
import static x.java.JavaConfig.EOL;
import static x.java.JavaConfig.getBlankService;
import static x.java.JavaConfig.getIndentService;
public class FieldDeclaration extends SimpleNodesJavaCodeSnippet {
    private NodeWrapper lastNode;
    @Override
    public String toSourceString() {
        String superResult = super.toSourceString();
        if (superResult.isEmpty()) {
            return "";
        } else {
            return superResult + (lastNode.isNextNodeACommentInSameLine() ? "" : EOL + getIndentService().calculateIndentToAppendTo(lastNode));
        }
    }
    @Override
    protected String toSourceString(NodeWrapper node) {
        lastNode = node;
        final StringBuilder result = new StringBuilder();
        result.append(node.toSourceString());
        if (requiresSingleBlankAfterNodeInAnyCase(node)) {
            result.append(" ");
        }
        return result.toString();
    }
    private boolean requiresSingleBlankAfterNodeInAnyCase(NodeWrapper node) {
        return getBlankService().requiresSingleBlankAfterNodeInAnyCase(node).orElse(! node.matchesRulePath("fieldDeclaration", "classInstanceCreationExpression_lfno_primary"));
    }
}