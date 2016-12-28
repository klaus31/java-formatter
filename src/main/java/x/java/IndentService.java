package x.java;

import org.apache.commons.lang3.StringUtils;

public class IndentService {

    private int indent;

    public IndentService() {
        indent = 0;
    }

    public String indentCurrent() {
        return StringUtils.repeat(JavaConfig.INTENT, indent);
    }

    public String calculateIndentAfter(NodeWrapper node) {
        if ("{".equals(node.toSourceString())) {
            return indentPlusOne();
        } else if ("}".equals(node.calculateNext().getText())) {
            return indentMinusOne();
        } else {
            return indentCurrent();
        }
    }

    private String indentPlusOne() {
        return StringUtils.repeat(JavaConfig.INTENT, ++indent);
    }

    private String indentMinusOne() {
        return StringUtils.repeat(JavaConfig.INTENT, --indent);
    }

}
