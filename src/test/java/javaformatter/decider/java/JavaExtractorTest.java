package javaformatter.decider.java;

import javaformatter.TestFileReadIn;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class JavaExtractorTest {

    private List<String> lines = TestFileReadIn.read("java", "ExampleJavaClass");

    @Test
    public void extractMethodsShouldExtractMethodsOfMainClass() {
        // given
        JavaExtractor extractor = new JavaExtractor(lines);
        // when
        List<JavaMethod> javaMethods = extractor.extractMethods();
        // then
        assertThat(javaMethods, hasSize(4));
    }

    @Test
    public void extractMethodsShouldExtractCorrectLines() {
        // given
        JavaExtractor extractor = new JavaExtractor(lines);
        // when
        List<JavaMethod> javaMethods = extractor.extractMethods();
        // when / then
        assertThat(javaMethods.get(0).extractMethodDeclaration().trim(), is("public static int fact(int n)"));
        assertThat(javaMethods.get(0).getLines().size(), is(7));
        assertThat(javaMethods.get(1).extractMethodDeclaration().trim(), is("abstract void doIt(Object likeTheyDoIt);"));
        assertThat(javaMethods.get(1).getLines().size(), is(4));
    }

}