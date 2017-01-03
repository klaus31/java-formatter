package x.java;

import x.format.*;
import x.java.snippets.*;

class JavaFormatter implements Formatter {

    private final CompilationUnit compilationUnit;

    JavaFormatter() {
        compilationUnit = JavaConfig.createCompilationUnit();
    }

    void add(NodeWrapper node) {
        if(!node.isEOF())
        compilationUnit.add(node);
    }

    @Override
    public FormattedSourceCode getFormattedSourceCode() {
        // FIXME calling this multiple time will return different results
        FormattedSourceCode formattedSourceCode = new FormattedSourceCode();
        formattedSourceCode.add(compilationUnit);
        return formattedSourceCode;
    }



    public void enterRule(JavaRulePath javaRulePath) {
        compilationUnit.enterRule(javaRulePath);
    }
}
