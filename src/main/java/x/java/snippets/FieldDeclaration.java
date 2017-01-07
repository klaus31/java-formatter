package x.java.snippets;

import org.antlr.v4.runtime.tree.ParseTree;
import x.java.IndentService;
import x.java.NodeWrapper;
import static java.util.Arrays.asList;
import static x.java.JavaConfig.EOL;
import static x.java.JavaConfig.getIndentService;
public class FieldDeclaration extends SimpleNodesJavaCodeSnippet {
    private NodeWrapper lastNode;
    @Override
    public String toSourceString() {
        return super.toSourceString() + (lastNode.isNextNodeACommentInSameLine() ? " " :
        EOL + getIndentService().calculateIndentToAppendTo(lastNode));
    }
    @Override
    protected String toSourceString(NodeWrapper node) {
        lastNode = node;
        final StringBuilder result = new StringBuilder();
        result.append(node.toSourceString());
        if (requiresWhitespaceAfter(node)) {
            result.append(" ");
        }
        return result.toString();
    }
    private boolean requiresWhitespaceAfter(NodeWrapper node) {
        if (asList(";", "(").contains(node.toSourceString())) {
            return false;
        }
        if (asList("new").contains(node.toSourceString())) {
            return true;
        }
        ParseTree nextNode = node.calculateNext();
        if (asList(";", ")").contains(nextNode.getText())) {
            return false;
        }
        if (")".equals(node.toSourceString())) {
            return ! asList(".").contains(nextNode.getText());
        }
        if (node.matchesRulePath("unannClassType_lfno_unannClassOrInterfaceType")) {
            if (asList("<", ",", ">").contains(nextNode.getText())) {
                return false;
            } else if (node.toSourceString().equals(",")) {
                return true;
            } else if (node.toSourceString().equals("<")) {
                return false;
            } else {
                return true;
            }
        }
        if (node.matchesRulePath("fieldDeclaration", "classInstanceCreationExpression_lfno_primary")) {
            return false; // expecting diamond operator
        }
        return true;
    }
}