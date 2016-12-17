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
    public void formatterShouldOrderComponentsCorrectly() throws IOException {
        doItWith("java", "example-class-1");
    }

    private void doItWith(String language, String fileNameKey) throws IOException {
        //given
        String inputFileName = fileNameKey + "-input";String expectedOutputFileName = fileNameKey + "-expected-output";
        SourceCodeFile sourceCodeFile = new SourceCodeFile(calcPath(language, inputFileName));
        SourceCodeFileFormatter formatter = new SourceCodeFileFormatter4JavaDefault(sourceCodeFile);

        // when
        List<String> actualOutputLines = formatter.createOutputLines();

        //then
        List<String> expectedOutputLines = read("java", expectedOutputFileName);
        for (int i = 0; i < expectedOutputLines.size(); i++) {
            String debugMessage = expectedOutputFileName + " at line " + (i + 1) + ":\nActual: \"" + actualOutputLines.get(i) + "\"\nShould: \"" + expectedOutputLines.get(i);
            assertThat(debugMessage, actualOutputLines.get(i), is(expectedOutputLines.get(i)));
        }
    };
}