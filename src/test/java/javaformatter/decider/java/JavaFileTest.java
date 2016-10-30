package javaformatter.decider.java;

import java.util.List;
import javaformatter.TestFileReadIn;
import org.junit.Test;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class JavaFileTest {

    private List<String> lines = TestFileReadIn.read("java", "ExampleJavaClass");

    @Test
    public void extractMethodsShouldExtractMethodsOfMainClass() {

        // given
        JavaFile extractor = new JavaFile(lines);

        // when
        List<JavaMethod> javaMethods = extractor.extractClasses().get(0).extractMethods();

        // then
        assertThat(javaMethods, hasSize(4));
    }

    @Test
    public void extractMethodsShouldExtractCorrectLines() {

        // given
        JavaFile extractor = new JavaFile(lines);

        // when
        List<JavaMethod> javaMethods = extractor.extractClasses().get(0).extractMethods();

        // when / then
        assertThat(javaMethods.get(0).extractMethodDeclaration().trim(), is("public static int fact(int n)"));
        assertThat(javaMethods.get(0).getLines().size(), is(7));
        assertThat(javaMethods.get(1).extractMethodDeclaration().trim(), is("abstract void doIt(Object likeTheyDoIt);"));
        assertThat(javaMethods.get(1).getLines().size(), is(4));
    }
}