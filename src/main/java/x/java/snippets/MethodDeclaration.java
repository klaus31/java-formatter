package x.java.snippets;

import org.antlr.v4.runtime.tree.ParseTree;
import x.java.IndentService;
import x.java.JavaConfig;
import x.java.JavaRulePath;
import x.java.NodeWrapper;

import static java.util.Arrays.asList;

public class MethodDeclaration extends DecoratedJavaCodeSnippet {
    private final IndentService indentService;

    public MethodDeclaration(IndentService indentService) {
        this.indentService = indentService;
    }

    @Override
    public void enterRule(JavaRulePath rulePath) {
        if (rulePath.isCurrentRuleA("annotation")) {
            setCurrentCodeSnippet(new Annotation(indentService));
        } else if (rulePath.isCurrentRuleA("methodDeclaration") || rulePath.isCurrentRuleA("methodBody")) {
            setCurrentCodeSnippet(new MethodBody(indentService));
        } else if (rulePath.isCurrentRuleA("methodModifier")) {
            setCurrentCodeSnippet(new MethodFirstLine(indentService));
        }
        withCurrentSnippetIfPresent(s -> s.enterRule(rulePath));
    }
}

class MethodBody extends SimpleNodesJavaCodeSnippet {

    private final IndentService indentService;

    public MethodBody(IndentService indentService) {
        this.indentService = indentService;
    }

    @Override
    protected String toSourceString(NodeWrapper node) {
        StringBuilder builder = new StringBuilder();
        builder.append(node.toSourceString());
        if (node.isBlockStartOrEnd() || node.isSemicolonAtEnd()) {
            builder.append(JavaConfig.EOL);
            builder.append(indentService.calculateIndentAfter(node));
        } else if (requiresWhitespaceAfter(node)) {
            builder.append(" ");
        }
        return builder.toString();
    }

    private boolean requiresWhitespaceAfter(NodeWrapper node) {
        if (node.isSemicolonInBasicForStatement()) {
            return true;
        }
        if (asList(";", "(", ".","++","--").contains(node.toSourceString())) {
            return false;
        }
        if (asList("new", ",").contains(node.toSourceString())) {
            return true;
        }
        ParseTree nextNode = node.calculateNext();
        if("(".equals(nextNode.getText())) {
            return asList("if", "for", "while", "+","-","*","/","%").contains(node.toSourceString());
        }
        if (asList(";", ")", ",", ".", "++", "--").contains(nextNode.getText())) {
            return false;
        }
        if (")".equals(node.toSourceString())) {
            return !asList(".").contains(nextNode.getText());
        }
        if (node.matchesRulePath("unannClassType_lfno_unannClassOrInterfaceType")) {
            if (asList("<", ",", ">").contains(nextNode.getText())) {
                return false;
            } else if (node.toSourceString().equals(",")) {
                return true;
            } else if (node.toSourceString().equals("<")) {
                return false;
            } else {
                return true;
            }
        }
        if (node.matchesRulePath("classInstanceCreationExpression_lfno_primary")) {
            return false; // expecting diamond operator
        }
        return true;
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