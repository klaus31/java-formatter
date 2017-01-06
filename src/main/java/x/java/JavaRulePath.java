package x.java;

import x.format.RulePath;

/**
 * TODO do we need this class?!
 */
public class JavaRulePath extends RulePath {

    JavaRulePath() {
        super();
    }

    JavaRulePath(JavaRulePath javaRulePath) {
        super();
        javaRulePath.getRulePathNames().forEach(this::enter);
    }

}
