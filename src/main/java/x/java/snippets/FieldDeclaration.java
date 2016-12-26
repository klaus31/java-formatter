package x.java.snippets;

import org.antlr.v4.runtime.tree.ParseTree;
import x.java.JavaConfig;
import x.java.NodeWrapper;

import java.util.Arrays;

public class FieldDeclaration extends SimpleNodesJavaCodeSnippet {

    @Override
    public String toSourceString() {
        return super.toSourceString() + JavaConfig.EOL;
    }

    @Override
    protected String toSourceString(NodeWrapper node) {
        final StringBuilder result = new StringBuilder();
        result.append(node.toSourceString());
        if (requiresWhitespaceAfter(node)) {
            result.append(" ");
        }
        return result.toString();
    }

    private boolean requiresWhitespaceAfter(NodeWrapper node) {
        if (node.toSourceString().matches(";")) {
            return false;
        }
        ParseTree nextNode = node.calculateNext();
        if (node.matchesRulePath("unannClassType_lfno_unannClassOrInterfaceType")) {
            if (Arrays.asList("<", ",", ">").contains(nextNode.getText())) {
                return false;
            } else if (node.toSourceString().equals(",")) {
                return true;
            } else if (node.toSourceString().equals("<")) {
                return false;
            } else {
                return true;
            }
        }
        if (node.matchesRulePath("classInstanceCreationExpression_lfno_primary")) {
            return false; // expecting diamond operator
        }
        return !nextNode.getText().equals(";");
    }

}
