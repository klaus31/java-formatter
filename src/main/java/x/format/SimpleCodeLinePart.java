package x.format;

import org.apache.commons.lang3.StringUtils;

public class SimpleCodeLinePart implements CodeLinePart {

    private final String linePart;

    public SimpleCodeLinePart(String linePart) {
        this.linePart = linePart;
    }

    public static SimpleCodeLinePart produceWhitespace() {
        return new SimpleCodeLinePart(" ");
    }

    @Override
    public String toSourceString() {
        return linePart;
    }
}
