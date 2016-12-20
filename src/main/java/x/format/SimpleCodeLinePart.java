package x.format;

import org.apache.commons.lang3.StringUtils;

public class SimpleCodeLinePart implements CodeLinePart {

    private final String linePart;

    private SimpleCodeLinePart(String linePart) {
        this.linePart = linePart;
    }

    public static SimpleCodeLinePart produceWhitespace() {
        return new SimpleCodeLinePart(" ");
    }

    public static SimpleCodeLinePart produceTab(int length) {
        return new SimpleCodeLinePart(StringUtils.repeat(' ', length));
    }

    @Override
    public String toSourceString() {
        return linePart;
    }
}
