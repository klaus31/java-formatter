package x.java.snippets;

import org.antlr.v4.runtime.tree.ParseTree;
import x.java.NodeWrapper;
import static java.util.Arrays.asList;
import static x.java.JavaConfig.EOL;
import static x.java.JavaConfig.getIndentService;
public class MethodBody extends SimpleNodesJavaCodeSnippet {
    @Override
    protected String toSourceString(NodeWrapper node) {
        StringBuilder result = new StringBuilder();
        result.append(node.toSourceString());
        if (! onlyPureNodeValueIsRequired(node)) {
            if (requiresSingleBlankAfter(node)) {
                result.append(" ");
            } else if (isAEolCandidate(node)) {
                result.append(EOL);
                result.append(getIndentService().calculateIndentToAppendTo(node));
            }
        }
        return result.toString();
    }
    private boolean isAEolCandidate(NodeWrapper node) {
        return node.isBlockStartOrEnd() || node.isSemicolonAtEnd() || ":".equals(node.toSourceString());
    }
    private boolean onlyPureNodeValueIsRequired(NodeWrapper node) {
        return node.isBlockEnd() && asList(")", ";").contains(node.calculateNext().getText()) || node.isBlockStart() && node.matchesRulePath("arrayInitializer");
    }
    private boolean requiresSingleBlankAfter(NodeWrapper node) {
        if (isAEolCandidate(node)) {
            if (node.isNextNodeACommentInSameLine() || node.isBlockEnd() && node.isNextNodeElseCatchOrWhile()) {
                return true;
            } else {
                return false;
            }
        }
        if (node.isSemicolonInBasicForStatement()) {
            return true;
        }
        if (asList(";", "::", "(", ".", "++", "--").contains(node.toSourceString())) {
            return false;
        }
        if (asList("new", ",").contains(node.toSourceString())) {
            return true;
        }
        ParseTree nextNode = node.calculateNext();
        if ("(".equals(nextNode.getText())) {
            return asList("catch", "switch", "if", "for", "while", "+", "-", "*", "/", "%").contains(node.toSourceString());
        }
        if (asList(";", "::", ")", ",", ".", "++", "--", "[", "]").contains(nextNode.getText())) {
            return false;
        }
        if (nextNode.getText().equals(":")) {
            // TODO for each in case block and named statements
            return ! node.matchesRulePath("switchBlock");
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
        if (node.matchesRulePath("methodDeclaration", "classInstanceCreationExpression_lfno_primary")) {
            return false; // expecting diamond operator
        }
        return true;
    }
}