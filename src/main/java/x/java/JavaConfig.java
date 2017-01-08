package x.java;

import x.java.snippets.*;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static x.ctrl.MiserableLogger.logDebug;
public class JavaConfig {
    public static final String EOL = "\n";
    private static final IndentService indentService = new IndentService("    ");
    private static String ruleCurrentJavaCodeSnippetIsFor;
    private JavaConfig() {
    }
    public static IndentService getIndentService() {
        return indentService;
    }
    static JavaCodeSnippet createCompilationUnit() {
        return new CompilationUnit();
    }
    public static SimpleNodesJavaCodeSnippet createComment() {
        return new Comment();
    }
    private static final List<String> RULES_HAVING_A_MATCHING_FORMATTER = Arrays.asList("annotation", "packageDeclaration", "importDeclaration", "fieldDeclaration", "methodDeclaration", "interfaceMethodDeclaration", "classDeclaration", "interfaceDeclaration");
    public static Optional<JavaCodeSnippet> getMatchingCodeSnippetFor(JavaRulePath rulePath) {
        Optional<String> newRule = rulePath.calculateLastRuleEqualsAnyOf(RULES_HAVING_A_MATCHING_FORMATTER);
        if (! newRule.isPresent() || newRule.get().equals(ruleCurrentJavaCodeSnippetIsFor)) {
            return Optional.empty();
        } else {
            ruleCurrentJavaCodeSnippetIsFor = newRule.get();
            return Optional.of(getMatchingCodeSnippetFor(ruleCurrentJavaCodeSnippetIsFor));
        }
    }
    private static JavaCodeSnippet getMatchingCodeSnippetFor(String ruleName) {
        logDebug("Using new CodeSnippet for " + ruleName);
        switch (ruleName) {
            case "annotation":
            return new Annotation();
            case "packageDeclaration":
            return new PackageDeclaration();
            case "importDeclaration":
            return new ImportDeclaration();
            case "fieldDeclaration":
            case "methodDeclaration":
            case "interfaceMethodDeclaration":
            case "classDeclaration":
            case "interfaceDeclaration":
            return new WhateverDeclaration();
            default:
            throw new AssertionError(ruleName + " does not have a matching code snippet formatter");
        }
    }
}