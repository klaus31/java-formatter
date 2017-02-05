package x.format;

import x.ctrl.SourceCodeFile;

public interface CodeSnippet {

    String toSourceString(SourceCodeFile file);
}