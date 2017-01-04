package x.java.snippets;

import x.java.JavaRulePath;

import static x.java.JavaConfig.getMatchingCodeSnippetFor;

public class CompilationUnit extends DecoratedJavaCodeSnippet {

    @Override
    public void enterRule(JavaRulePath rulePath) {
        getMatchingCodeSnippetFor(rulePath).ifPresent(this::setCurrentCodeSnippet);
        withCurrentSnippetIfPresent(s -> s.enterRule(rulePath));
    }
}
