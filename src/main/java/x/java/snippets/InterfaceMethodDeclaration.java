package x.java.snippets;

import x.java.JavaConfig;
import x.java.JavaRulePath;

import static x.java.JavaConfig.createAnnotation;
import static x.java.JavaConfig.createInterfaceMethodDeclaration;
import static x.java.JavaConfig.createMethodDeclarator;

class InterfaceMethodDeclaration extends DecoratedJavaCodeSnippet {

    @Override
    public void enterRule(JavaRulePath rulePath) {
        if (rulePath.isCurrentRuleA("annotation")) {
            setCurrentCodeSnippet(createAnnotation());
        } else if (rulePath.isCurrentRuleA("interfaceMethodDeclaration")) {
            setCurrentCodeSnippet(createInterfaceMethodDeclaration());
        } else if (rulePath.isCurrentRuleA("methodDeclarator")) {
            setCurrentCodeSnippet(createMethodDeclarator());
        }
        withCurrentSnippetIfPresent(s -> s.enterRule(rulePath));
    }
}
