package x.java;

import x.format.CodeLine;
import x.format.FormattedSourceCode;
import x.format.Formatter;

class JavaFormatter implements Formatter {

    private final FormattedSourceCode formattedSourceCode;
    private CodeLine currentLine;

    JavaFormatter() {
        formattedSourceCode = new FormattedSourceCode();
        currentLine = new CodeLine();
    }

    void add(NodeWrapper node) {
        currentLine.addPart(node);
        if (node.requiresWhitespace()) {
            currentLine.addWhitspace();
        } else if (node.requiresEOL()) {
            formattedSourceCode.addLine(currentLine);
            currentLine= new CodeLine();
        }
    }

    @Override
    public FormattedSourceCode getFormattedSourceCode() {
        return formattedSourceCode;
    }
}
