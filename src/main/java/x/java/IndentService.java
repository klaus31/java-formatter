package x.java;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;

import java.util.Arrays;
import java.util.List;

public class IndentService {

    public static final List<String> CALCULATABLE_LINE_ENDINGS = Arrays.asList("{", "}", ":", ";");
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
        Validate.isTrue(CALCULATABLE_LINE_ENDINGS.contains(node.toSourceString()));
        if (node.isBlockStart() && !node.isNextNodeText("}")) {
            return indentPlusOne();
        } else if (node.isNextNodeText("}") && !node.isBlockStart()) {
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
