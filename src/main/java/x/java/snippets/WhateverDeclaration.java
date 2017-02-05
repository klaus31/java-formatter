package x.java.snippets;

import x.ctrl.SourceCodeFile;
import x.java.IndentService;
import x.java.NodeWrapper;
import java.util.List;
import static x.java.JavaConfig.EOL;
import static x.java.JavaConfig.getIndentService;

public class WhateverDeclaration extends SimpleNodesJavaCodeSnippet {

    @Override
    protected String afterSnippet(List<NodeWrapper> nodesInSnippet, List<NodeWrapper> allNodesInCompilationUnit, IndentService indentService) {
        NodeWrapper lastNodeInSnippet = nodesInSnippet.get(nodesInSnippet.size() - 1);
        if (lastNodeInSnippet.isNextNodeACommentInSameLine()) {
            return "";
        }
        int indexOfLastNode = allNodesInCompilationUnit.indexOf(lastNodeInSnippet);
        NodeWrapper nextNode = allNodesInCompilationUnit.get(indexOfLastNode + 1);
        return "}".equals(nextNode.getText()) ? "" : EOL + indentService.getCurrentIndent();
    }

    @Override
    protected String toSourceString(NodeWrapper node, SourceCodeFile file) {
        StringBuilder builder = new StringBuilder();
        builder.append(node.getText());
        if ("@".equals(node.getText()) && node.getJavaRulePath().isCurrentRuleA("annotationTypeDeclaration")) {
            return builder.toString();
        }
        if (requiresSingleEolAfterNode(node)) {
            builder.append(EOL);
            builder.append(getIndentService(file).calculateIndentToAppendTo(node));
        } else if (requiresSingleBlankAfterNode(node)) {
            builder.append(" ");
        }
        return builder.toString();
    }

    private boolean requiresSingleBlankAfterNode(NodeWrapper node) {
        if (node.isNodeTextAnyOf("-", "+")) {
            return node.isCurrentRuleA("additiveExpression");
        }
        if (node.isNextNodeText("}")) {
            return false;
        }
        if (node.isNextNodeACommentInSameLine()) {
            return true;
        }
        if (requiresSingleEolAfterNode(node)) {
            return false;
        }
        if (node.isSemicolonInBasicForStatement()) {
            return true;
        }
        if (node.isNodeTextAnyOf(";", "::", "(", ".", "++", "--")) {
            return false;
        }
        if (node.isNodeTextAnyOf("new", ",", "=")) {
            return true;
        }
        if (node.isBlockStart() && node.matchesRulePath("arrayInitializer")) {
            return false;
        }
        if (node.isNextNodeText("(")) {
            return node.isNodeTextAnyOf("catch", "switch", "if", "for", "while", "+", "-", "*", "/", "%", ":");
        }
        if (node.isNextNodeTextOneOf(";", "::", ")", ",", ".", "++", "--", "[", "]", "}")) {
            return false;
        }
        if (node.isDoublePointInEnhancedForStatement()) {
            return true;
        }
        if (node.isNextADoublePointInEnhancedForStatement()) {
            return true;
        }
        if (node.isDoublePointInLabeledStatement()) {
            return true;
        }
        if (node.isNextADoublePointInLabeledStatement()) {
            return false;
        }
        if (node.isDoublePointInSwitchStatement()) {
            return true;
        }
        if (node.isNextADoublePointInSwitchStatement(getAllNodesInCompilationUnit())) {
            return false;
        }
        if (")".equals(node.getText())) {
            return !node.isNextNodeText(".");
        }
        if (node.isNodeText("!")) {
            return false;
        }
        if (node.isInGenericTypeDeclaration()) {
            if (node.isNextNodeTextOneOf("<", ",", ">")) {
                return false;
            } else if (node.getText().equals(",")) {
                return true;
            } else if (node.getText().equals("<")) {
                return false;
            } else {
                return true;
            }
        }
        return true;
    }

    private boolean requiresSingleEolAfterNode(NodeWrapper node) {
        if (node.isNextNodeACommentInSameLine()) {
            return false;
        }
        if (node.isBlockEnd() && node.isNextNodeElseCatchOrWhile() || node.isNextNodeText(";") || node.isNextNodeText(")")) {
            return false;
        }
        if (node.isBlockStart() && node.matchesRulePath("arrayInitializer")) {
            return false;
        }
        if (node.isBlockStartOrEnd() || node.isSemicolonAtEnd() || node.isDoublePointInSwitchStatement()) {
            return true;
        }
        return false;
    }
}