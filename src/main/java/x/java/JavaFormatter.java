package x.java;

import x.format.CodeLine;
import x.format.FormattedSourceCode;
import x.format.Formatter;
import x.format.SourceCodeSnippet;

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
            currentLine = new CodeLine();
        } else if (node.isEOF()) {
            formattedSourceCode.calculateIndent(this::calculateIndentChange);
        }
    }

    private Integer calculateIndentChange(SourceCodeSnippet snippet) {
        int result = 0;
        if (snippet.getPreviousCodeLine().isPresent() && snippet.getPreviousCodeLine().get().getLastElement().toSourceString().equals("{")) {
            result++;
        }
        if (snippet.getCurrentCodeLine().getFirstElement().toSourceString().equals("}")) {
            result--;
        }
        return result;
    }

    @Override
    public FormattedSourceCode getFormattedSourceCode() {
        return formattedSourceCode;
    }
}
