package x.java;

import x.java.snippets.*;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static x.ctrl.MiserableLogger.logDebug;

public class JavaConfig {
    public static final String EOL = "\n";
    private static IndentService indentService;
    private static String ruleCurrentJavaCodeSnippetIsFor;
    private JavaConfig ( ) {
    }
    public static IndentService getIndentService() {
        if (indentService == null) {
            indentService = new IndentService("    ");
        }
        return indentService;
    }
    static JavaCodeSnippet createCompilationUnit() {
        return new CompilationUnit();
    }
    public static SimpleNodesJavaCodeSnippet createComment() {
        return new Comment();
    }
    private static final List<String> RULES_HAVING_A_MATCHING_FORMATTER = Arrays . asList ("annotation" , "methodBody" , "methodModifier" , "methodDeclarator" , "packageDeclaration" , "importDeclaration" , "fieldDeclaration" , "methodDeclaration" , "interfaceMethodDeclaration" , "classDeclaration" , "classModifier" , "classBody" , "interfaceDeclaration");
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
            case "methodDeclaration":
            return new MethodBody();
            case "methodBody":
            return new MethodBody();
            case "methodModifier":
            return new MethodBody();
            case "methodDeclarator":
            return new MethodBody();
            case "packageDeclaration":
            return new PackageDeclaration();
            case "importDeclaration":
            return new ImportDeclaration();
            case "fieldDeclaration":
            return new FieldDeclaration();
            case "interfaceMethodDeclaration":
            return new MethodBody();
            case "classDeclaration":
            return new ClassDeclaration();
            case "classModifier":
            return new ClassDeclaration();
            case "classBody":
            return new ClassDeclaration();
            case "interfaceDeclaration":
            return new ClassDeclaration();
            default:
            throw new AssertionError(ruleName+" does not have a matching code snippet formatter");
        }
    }
}