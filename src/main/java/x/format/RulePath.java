package x.format;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class RulePath {

    private final List<String> rulePathNames;

    protected RulePath() {
        this.rulePathNames = new ArrayList<>();
    }

    protected List<String> getRulePathNames() {
        return rulePathNames;
    }

    public void enter(String ruleName) {
        rulePathNames.add(ruleName);
    }

    public void exit(String ruleName) {
        rulePathNames.remove(rulePathNames.size()-1);
    }

    String getCurrentRule() {
        return rulePathNames.get(rulePathNames.size()-1);
    }

    public boolean isCurrentRuleA(String ruleName) {
        return ruleName.equals(getCurrentRule());
    }

    public boolean isPartOf(String ruleName) {
        return rulePathNames.contains(ruleName);
    }

    @Override
    public String toString() {
        return rulePathNames.toString();
    }

    public boolean isPartOfAnyOf(String ... ruleNames) {
        return Stream.of(ruleNames).anyMatch(rulePathNames::contains);
    }

    public boolean isCurrentRuleAnyOf(String ... ruleNames) {
        return Stream.of(ruleNames).anyMatch(this::isCurrentRuleA);
    }

    public int stepsAwayFrom(String ruleName) {
        return rulePathNames.size() - 1 - rulePathNames.lastIndexOf(ruleName);
    }

    public boolean matches(String ... ruleNames) {
        int index = -1;
        for (String ruleName : ruleNames) {
            int currentIndex = rulePathNames.lastIndexOf(ruleName);
            if(currentIndex < 0 || currentIndex <= index) {
                return false;
            } else {
                index = currentIndex;
            }
        }
        return true;
    }

    public Optional<String> calculateLastRuleEqualsAnyOf(List<String> rules) {
        Optional<String> winner = Optional.empty();
        int tmp = -1;
        for (String rule : rules) {
            int lastIndex = this.getRulePathNames().lastIndexOf(rule);
            if(tmp < lastIndex) {
                winner = Optional.of(rule);
                tmp = lastIndex;
            }
        }
        return winner;
    }
}
