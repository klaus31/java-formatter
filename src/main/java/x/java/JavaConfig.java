package x.java;

import x.java.snippets.*;

public class JavaConfig {
    public static final String EOL = "\n";
    private static IndentService indentService;

    private JavaConfig() {
    }

    public static IndentService getIndentService() {
        if(indentService == null) {
            indentService = new IndentService("    ");
        }
        return indentService;
    }


    public static JavaCodeSnippet createCompilationUnit() {
        return new CompilationUnit();
    }

    public static JavaCodeSnippet createPackageDeclaration() {
        return new PackageDeclaration();
    }

    public static JavaCodeSnippet createImportDeclaration() {
        return new ImportDeclaration();
    }

    public static JavaCodeSnippet createTypeDeclaration() {
        return new TypeDeclaration();
    }

    public static JavaCodeSnippet createAnnotation() {
        return new Annotation();
    }

    public static JavaCodeSnippet createInterfaceMethodDeclaration() {
        return createMethodBody();
    }

    public static JavaCodeSnippet createMethodDeclarator() {
        return createMethodBody();
    }

    public static JavaCodeSnippet createMethodDeclaration() {
        return createMethodBody();
    }

    public static JavaCodeSnippet createMethodBody() {
        return new MethodBody();
    }

    public static JavaCodeSnippet createMethodModifier() {
        return createMethodDeclarator();
    }

    public static SimpleNodesJavaCodeSnippet createComment() {
        return new Comment();
    }
}
