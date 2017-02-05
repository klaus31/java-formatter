package x.format;

import org.junit.Test;
import x.java.JavaRulePath;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
public class RulePathTest {

    @Test
    public void matchesShouldMatch() {

        // given
        RulePath rulePath = new RulePath();
        rulePath.enter("a");
        rulePath.enter("b");
        rulePath.enter("c");

        // when / then
        assertThat(rulePath.matches("a", "c"), is(true));
        assertThat(rulePath.matches("b", "c"), is(true));
        assertThat(rulePath.matches("c", "a"), is(false));
        assertThat(rulePath.matches("d"), is(false));
        assertThat(rulePath.matches("a", "a"), is(false));
    }
}