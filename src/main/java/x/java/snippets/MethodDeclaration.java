package x.java.snippets;

import x.java.IndentService;
import x.java.JavaRulePath;

public class MethodDeclaration extends DecoratedJavaCodeSnippet {

    @Override
    public void enterRule(JavaRulePath rulePath) {
        if (rulePath.isCurrentRuleA("annotation")) {
            setCurrentCodeSnippet(new Annotation());
        } else if (rulePath.isCurrentRuleA("methodDeclaration") || rulePath.isCurrentRuleA("methodBody")) {
            setCurrentCodeSnippet(new MethodBody());
        } else if (rulePath.isCurrentRuleA("methodModifier")) {
            setCurrentCodeSnippet(new MethodDeclarator());
        }
        withCurrentSnippetIfPresent(s -> s.enterRule(rulePath));
    }
}



