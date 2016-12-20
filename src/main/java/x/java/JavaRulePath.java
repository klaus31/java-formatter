package x.java;

import x.format.RulePath;

class JavaRulePath extends RulePath {

    JavaRulePath() {
        super();
    }

    boolean isInsideForStatement() {
        return isCurrentRuleA("basicForStatement");
    }

    boolean isClassDeclaration() {
        return isPartOf("classDeclaration");
    }

    boolean isMethodDeclaration() {
        return isPartOf("methodDeclaration");
    }

    boolean isLocalVariableDeclaration() {
        return isPartOf("localVariableDeclaration");
    }

    boolean isMethodInvocation() {
        return isPartOf("methodInvocation");
    }

    boolean isAnnotation() {
        return isPartOf("annotation");
    }

    boolean isImportDeclaration() {
        return isPartOf("importDeclaration");
    }

    boolean isPackageDeclaration() {
        return isPartOf("packageDeclaration");
    }
}
