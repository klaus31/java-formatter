package javaformatter.decider.java;

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
    }
}