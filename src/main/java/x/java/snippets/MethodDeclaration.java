package x.java.snippets;

import x.java.JavaConfig;
import x.java.JavaRulePath;

import static x.java.JavaConfig.*;

public class MethodDeclaration extends DecoratedJavaCodeSnippet {

    @Override
    public void enterRule(JavaRulePath rulePath) {
        if (rulePath.isCurrentRuleA("annotation")) {
            setCurrentCodeSnippet(createAnnotation());
        } else if (rulePath.isCurrentRuleA("methodDeclaration")) {
            setCurrentCodeSnippet(createMethodDeclaration());
        } else if (rulePath.isCurrentRuleA("methodBody")) {
            setCurrentCodeSnippet(createMethodBody());
        } else if (rulePath.isCurrentRuleA("methodModifier")) {
            setCurrentCodeSnippet(createMethodModifier());
        }
        withCurrentSnippetIfPresent(s -> s.enterRule(rulePath));
    }
}



