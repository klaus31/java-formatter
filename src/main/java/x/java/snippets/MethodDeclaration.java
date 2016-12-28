package x.java.snippets;

import x.java.IndentService;
import x.java.JavaConfig;
import x.java.JavaRulePath;
import x.java.NodeWrapper;

import static java.util.Arrays.asList;
import static x.java.IndentService.*;

public class MethodDeclaration extends DecoratedJavaCodeSnippet {
    private final IndentService indentService;

    public MethodDeclaration(IndentService indentService) {
        this.indentService = indentService;
    }

    @Override
    public void enterRule(JavaRulePath rulePath) {
        if (rulePath.isCurrentRuleA("annotation")) {
            setCurrentCodeSnippet(new Annotation(indentService));
        } else if (rulePath.isCurrentRuleA("methodDeclaration")) {
            setCurrentCodeSnippet(new DefaultJavaCodeSnippetdHeader(indentService));
        } else if (rulePath.isCurrentRuleA("methodModifier")) {
            setCurrentCodeSnippet(new MethodFirstLine(indentService));
        } else if (rulePath.isCurrentRuleA("methodBody")) {
            setCurrentCodeSnippet(new DefaultJavaCodeSnippetdHeader(indentService));
        }
        withCurrentSnippetIfPresent(s -> s.enterRule(rulePath));
    }
}

class DefaultJavaCodeSnippetdHeader extends SimpleNodesJavaCodeSnippet {

    private final IndentService indentService;

    public DefaultJavaCodeSnippetdHeader(IndentService indentService) {
        this.indentService = indentService;
    }

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
}

class MethodFirstLine extends SimpleNodesJavaCodeSnippet {

    private final IndentService indentService;

    public MethodFirstLine(IndentService indentService) {
        this.indentService = indentService;
    }

    @Override
    protected String toSourceString(NodeWrapper node) {
        StringBuilder builder = new StringBuilder();
        builder.append(node.toSourceString());
        // TODO tmp solution
        if (asList("}", "{", ";").contains(node.toSourceString())) {
            builder.append(JavaConfig.EOL);
            builder.append(indentService.calculateIndentAfter(node));
        } else if (!asList("(").contains(node.toSourceString()) && !asList("(", ")", "<", ">", ",").contains(node.calculateNext().getText())) {
            builder.append(" ");
        }
        return builder.toString();
    }
}