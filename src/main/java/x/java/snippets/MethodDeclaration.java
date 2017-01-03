package x.java.snippets;

import x.java.IndentService;
import x.java.JavaRulePath;

public class MethodDeclaration extends DecoratedJavaCodeSnippet {
    private final IndentService indentService;

    public MethodDeclaration(IndentService indentService) {
        this.indentService = indentService;
    }

    @Override
    public void enterRule(JavaRulePath rulePath) {
        if (rulePath.isCurrentRuleA("annotation")) {
            setCurrentCodeSnippet(new Annotation(indentService));
        } else if (rulePath.isCurrentRuleA("methodDeclaration") || rulePath.isCurrentRuleA("methodBody")) {
            setCurrentCodeSnippet(new MethodBody(indentService));
        } else if (rulePath.isCurrentRuleA("methodModifier")) {
            setCurrentCodeSnippet(new MethodDeclarator(indentService));
        }
        withCurrentSnippetIfPresent(s -> s.enterRule(rulePath));
    }
}



