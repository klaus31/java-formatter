package x.java;

import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;
import x.format.CodeLinePart;

import java.util.Arrays;

public class NodeWrapper implements CodeLinePart {

    private final JavaRulePath javaRulePath;
    private final TerminalNode node;

    public NodeWrapper(TerminalNode node, JavaRulePath javaRulePath) {
        this.javaRulePath = javaRulePath;
        this.node = node;
    }

    // FIXME some rules are part of other rules as well (e.g. localVariableDeclaration is part of a classDeclaration) ...
    // FIXME ... because of the given order of if ... else lines here this is fine ...
    // FIXME ... until we have inner classes.
    // FIXME ... Solution: Write specific methods to be sure that we are really in a line being a ... and use something like xpath
    boolean requiresWhitespace() {
        if (isSemicolon()) {
            return javaRulePath.isInsideForStatement();
        } else if (javaRulePath.isPackageDeclaration()) {
            return isWordPackage();
        } else if (javaRulePath.isImportDeclaration()) {
            return isWordImport() || isWordStatic();
        } else if (javaRulePath.isAnnotation()) {
            return javaRulePath.isCurrentRuleA("elementValuePairList");
        } else if (javaRulePath.isMethodInvocation()) {
            return javaRulePath.isCurrentRuleA("argumentList");
        } else if (javaRulePath.isLocalVariableDeclaration()) {
            return javaRulePath.isPartOfAnyOf("expression", "unannType") ||
                    javaRulePath.isCurrentRuleOneOf(
                            "unannClassType_lfno_unannClassOrInterfaceType",
                            "variableDeclaratorId",
                            "variableDeclarator",
                            "expressionName",
                            "classInstanceCreationExpression_lfno_primary");
        } else if (javaRulePath.isMethodDeclaration()) {
            return javaRulePath.isCurrentRuleA("methodDeclarator") && node.getText().equals(")") ||
                    javaRulePath.isCurrentRuleOneOf(
                            "methodModifier",
                            "throws_",
                            "classType",
                            "result",
                            "unannClassType_lfno_unannClassOrInterfaceType");
        } else if (javaRulePath.isClassDeclaration()) {
            return javaRulePath.isCurrentRuleOneOf(
                    "unannClassType_lfno_unannClassOrInterfaceType",
                    "classModifier",
                    "classType",
                    "normalClassDeclaration");
        } else {
            return false;
        }
    }

    private boolean isWordStatic() {
        return node.getText().equals("static");
    }

    private boolean isWordImport() {
        return node.getText().equals("import");
    }

    private boolean isWordPackage() {
        return node.getText().equals("package");
    }

    boolean requiresEOL() {
        if (isEndOfAnnotation()) {
            return true;
        }
        return isSemicolon() && !javaRulePath.isInsideForStatement() ||
                Arrays.asList("{", "}").contains(node.getText());
    }

    private boolean isEndOfAnnotation() {
        return isEndOf("annotation");
    }

    private boolean isEndOf(String ruleName) {
        if (javaRulePath.isPartOf(ruleName)) {
            ParseTree nodeRuleStarts = getParentNodeWhereRuleStarts(ruleName);
            ParseTree lastChild = getVeryLastChildOf(nodeRuleStarts);
            return lastChild.equals(node);
        }
        return false;
    }

    private ParseTree getParentNodeWhereRuleStarts(String ruleName) {
        ParseTree nodeRuleStarts = node;
        int stepsAway = javaRulePath.stepsAwayFrom(ruleName);
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

    @Override
    public String toSourceString() {
        return node.getText();
    }

    public boolean isEOF() {
        return node.getSymbol().getType() == Token.EOF;
    }
}
