package x.java;

import x.ctrl.MiserableLogger;
import x.format.RulePath;
import java.util.ArrayList;
import java.util.List;
public class JavaRulePath extends RulePath {
    JavaRulePath() {
        super();
    }
    JavaRulePath(JavaRulePath javaRulePath) {
        super();
        javaRulePath.getRulePathNames().forEach(this::enter);
    }
}