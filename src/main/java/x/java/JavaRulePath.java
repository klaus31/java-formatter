package x.java;

import x.format.RulePath;

public class JavaRulePath extends RulePath {

    JavaRulePath() {
        super();
    }

    public boolean isInsideForStatement() {
        return isCurrentRuleA("basicForStatement");
    }

    public boolean isClassDeclaration() {
        return isPartOf("classDeclaration");
    }

    public boolean isMethodDeclaration() {
        return isPartOf("methodDeclaration");
    }

    public boolean isLocalVariableDeclaration() {
        return isPartOf("localVariableDeclaration");
    }

    public boolean isMethodInvocation() {
        return isPartOf("methodInvocation");
    }

    public boolean isAnnotation() {
        return isPartOf("annotation");
    }

    public boolean isImportDeclaration() {
        return isPartOf("importDeclaration");
    }

    public boolean isPackageDeclaration() {
        return isPartOf("packageDeclaration");
    }

    public boolean isFieldDeclaration() {
        return isPartOf("fieldDeclaration");
    }
}
