package x.java.snippets;

import x.format.CodeSnippet;
import x.java.JavaConfig;
import x.java.JavaRulePath;
import x.java.NodeWrapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.joining;

public class TypeDeclaration extends DecoratedJavaCodeSnippet {

    @Override
    public void enterRule(JavaRulePath rulePath) {
        if (rulePath.isCurrentRuleA("annotation") && !rulePath.isPartOf("classBody")) {
            setCurrentCodeSnippet(new Annotation());
        // TODO grep fieldDeclaration, methodDeclaration, innerClasses, staticConstructor etc.
        } else if (rulePath.matchesCurrentRuleAnyOf("classDeclaration", "classModifier")) {
            setCurrentCodeSnippet(new SimpleNodesJavaCodeSnippet(){

                @Override
                protected String toSourceString(NodeWrapper node) {
                    StringBuilder builder = new StringBuilder();
                    builder.append(node.toSourceString());
                    if(Arrays.asList("}","{",";").contains(node.toSourceString())) {
                        builder.append(JavaConfig.EOL);
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


