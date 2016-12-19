package x.java;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class RulePath {

    private final List<String> rulePathNames;

    RulePath() {
        this.rulePathNames = new ArrayList<>();
    }

    void enter(String ruleName) {
        rulePathNames.add(ruleName);
    }

    void exit(String ruleName) {
        rulePathNames.remove(rulePathNames.size()-1);
    }

    String getCurrentRule() {
        return rulePathNames.get(rulePathNames.size()-1);
    }

    private boolean isCurrentRuleA(String ruleName) {
        return ruleName.equals(getCurrentRule());
    }

    private boolean contains(String ruleName) {
        return rulePathNames.contains(ruleName);
    }

    @Override
    public String toString() {
        return rulePathNames.toString();
    }

    public boolean containsOneOf(String ... ruleNames) {
        return Stream.of(ruleNames).anyMatch(rulePathNames::contains);
    }

    public boolean isPartOfPackage() {
        return contains("packageDeclaration");
    }

    public boolean isPartOfImport() {
        return contains("importDeclaration");
    }

    public boolean isPartOfStaticImport() {
        return containsOneOf("staticImportOnDemandDeclaration", "singleStaticImportDeclaration", "singleTypeImportDeclaration");
    }

    public boolean isCurrentRuleAnImport() {
        return isCurrentRuleA("importDeclaration");
    }

    public boolean isCurrentRuleAPackage() {
        return isCurrentRuleA("packageDeclaration");
    }

    public boolean isCurrentRuleACompilationUnit() {
        return isCurrentRuleA("compilationUnit");
    }
}
