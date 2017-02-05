package x.format;

import x.ctrl.SourceCodeFile;
import x.java.snippets.CompilationUnit;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import static java.util.stream.Collectors.joining;
public class FormattedSourceCode {

    private final CompilationUnit compilationUnit;

    public FormattedSourceCode(CompilationUnit compilationUnit) {
        this.compilationUnit = compilationUnit;
    }

    public List<String> getCode(String eol, SourceCodeFile file) {
        return Arrays.asList(compilationUnit.toSourceString(file).split(eol));
    }
}