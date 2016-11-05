package javaformatter.ctrl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javaformatter.decider.Decider;
import javaformatter.decider.java.JavaDeciderDefault;
import org.junit.Assert;
import org.junit.Ignore;
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

    @Test
    @Ignore // TODO das ding ist hammerhart!
    public void exampleCInputShouldHaveExmpleCExpectedOutput() throws IOException {

        // Ich sollte in der preaction erst einmal alles in einer zeile schreiben, was:

        // 1. nicht mit einem ; endet

        // 2. anschließend nach Punkten trennen, wenn bestimmte Bedingungen:

        // 2.1 Die Zeile ist zu lang

        // 2.2 Schlüsselwörter kommen vor (nicht mein geschmack,aber gut)
        doItWith("java", "ExampleCInput", "ExampleCExpectedOutput");
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
                Assert.assertThat(expectedOutputFileName + " at line " + i + ":\nActual: \"" + actualOutputLines.get(i) + "\"\nShould: \"" + expectedOutputLines.get(i) + "\"\n\n" + actualFormattedSource, actualOutputLines.get(i), is(expectedOutputLines.get(i)));
            }
        });
    }
}