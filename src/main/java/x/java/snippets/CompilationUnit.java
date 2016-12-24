package x.java.snippets;

import x.format.CodeSnippet;
import x.java.JavaRulePath;
import x.java.NodeWrapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.joining;

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
