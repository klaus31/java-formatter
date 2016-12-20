package x.format;

import java.util.Optional;

public class SourceCodeSnippet {
    private final CodeLine currentCodeLine;
    private final CodeLine previousCodeLine;

    public SourceCodeSnippet(CodeLine currentCodeLine, CodeLine previousCodeLine) {
        this.currentCodeLine = currentCodeLine;
        this.previousCodeLine = previousCodeLine;
    }

    public CodeLine getCurrentCodeLine() {
        return currentCodeLine;
    }

    public Optional<CodeLine> getPreviousCodeLine() {
        return Optional.ofNullable(previousCodeLine);
    }
}
