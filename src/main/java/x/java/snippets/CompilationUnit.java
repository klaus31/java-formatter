package x.java.snippets;

import x.java.JavaRulePath;

import static x.java.JavaConfig.*;

public class CompilationUnit extends DecoratedJavaCodeSnippet {

    @Override
    public void enterRule(JavaRulePath rulePath) {
        if (rulePath.isCurrentRuleA("packageDeclaration")) {
            setCurrentCodeSnippet(createPackageDeclaration());
        } else if (rulePath.isCurrentRuleA("importDeclaration")) {
            setCurrentCodeSnippet(createImportDeclaration());
        } else if (rulePath.isCurrentRuleA("typeDeclaration")) {
            setCurrentCodeSnippet(createTypeDeclaration());
        }
        withCurrentSnippetIfPresent(s -> s.enterRule(rulePath));
    }
}
