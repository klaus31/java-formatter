package x.java;

import x.format.RulePath;

import java.util.List;
import java.util.Optional;

public class JavaRulePath extends RulePath {

    JavaRulePath() {
        super();
    }

    JavaRulePath(JavaRulePath javaRulePath) {
        super();
        javaRulePath.getRulePathNames().forEach(this::enter);
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
