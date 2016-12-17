package javaformatter.ctrl;

import javaformatter.java.SourceCodeFileFormatter4JavaDefault;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static javaformatter.TestFileReadIn.calcPath;
import static javaformatter.TestFileReadIn.read;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class E2ETest {

    @Test
    @Ignore
    public void formatterShouldMeltLinesCorrectly() throws IOException {
        doItWith("java", "melt-lines-together-input", "melt-lines-together-expected-output");
    }

    @Test
    @Ignore
    public void formatterShouldNotDoAlreadyKnownBugs() throws IOException {
        doItWith("java", "debugging-stuff-input", "debugging-stuff-input-expected-output");
    }

    @Test
    @Ignore
    public void formatterShouldOrderComponentsCorrectly() throws IOException {
        doItWith("java", "order-components-test-input", "order-components-test-expected-output");
    }

    private void doItWith(String language, String inputFileName, String expectedOutputFileName) throws IOException {

        //given
        SourceCodeFile sourceCodeFile = new SourceCodeFile(calcPath(language, inputFileName));
        SourceCodeFileFormatter formatter = new SourceCodeFileFormatter4JavaDefault();

        // when
        formatter.createOutputLines(sourceCodeFile.readContentLines());
        List<String> actualOutputLines = formatter.createOutputLines(sourceCodeFile.readContentLines());

        //then
        List<String> expectedOutputLines = read("java", expectedOutputFileName);
        for (int i = 0; i < expectedOutputLines.size(); i++) {
            String debugMessage = expectedOutputFileName + " at line " + (i + 1) + ":\nActual: \"" + actualOutputLines.get(i) + "\"\nShould: \"" + expectedOutputLines.get(i);
            assertThat(debugMessage, actualOutputLines.get(i), is(expectedOutputLines.get(i)));
        }
    };
}