package x.java;

import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.apache.commons.lang3.StringUtils;
import x.format.RulePath;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Stream;
import static java.util.Arrays.asList;
public class NodeWrapper {

    private final JavaRulePath javaRulePath;
    private final TerminalNode node;
    private ParseTree nextNode;


    public boolean isNextNodeAComment() {
        String nextNodeText = calculateNext().getText();
        return nextNodeText.replaceAll("\\r?\\n", "").matches("/\\*.*") || nextNodeText.matches("//.*");
    }

    public JavaRulePath getJavaRulePath() {
        return javaRulePath;
    }

    public NodeWrapper(TerminalNode node, JavaRulePath javaRulePath) {
        this.javaRulePath = javaRulePath;
        this.node = node;
    }

    private boolean occursOnSameLineAs(TerminalNode otherNode) {
        return node.getSymbol().getLine() == otherNode.getSymbol().getLine();
    }

    public boolean occursOnSameLineAs(ParseTree otherNode) {
        try {
            return occursOnSameLineAs((TerminalNode) otherNode);
        } catch (ClassCastException e) {
            e.printStackTrace(); // FIXME die, evil, die
            return false;
        }
    }

    ParseTree calculateNext() {
        if (nextNode == null) {
            int nodeIndex = getNodeIndex();
            ParseTree parent = node.getParent();
            if (nodeIndex == parent.getChildCount() - 1) {

                // last child at this level
                while (parent != null && isLastNodeInLevel(parent)) {
                    parent = parent.getParent();
                }
                if (parent == null) {
                    throw new AssertionError("Should never been called on <EOF> node");
                } else {
                    ParseTree candidate = parent.getParent().getChild(getNodeIndex(parent) + 1);
                    nextNode = getVeryFirstLeafOf(candidate);
                }
            } else {
                nextNode = getVeryFirstLeafOf(parent.getChild(nodeIndex + 1));
            }
        }
        return nextNode;
    }

    private ParseTree getVeryFirstLeafOf(ParseTree node) {
        if (node.getChildCount() == 0) return node;
        else return getVeryFirstLeafOf(node.getChild(0));
    }

    private boolean isLastNodeInLevel(ParseTree node) {
        return isRoot(node) || getNodeIndex(node) == node.getParent().getChildCount() - 1;
    }

    private boolean isRoot(ParseTree node) {
        return node.getParent() == null;
    }

    private int getNodeIndex() {
        return getNodeIndex(node);
    }

    private int getNodeIndex(ParseTree node) {
        if (node == null || isRoot(node)) {
            return -1;
        }
        ParseTree parent = node.getParent();
        for (int i = 0; i < parent.getChildCount(); i++) {
            if (parent.getChild(i) == node) {
                return i;
            }
        }
        return -1;
    }

    private Optional<ParseTree> getRightSibling(ParseTree node) {
        int index = getNodeIndex();
        ParseTree parent = node.getParent();
        if (index < 0 || index >= parent.getChildCount() - 1) {
            return Optional.empty();
        }
        return Optional.of(parent.getChild(index + 1));
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

    private boolean isEndOfAnnotation() {
        return isEndOf("annotation");
    }

    public boolean isEndOf(String ruleName) {
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
        while (stepsAway--> 0) {
            nodeRuleStarts = nodeRuleStarts.getParent();
        }
        return nodeRuleStarts;
    }

    private ParseTree getVeryLastChildOf(ParseTree node) {
        if (node.getChildCount() == 0) return node;
        else return getVeryLastChildOf(node.getChild(node.getChildCount() - 1));
    }

    public boolean isSemicolonAtEnd() {
        return node.getSymbol().getType() == 63 && !isCurrentRuleA("basicForStatement");
    }

    public boolean isSemicolonInBasicForStatement() {
        return node.getSymbol().getType() == 63 && isCurrentRuleA("basicForStatement");
    }

    public String getText() {
        return node.getText();
    }

    public boolean isEOF() {
        return node.getSymbol().getType() == Token.EOF;
    }

    public boolean matchesRulePath(Predicate<RulePath> predicate) {
        return predicate.test(javaRulePath);
    }

    public boolean matchesRulePath(String ... ruleNames) {
        return javaRulePath.matches(ruleNames);
    }

    public boolean isNextADoublePointInEnhancedForStatement() {
        return isNextADoublePoint() && javaRulePath.ruleNameFromEndEquals(1, "enhancedForStatement");
    }

    public boolean isNextADoublePointInSwitchStatement(List<NodeWrapper> allNodesInCompilationUnit) {
        return isNextADoublePoint() &&(isCurrentRuleA("switchLabel") || prevNodeMatchesRulePath(jrp -> jrp.isCurrentRuleA("switchLabel"), allNodesInCompilationUnit));
    }

    public boolean isNextADoublePointInLabeledStatement() {
        return isNextADoublePoint() && isCurrentRuleA("labeledStatement");
    }

    public boolean isDoublePointInEnhancedForStatement() {
        return isDoublePoint() && isCurrentRuleA("enhancedForStatement");
    }

    public boolean isDoublePointInSwitchStatement() {
        return isDoublePoint() && isCurrentRuleA("switchLabel");
    }

    public boolean isCurrentRuleA(String ruleName) {
        return javaRulePath.isCurrentRuleA(ruleName);
    }

    public boolean isDoublePointInLabeledStatement() {
        return isDoublePoint() && isCurrentRuleA("labeledStatement");
    }

    private boolean isNextADoublePoint() {
        return ":".equals(calculateNext().getText());
    }

    private boolean isDoublePoint() {
        return ":".equals(node.getText());
    }

    @Override
    public String toString() {
        return StringUtils.rightPad(getText(), 20) + ": " + javaRulePath.toString();
    }

    public boolean isBlockStartOrEnd() {
        return asList("}", "{").contains(getText());
    }

    public boolean isBlockEnd() {
        return "}".equals(getText());
    }

    public boolean isBlockStart() {
        return "{".equals(getText());
    }

    public boolean isNextNodeACommentInSameLine() {
        return isNextNodeAComment() && occursOnSameLineAs(calculateNext());
    }

    public boolean isNextNodeElseCatchOrWhile() {
        return asList("else", "catch", "while").contains(calculateNext().getText());
    }

    public boolean isNextNodeText(String string) {
        return string.equals(calculateNext().getText());
    }

    public boolean isInGenericTypeDeclaration() {
        return matchesRulePath("unannClassType_lfno_unannClassOrInterfaceType") || matchesRulePath("classInstanceCreationExpression_lfno_primary") || matchesRulePath("typeArguments") || javaRulePath.isCurrentRuleA("classType") && calculateNext().getText().equals("<");
    }

    public boolean isNextNodeTextOneOf(String ... ruleNames) {
        return Stream.of(ruleNames).anyMatch(this::isNextNodeText);
    }

    public boolean isNodeText(String text) {
        return text.equals(getText());
    }

    public boolean isNodeTextAnyOf(String ... texts) {
        return Stream.of(texts).anyMatch(this::isNodeText);
    }

    boolean isLastNodeInSwitchStatement() {
        return isNextNodeText("}") && javaRulePath.ruleNameFromEndEquals(5, "switchBlockStatementGroup");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NodeWrapper that = (NodeWrapper) o;
        if (!node.equals(that.node)) return false;
        return true;
    }

    @Override
    public int hashCode() {
        return node.hashCode();
    }

    public NodeWrapper calculateNextNode(List<NodeWrapper> allNodesInCompilationUnit) {
        return allNodesInCompilationUnit.get(allNodesInCompilationUnit.indexOf(this) + 1);
    }

    private NodeWrapper calculatePrevNode(List<NodeWrapper> allNodesInCompilationUnit) {
        return allNodesInCompilationUnit.get(allNodesInCompilationUnit.indexOf(this) - 1);
    }

    private boolean prevNodeMatchesRulePath(Predicate<RulePath> predicate, List<NodeWrapper> allNodesInCompilationUnit) {
        NodeWrapper prevNode = calculatePrevNode(allNodesInCompilationUnit);
        return prevNode != null && predicate.test(prevNode.javaRulePath);
    }
}