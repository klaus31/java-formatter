package x.java.snippets;

import x.java.JavaRulePath;

public class CompilationUnit extends DecoratedJavaCodeSnippet {

    @Override
    public void enterRule(JavaRulePath rulePath) {
        if (rulePath.isCurrentRuleA("packageDeclaration")) {
            setCurrentCodeSnippet(new PackageDeclaration());
        } else if (rulePath.isCurrentRuleA("importDeclaration")) {
            setCurrentCodeSnippet(new ImportDeclaration());
        } else if (rulePath.isCurrentRuleA("typeDeclaration")) {
            setCurrentCodeSnippet(new TypeDeclaration());
        }
        withCurrentSnippetIfPresent(s -> s.enterRule(rulePath));
    }
}
