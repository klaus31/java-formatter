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

    boolean isCurrentRuleA(String ruleName) {
        return ruleName.equals(getCurrentRule());
    }

    boolean isPartOf(String ruleName) {
        return rulePathNames.contains(ruleName);
    }

    @Override
    public String toString() {
        return rulePathNames.toString();
    }

    public boolean isPartOfAnyOf(String ... ruleNames) {
        return Stream.of(ruleNames).anyMatch(rulePathNames::contains);
    }

    public boolean isCurrentRuleOneOf(String ... ruleNames) {
        return Stream.of(ruleNames).anyMatch(this::isCurrentRuleA);
    }

    public int stepsAwayFrom(String ruleName) {
        return rulePathNames.size() - 1 - rulePathNames.lastIndexOf(ruleName);
    }
}
