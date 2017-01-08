package x.java;

import x.java.snippets.*;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static x.ctrl.MiserableLogger.logDebug;
public class JavaConfig {
    public static final String EOL = "\n";
    private static final IndentService indentService = new IndentService("    ");
    private static final EolService eolService = new EolService();
    private static final BlankService blankService = new BlankService(getEolService());
    private static String ruleCurrentJavaCodeSnippetIsFor;
    private JavaConfig() {
    }
    public static BlankService getBlankService() {
        return blankService;
    }
    public static EolService getEolService() {
        return eolService;
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
    private static final List<String> RULES_HAVING_A_MATCHING_FORMATTER = Arrays.asList("annotation", "methodModifier", "methodDeclarator", "packageDeclaration", "importDeclaration", "fieldDeclaration", "methodDeclaration", "interfaceMethodDeclaration", "classDeclaration", "classModifier", "classBody", "interfaceDeclaration");
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
            return new MethodBodyOrClassDeclaration();
            case "methodModifier":
            return new MethodBodyOrClassDeclaration();
            case "methodDeclarator":
            return new MethodBodyOrClassDeclaration();
            case "packageDeclaration":
            return new PackageDeclaration();
            case "importDeclaration":
            return new ImportDeclaration();
            case "fieldDeclaration":
            return new FieldDeclaration();
            case "interfaceMethodDeclaration":
            return new MethodBodyOrClassDeclaration();
            case "classDeclaration":
            return new MethodBodyOrClassDeclaration();
            case "classModifier":
            return new MethodBodyOrClassDeclaration();
            case "classBody":
            return new MethodBodyOrClassDeclaration();
            case "interfaceDeclaration":
            return new MethodBodyOrClassDeclaration();
            default:
            throw new AssertionError(ruleName + " does not have a matching code snippet formatter");
        }
    }
}