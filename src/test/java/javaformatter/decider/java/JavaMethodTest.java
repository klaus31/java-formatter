package javaformatter.decider.java;

import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class JavaMethodTest {

    @Test
    public void extractMethodDeclarationShouldDo() {

        // given
        List<String> lines = new ArrayList<>();
        lines.add("public void foo() {");
        lines.add("}");
        JavaMethod javaMethod = new JavaMethod(lines);

        // when / then
        assertThat(javaMethod.extractMethodDeclaration(), is("public void foo() {"));
        assertThat(javaMethod.getLines().size(), is(2));
    }
}