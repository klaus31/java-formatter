package x.java.snippets;

import x.java.JavaConfig;
import x.java.JavaRulePath;

public class MethodDeclaration extends DecoratedJavaCodeSnippet {

    @Override
    public void enterRule(JavaRulePath rulePath) {
        if (rulePath.isCurrentRuleA("annotation")) {
            setCurrentCodeSnippet(JavaConfig.createAnnotation());
        } else if (rulePath.isCurrentRuleA("methodDeclaration")) {
            setCurrentCodeSnippet(JavaConfig.createMethodDeclaration());
        } else if (rulePath.isCurrentRuleA("methodBody")) {
            setCurrentCodeSnippet(JavaConfig.createMethodBody());
        } else if (rulePath.isCurrentRuleA("methodModifier")) {
            setCurrentCodeSnippet(JavaConfig.createMethodModifier());
        }
        withCurrentSnippetIfPresent(s -> s.enterRule(rulePath));
    }
}



