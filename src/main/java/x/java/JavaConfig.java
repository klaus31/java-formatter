package x.java;

import x.java.snippets.*;

import java.util.Optional;

public class JavaConfig {
    public static final String EOL = "\n";
    private static IndentService indentService;

    private JavaConfig() {
    }

    public static IndentService getIndentService() {
        if (indentService == null) {
            indentService = new IndentService("    ");
        }
        return indentService;
    }


    static JavaCodeSnippet createCompilationUnit() {
        return new CompilationUnit();
    }

    private static JavaCodeSnippet createPackageDeclaration() {
        return new PackageDeclaration();
    }

    private static JavaCodeSnippet createImportDeclaration() {
        return new ImportDeclaration();
    }

    private static JavaCodeSnippet createAnnotation() {
        return new Annotation();
    }

    private static JavaCodeSnippet createInterfaceMethodDeclaration() {
        return createMethodBody();
    }

    private static JavaCodeSnippet createMethodDeclarator() {
        return createMethodBody();
    }

    private static JavaCodeSnippet createMethodDeclaration() {
        return createMethodBody();
    }

    private static JavaCodeSnippet createMethodBody() {
        return new MethodBody();
    }

    private static JavaCodeSnippet createMethodModifier() {
        return createMethodDeclarator();
    }

    public static SimpleNodesJavaCodeSnippet createComment() {
        return new Comment();
    }

    public static Optional<JavaCodeSnippet> getMatchingCodeSnippetFor(JavaRulePath rulePath) {
        if (rulePath.isCurrentRuleA("annotation")) {
            return Optional.of(createAnnotation());
        } else if (rulePath.isCurrentRuleA("methodDeclaration")) {
            return Optional.of(createMethodDeclaration());
        } else if (rulePath.isCurrentRuleA("methodBody")) {
            return Optional.of(createMethodBody());
        } else if (rulePath.isCurrentRuleA("methodModifier")) {
            return Optional.of(createMethodModifier());
        } else if (rulePath.isCurrentRuleA("interfaceMethodDeclaration")) {
            return Optional.of(createInterfaceMethodDeclaration());
        } else if (rulePath.isCurrentRuleA("methodDeclarator")) {
            return Optional.of(createMethodDeclarator());
        } else if (rulePath.isCurrentRuleA("packageDeclaration")) {
            return Optional.of(createPackageDeclaration());
        } else if (rulePath.isCurrentRuleA("importDeclaration")) {
            return Optional.of(createImportDeclaration());
        }else if (rulePath.isCurrentRuleA("fieldDeclaration")) {
            return Optional.of(createFieldDeclaration());
        }else if (rulePath.isCurrentRuleA("methodDeclaration")) {
            return Optional.of(createMethodDeclaration());
        }else if (rulePath.isCurrentRuleA("interfaceMethodDeclaration")) {
            return Optional.of(createInterfaceMethodDeclaration());
        } else if (rulePath.matchesCurrentRuleAnyOf("classDeclaration", "classModifier", "interfaceDeclaration")) {
            return Optional.of(createClassDeclaration());
        } else {
            return Optional.empty();
        }
    }

    private static JavaCodeSnippet createFieldDeclaration() {
        return new FieldDeclaration();
    }

    private static JavaCodeSnippet createClassDeclaration() {
        return new ClassDeclaration();
    }
}
