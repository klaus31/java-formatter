package x.java.snippets;

import x.java.NodeWrapper;
import static java.util.Arrays.asList;
import static x.java.JavaConfig.*;

public class MethodBody extends SimpleNodesJavaCodeSnippet {
    @Override
    protected String toSourceString(NodeWrapper node) {
        StringBuilder result = new StringBuilder();
        result.append(node.toSourceString());
        if (! onlyPureNodeValueIsRequired(node)) {
            if (requiresSingleBlankAfter(node)) {
                result.append(" ");
            } else if (getEolService().requiresSingleEolAfterNodeInAnyCase(node).orElse(false)) {
                result.append(EOL);
                result.append(getIndentService().calculateIndentToAppendTo(node));
            }
        }
        return result.toString();
    }
    private boolean onlyPureNodeValueIsRequired(NodeWrapper node) {
        return node.isBlockEnd() && asList(")", ";").contains(node.calculateNext().getText()) || node.isBlockStart() && node.matchesRulePath("arrayInitializer");
    }
    private boolean requiresSingleBlankAfter(NodeWrapper node) {
        return getBlankService().requiresSingleBlankAfterNodeInAnyCase(node).orElse(!node.matchesRulePath("methodDeclaration", "classInstanceCreationExpression_lfno_primary"));
    }
}