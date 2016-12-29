package x.java.snippets;

import x.java.IndentService;
import x.java.JavaRulePath;

public class CompilationUnit extends DecoratedJavaCodeSnippet {

    private final IndentService indentService;

    public CompilationUnit(IndentService indentService) {
        this.indentService = indentService;
    }

    @Override
    public void enterRule(JavaRulePath rulePath) {
        if (rulePath.isCurrentRuleA("packageDeclaration")) {
            setCurrentCodeSnippet(new PackageDeclaration());
        } else if (rulePath.isCurrentRuleA("importDeclaration")) {
            setCurrentCodeSnippet(new ImportDeclaration());
        } else if (rulePath.isCurrentRuleA("comment") && !rulePath.matches("importDeclaration")) {
            setCurrentCodeSnippet(new Comment());
        } else if (rulePath.isCurrentRuleA("typeDeclaration")) {
            setCurrentCodeSnippet(new TypeDeclaration(indentService));
        }
        withCurrentSnippetIfPresent(s -> s.enterRule(rulePath));
    }
}
