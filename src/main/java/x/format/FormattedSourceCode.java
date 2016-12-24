package x.format;

import x.java.JavaConfig;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

public class FormattedSourceCode {

    private final List<CodeSnippet> snippets;

    private Map<Object, Object> code;

    public FormattedSourceCode() {
        this.snippets = new ArrayList<>();
    }

    public void add(CodeSnippet snippet) {
        snippets.add(snippet);
    }

    public List<String> getCode(String eol) {
        String aVeryLongString = snippets.stream().map(CodeSnippet::toSourceString).collect(joining());
        return Arrays.asList(aVeryLongString.split(eol));
    }
}
