package x.java.snippets;

import x.java.NodeWrapper;
import static java.util.Arrays.asList;
import static x.java.JavaConfig.EOL;
import static x.java.JavaConfig.getIndentService;
public class WhateverDeclaration extends SimpleNodesJavaCodeSnippet {
    @Override
    protected String toSourceString(NodeWrapper node) {
        StringBuilder builder = new StringBuilder();
        builder.append(node.getText());
        if (requiresSingleEolAfterNode(node)) {
            builder.append(EOL);
            builder.append(getIndentService().calculateIndentToAppendTo(node));
        } else if (requiresSingleBlankAfterNode(node)) {
            builder.append(" ");
        }
        return builder.toString();
    }
    private boolean requiresSingleBlankAfterNode(NodeWrapper node) {
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
        if (asList(";", "::", "(", ".", "++", "--").contains(node.getText())) {
            return false;
        }
        if (asList("new", ",", "=").contains(node.getText())) {
            return true;
        }
        if (node.isBlockStart() && node.matchesRulePath("arrayInitializer")) {
            return false;
        }
        if(node.isNextNodeText("(")) {
            return asList("catch", "switch", "if", "for", "while", "+", "-", "*", "/", "%", ":").contains(node.getText());
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
        if (node.isNextADoublePointInSwitchStatement()) {
            return false;
        }
        if (")".equals(node.getText())) {
            return ! node.isNextNodeText(".");
        }
        if(node.isNodeText("!")) {
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