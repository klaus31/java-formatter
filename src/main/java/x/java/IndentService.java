package x.java;

import org.apache.commons.lang3.StringUtils;

public class IndentService {

    private int indent;

    IndentService() {
        indent = 0;
    }

    public String getCurrentIndent() {
        return StringUtils.repeat(JavaConfig.INTENT, indent);
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
        return StringUtils.repeat(JavaConfig.INTENT, ++indent);
    }

    private String indentMinusOne() {
        return StringUtils.repeat(JavaConfig.INTENT, --indent);
    }

}
