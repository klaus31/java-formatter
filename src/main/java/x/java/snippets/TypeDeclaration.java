package x.java.snippets;

import x.java.IndentService;
import x.java.JavaConfig;
import x.java.JavaRulePath;
import x.java.NodeWrapper;

import static x.java.JavaConfig.EOL;
import static x.java.JavaConfig.getIndentService;

public class TypeDeclaration extends DecoratedJavaCodeSnippet {

    @Override
    public void enterRule(JavaRulePath rulePath) {
            // TODO innerClasses, staticConstructor etc.
        if (rulePath.isCurrentRuleA("annotation") && !rulePath.isPartOf("classBody")) {
            setCurrentCodeSnippet(new Annotation());
        }else if (rulePath.isCurrentRuleA("fieldDeclaration")) {
            setCurrentCodeSnippet(new FieldDeclaration());
        }else if (rulePath.isCurrentRuleA("methodDeclaration")) {
            setCurrentCodeSnippet(new MethodDeclaration());
        }else if (rulePath.isCurrentRuleA("interfaceMethodDeclaration")) {
            setCurrentCodeSnippet(new InterfaceMethodDeclaration());
        } else if (rulePath.matchesCurrentRuleAnyOf("classDeclaration", "classModifier", "interfaceDeclaration")) {
            setCurrentCodeSnippet(new SimpleNodesJavaCodeSnippet(){

                @Override
                protected String toSourceString(NodeWrapper node) {
                    StringBuilder builder = new StringBuilder();
                    builder.append(node.toSourceString());
                    if (node.isBlockStartOrEnd() || node.isSemicolonAtEnd()) {
                        builder.append(EOL);
                        builder.append(getIndentService().calculateIndentToAppendTo(node));
                    } else {
                        builder.append(" ");
                    }
                    return builder.toString();
                }
            });
        }
        withCurrentSnippetIfPresent(s -> s.enterRule(rulePath));
    }
}


