package x.java;

import x.java.snippets.CompilationUnit;

public class JavaConfig {
    public static final String EOL = "\n";

    private static final IndentService INDENT_SERVICE = new IndentService();

    private JavaConfig(){}
    public static final String INTENT = "    ";

    public static IndentService getIndentService() {
        return INDENT_SERVICE;
    }


    public static CompilationUnit createCompilationUnit() {
        return new CompilationUnit();
    }
}
