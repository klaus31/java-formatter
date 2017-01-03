package x.java.snippets;

import x.java.IndentService;
import x.java.JavaRulePath;

class InterfaceMethodDeclaration extends DecoratedJavaCodeSnippet {

    @Override
    public void enterRule(JavaRulePath rulePath) {
        if (rulePath.isCurrentRuleA("annotation")) {
            setCurrentCodeSnippet(new Annotation());
        } else if (rulePath.isCurrentRuleA("interfaceMethodDeclaration")) {
            setCurrentCodeSnippet(new MethodBody());
        } else if (rulePath.isCurrentRuleA("methodDeclarator")) {
            setCurrentCodeSnippet(new MethodBody());
        }
        withCurrentSnippetIfPresent(s -> s.enterRule(rulePath));
    }
}
