package x.java.snippets;

import x.java.IndentService;
import x.java.JavaConfig;
import x.java.JavaRulePath;
import x.java.NodeWrapper;

public class TypeDeclaration extends DecoratedJavaCodeSnippet {

    private final IndentService indentService;

    public TypeDeclaration(IndentService indentService) {
        this.indentService=indentService;
    }

    @Override
    public void enterRule(JavaRulePath rulePath) {
            // TODO innerClasses, staticConstructor etc.
        if (rulePath.isCurrentRuleA("annotation") && !rulePath.isPartOf("classBody")) {
            setCurrentCodeSnippet(new Annotation(indentService));
        }else if (rulePath.isCurrentRuleA("fieldDeclaration")) {
            setCurrentCodeSnippet(new FieldDeclaration(indentService));
        }else if (rulePath.isCurrentRuleA("methodDeclaration")) {
            setCurrentCodeSnippet(new MethodDeclaration(indentService));
        } else if (rulePath.matchesCurrentRuleAnyOf("classDeclaration", "classModifier")) {
            setCurrentCodeSnippet(new SimpleNodesJavaCodeSnippet(){

                @Override
                protected String toSourceString(NodeWrapper node) {
                    StringBuilder builder = new StringBuilder();
                    builder.append(node.toSourceString());
                    if (node.isBlockStartOrEnd() || node.isSemicolonAtEnd()) {
                        builder.append(JavaConfig.EOL);
                        builder.append(indentService.calculateIndentAfter(node));
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


