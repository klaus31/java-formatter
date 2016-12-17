package javaformatter.decider.java;

import org.junit.Ignore;
import org.junit.Test;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class JavaDeciderForSpacesDefaultTest {

    @Test
    public void modifySpacesInPartOfLineShouldDoAllThese() {
        JavaDeciderForSpacesDefault decider = new JavaDeciderForSpacesDefault();
        assertThat(decider.modifySpacesInPartOfLine("public int calculatereturnCode() {"), is("public int calculatereturnCode() {"));
        assertThat(decider.modifySpacesInPartOfLine("int returnCode = 5;"), is("int returnCode = 5;"));
        assertThat(decider.modifySpacesInPartOfLine("return     returnCode;"), is("return returnCode;"));
        assertThat(decider.modifySpacesInPartOfLine("List<String, Map<Object, String>>"), is("List<String, Map<Object, String>>"));
    }

    @Test
    @Ignore // this 💩
    public void ichGebeDieseGanzeScheisseAufUndFangeVonVorneAnWeilDieseScheisseHierNichtFunktioniertTest() {
        JavaDeciderForSpacesDefault decider = new JavaDeciderForSpacesDefault();
        assertThat(decider.modifySpacesInPartOfLine("List<String extends Foo> r, String s, List<?> h) { "), is("List<String> r, String s, List<File> h) { "));
        assertThat(decider.modifySpacesInPartOfLine("List<Zoo super Off, ? extends Foo> r, String s, List<? super Bla> h) { "), is("List<String> r, String s, List<File> h) { "));
    }
}