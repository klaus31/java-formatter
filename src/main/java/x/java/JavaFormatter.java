package x.java;

import x.format.*;
import x.java.snippets.*;

class JavaFormatter implements Formatter {

    private CompilationUnit compilationUnit = new CompilationUnit();

    JavaFormatter() {
    }

    void add(NodeWrapper node) {
        if(!node.isEOF())
        compilationUnit.add(node);
    }

    @Override
    public FormattedSourceCode getFormattedSourceCode() {
        FormattedSourceCode formattedSourceCode = new FormattedSourceCode();
        formattedSourceCode.add(compilationUnit);
        return formattedSourceCode;
    }



    public void enterRule(JavaRulePath javaRulePath) {
        compilationUnit.enterRule(javaRulePath);
    }

    public void exitRule(JavaRulePath javaRulePath) {
    }
}
