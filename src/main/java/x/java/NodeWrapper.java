package x.java;

import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.Arrays;

public class NodeWrapper {

    private final RulePath rulePath;
    private final TerminalNode node;

    public NodeWrapper(TerminalNode node, RulePath rulePath) {
        this.rulePath = rulePath;
        this.node = node;
    }

    public TerminalNode getNode() {
        return node;
    }

    // FIXME some rules are part of other rules as well (e.g. localVariableDeclaration is part of a classDeclaration) ...
    // FIXME ... because of the given order of if ... else lines here this is fine ...
    // FIXME ... until we have inner classes.
    // FIXME ... Solution: Write specific methods to be sure that we are really in a line being a ... and use something like xpath
    boolean requiresWhitespace() {
        if (this.isSemicolon()) {
            return rulePath.isCurrentRuleA("basicForStatement");
        } else if (rulePath.isPartOf("packageDeclaration")) {
            return node.getText().equals("package");
        } else if(rulePath.isPartOf("importDeclaration")) {
            return node.getText().equals("import") || node.getText().equals("static");
        } else if(rulePath.isPartOf("annotation")) {
            return rulePath.isCurrentRuleA("elementValuePairList");
        } else if(rulePath.isPartOf("methodInvocation")) {
            return rulePath.isCurrentRuleA("argumentList");
        } else if(rulePath.isPartOf("localVariableDeclaration")) {
            return rulePath.isPartOfAnyOf("expression", "unannType") ||
                    rulePath.isCurrentRuleOneOf(
                    "unannClassType_lfno_unannClassOrInterfaceType",
                    "variableDeclaratorId",
                    "variableDeclarator",
                    "expressionName",
                    "classInstanceCreationExpression_lfno_primary");
        } else if (rulePath.isPartOf("methodDeclaration")) {
            return rulePath.isCurrentRuleA("methodDeclarator") && node.getText().equals(")") ||
                    rulePath.isCurrentRuleOneOf(
                    "methodModifier",
                    "throws_",
                    "classType",
                    "result",
                    "unannClassType_lfno_unannClassOrInterfaceType");
        } else if (rulePath.isPartOf("classDeclaration")) {
            return rulePath.isCurrentRuleOneOf(
                    "unannClassType_lfno_unannClassOrInterfaceType",
                    "classModifier",
                    "classType",
                    "normalClassDeclaration" );
        } else {
            return false;
        }
    }

    boolean requiresEOL() {
        if (isEndOfAnnotation()) {
            return true;
        }
        return Arrays.asList(";", "{", "}").contains(node.getText());
    }

    private boolean isEndOfAnnotation() {
        return isEndOf("annotation");
    }

    private boolean isEndOf(String ruleName) {
        if (rulePath.isPartOf(ruleName)) {
            ParseTree nodeRuleStarts = getParentNodeWhereRuleStarts(ruleName);
            ParseTree lastChild = getVeryLastChildOf(nodeRuleStarts);
            return lastChild.equals((ParseTree) node);
        }
        return false;
    }

    private ParseTree getParentNodeWhereRuleStarts(String ruleName) {
        ParseTree nodeRuleStarts = node;
        int stepsAway = rulePath.stepsAwayFrom(ruleName);
        while (stepsAway-- > 0) {
            nodeRuleStarts = nodeRuleStarts.getParent();
        }
        return nodeRuleStarts;
    }

    private ParseTree getVeryLastChildOf(ParseTree node) {
        if (node.getChildCount() == 0) return node;
        else return getVeryLastChildOf(node.getChild(node.getChildCount() - 1));
    }

    @Override
    public boolean equals(Object obj) {
        return node.equals(obj);
    }

    @Override
    public int hashCode() {
        return node.hashCode();
    }

    public boolean isSemicolon() {
        return node.getSymbol().getType() == 63;
    }
}
