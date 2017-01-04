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

    public static SimpleNodesJavaCodeSnippet createComment() {
        return new Comment();
    }

    public static Optional<JavaCodeSnippet> getMatchingCodeSnippetFor(JavaRulePath rulePath) {
        if (rulePath.isCurrentRuleA("annotation")) {
            return Optional.of(new Annotation());
        } else if (rulePath.isCurrentRuleA("methodDeclaration")) {
            return Optional.of(new MethodBody());
        } else if (rulePath.isCurrentRuleA("methodBody")) {
            return Optional.of(new MethodBody());
        } else if (rulePath.isCurrentRuleA("methodModifier")) {
            return Optional.of(new MethodBody());
        } else if (rulePath.isCurrentRuleA("interfaceMethodDeclaration")) {
            return Optional.of(new MethodBody());
        } else if (rulePath.isCurrentRuleA("methodDeclarator")) {
            return Optional.of(new MethodBody());
        } else if (rulePath.isCurrentRuleA("packageDeclaration")) {
            return Optional.of(new PackageDeclaration());
        } else if (rulePath.isCurrentRuleA("importDeclaration")) {
            return Optional.of(new ImportDeclaration());
        } else if (rulePath.isCurrentRuleA("fieldDeclaration")) {
            return Optional.of(new FieldDeclaration());
        } else if (rulePath.isCurrentRuleA("methodDeclaration")) {
            return Optional.of(new MethodBody());
        } else if (rulePath.isCurrentRuleA("interfaceMethodDeclaration")) {
            return Optional.of(new MethodBody());
        } else if (rulePath.matchesCurrentRuleAnyOf("classDeclaration", "classModifier", "classBody", "interfaceDeclaration")) {
            return Optional.of(new ClassDeclaration());
        } else {
            return Optional.empty();
        }
    }
}
