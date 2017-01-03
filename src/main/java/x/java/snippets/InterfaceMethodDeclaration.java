package x.java.snippets;

import x.java.IndentService;
import x.java.JavaConfig;
import x.java.JavaRulePath;
import x.java.NodeWrapper;

class InterfaceMethodDeclaration extends DecoratedJavaCodeSnippet {
    private final IndentService indentService;

    public InterfaceMethodDeclaration(IndentService indentService) {
        this.indentService = indentService;
    }

    @Override
    public void enterRule(JavaRulePath rulePath) {
        if (rulePath.isCurrentRuleA("annotation")) {
            setCurrentCodeSnippet(new Annotation(indentService));
        } else if (rulePath.isCurrentRuleA("interfaceMethodDeclaration")) {
            setCurrentCodeSnippet(new MethodBody(indentService));
        } else if (rulePath.isCurrentRuleA("methodDeclarator")) {
            setCurrentCodeSnippet(new MethodBody(indentService));
        }
        withCurrentSnippetIfPresent(s -> s.enterRule(rulePath));
    }
}
