package x.java;

import org.apache.commons.lang3.StringUtils;

public class IndentService {

    private final String singleIndent;
    private int indent;

    IndentService(String singleIndent) {
        indent = 0;
        this.singleIndent = singleIndent;
    }

    public String getCurrentIndent() {
        return StringUtils.repeat(singleIndent, indent);
    }

    public String calculateIndentToAppendTo(NodeWrapper node) {
        if (isIndentPlusTwoNeeded(node)) {
            return indentPlusTwo();
        } else if (isIndentPlusOneNeeded(node)) {
            return indentPlusOne();
        } else if (isIndentMinusTwoNeeded(node)) {
            return indentMinusTwo();
        } else if (isIndentMinusOneNeeded(node)) {
            return indentMinusOne();
        } else {
            return getCurrentIndent();
        }
    }

    private boolean isIndentPlusTwoNeeded(NodeWrapper node) {
        return false;
    }

    private boolean isIndentMinusTwoNeeded(NodeWrapper node) {
        return node.isLastNodeInSwitchStatement();
    }

    private boolean isIndentMinusOneNeeded(NodeWrapper node) {
        if (node.isNextNodeText("}") && !node.isBlockStart()) {
            return true;
        }
        if (!node.isDoublePointInSwitchStatement() && !node.isCurrentRuleA("methodBody") && node.isNextNodeTextOneOf("default", "case")) {
            return true;
        }
        return false;
    }

    private boolean isIndentPlusOneNeeded(NodeWrapper node) {
        if (node.isBlockStart() && !node.isNextNodeText("}")) {
            return true;
        }
        if (node.isDoublePointInSwitchStatement() && !node.isNextNodeTextOneOf("default", "case")) {
            return true;
        }
        return false;
    }

    private String indentPlusOne() {
        return StringUtils.repeat(singleIndent, ++indent);
    }

    private String indentPlusTwo() {
        return StringUtils.repeat(singleIndent, indent += 2);
    }

    private String indentMinusOne() {
        return StringUtils.repeat(singleIndent, --indent);
    }

    private String indentMinusTwo() {
        return StringUtils.repeat(singleIndent, indent -= 2);
    }
}