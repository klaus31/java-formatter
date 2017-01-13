package x.format;

import x.ctrl.SourceCodeFile;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import static java.util.stream.Collectors.joining;
public class FormattedSourceCode {
    private final List<CodeSnippet> snippets;
    private Map<Object, Object> code;
    public FormattedSourceCode() {
        this.snippets = new ArrayList<>();
    }
    public void add(CodeSnippet snippet) {
        snippets.add(snippet);
    }
    public List<String> getCode(String eol, SourceCodeFile file) {
        String aVeryLongString = snippets.stream().map((codeSnippet) -> codeSnippet.toSourceString(file)).collect(joining());
        return Arrays.asList(aVeryLongString.split(eol));
    }
}