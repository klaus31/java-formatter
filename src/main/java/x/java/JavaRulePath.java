package x.java;

import x.format.RulePath;

/**
 * TODO check, if this class is needed (class does not make sense yet)
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
