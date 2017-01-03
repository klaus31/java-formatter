package x.java;

import org.apache.commons.lang3.StringUtils;

public class IndentService {

    private int indent;
    private final String singleIndent;

    IndentService(String singleIndent) {
        indent = 0;
        this.singleIndent = singleIndent;
    }

    public String getCurrentIndent() {
        return StringUtils.repeat(singleIndent, indent);
    }

    public String calculateIndentToAppendTo(NodeWrapper node) {
        if ("{".equals(node.toSourceString())) {
            return indentPlusOne();
        } else if ("}".equals(node.calculateNext().getText())) {
            return indentMinusOne();
        } else {
            return getCurrentIndent();
        }
    }

    private String indentPlusOne() {
        return StringUtils.repeat(singleIndent, ++indent);
    }

    private String indentMinusOne() {
        return StringUtils.repeat(singleIndent, --indent);
    }

}
