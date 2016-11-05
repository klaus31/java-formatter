package javaformatter.ctrl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javaformatter.decider.Decider;
import javaformatter.decider.java.JavaDeciderDefault;
import org.junit.Assert;
import org.junit.Test;
import static javaformatter.TestFileReadIn.calcPath;
import static javaformatter.TestFileReadIn.read;
import static org.hamcrest.Matchers.is;

public class E2ETest {

    @Test
    public void classAInputShouldHaveClassAExpectedOutput() throws IOException {
        doItWith("java", "ClassAInput", "ClassAExpectedOutput");
    }

    @Test
    public void classBInputShouldHaveClassBExpectedOutput() throws IOException {
        doItWith("java", "ClassBInput", "ClassBExpectedOutput");
    }

    private void doItWith(String language, String inputFileName, String expectedOutputFileName) throws IOException {

        //given
        SourceCodeFile sourceCodeFile = new SourceCodeFile(calcPath(language, inputFileName));
        Decider decider = new JavaDeciderDefault();
        SourceCodeFormatter formatter = new SourceCodeFormatter(sourceCodeFile, decider);

        // when
        formatter.format();
        formatter.withSource(actualFormattedSource -> {

            //then
            List<String> expectedOutputLines = read("java", expectedOutputFileName);
            List<String> actualOutputLines = Arrays.asList(actualFormattedSource.split("\n"));
            for (int i = 0; i < expectedOutputLines.size(); i++) {
                Assert.assertThat(expectedOutputFileName + " at line " + i + ":\nActual: \"" + actualOutputLines.get(i) + "\"\nShould: \"" + expectedOutputLines.get(i)+ "\"\n\n" + actualFormattedSource, actualOutputLines.get(i), is(expectedOutputLines.get(i)));
            }
        });
    }
}