package x.ctrl;

import org.junit.Ignore;
import org.junit.Test;
import x.java.SourceCodeFileFormatter4JavaDefault;
import java.io.IOException;
import java.util.List;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static x.TestFileReadIn.calcPath;
import static x.TestFileReadIn.read;
public class E2ETest {
    @Test
    public void example1ShouldHaveExpectedOutput() throws IOException {
        expectInputFileEqualsOutputFile("java", 1);
    }
    @Test
    public void example2ShouldHaveExpectedOutput() throws IOException {
        expectInputFileEqualsOutputFile("java", 2);
    }
    @Test
    public void example3ShouldHaveExpectedOutput() throws IOException {
        expectFormatterNotChangingFile("java", 3);
    }
    @Test
    public void example4ShouldHaveExpectedOutput() throws IOException {
        expectFormatterNotChangingFile("java", 4);
    }
    @Test
    public void example5ShouldHaveExpectedOutput() throws IOException {
        expectFormatterNotChangingFile("java", 5);
    }
    @Test
    public void example6ShouldHaveExpectedOutput() throws IOException {
        expectFormatterNotChangingFile("java", 6);
    }
    @Test
    public void example7ShouldHaveExpectedOutput() throws IOException {
        expectFormatterNotChangingFile("java", 7);
    }
    @Test
    public void example8ShouldHaveExpectedOutput() throws IOException {
        expectFormatterNotChangingFile("java", 8);
    }
    @Test
    public void example9ShouldHaveExpectedOutput() throws IOException {
        expectFormatterNotChangingFile("java", 9);
    }
    @Test
    public void example10ShouldHaveExpectedOutput() throws IOException {
        expectFormatterNotChangingFile("java", 10);
    }
    private void expectFormatterNotChangingFile(String language, int fileId) {
        String inputAndOutputFileName = "test-" + fileId + "-input-output";
        expectInputFileEqualsOutputFile(language, inputAndOutputFileName, inputAndOutputFileName);
    }
    private void expectInputFileEqualsOutputFile(String language, int fileId) throws IOException {
        // given
        String inputFileName = "test-" + fileId + "-input";
        String outputFileName = "test-" + fileId + "-output";
        expectInputFileEqualsOutputFile(language, inputFileName, outputFileName);
    }
    private void expectInputFileEqualsOutputFile(String language, String inputFileName, String outputFileName) {
        SourceCodeFile sourceCodeFile = new SourceCodeFile(calcPath(language, inputFileName));
        SourceCodeFileFormatter formatter = new SourceCodeFileFormatter4JavaDefault(sourceCodeFile);
        // when
        List<String> actualOutputLines = formatter.createOutputLines();
        actualOutputLines.forEach(System.out::println);
        //then
        List<String> expectedOutputLines = read(language, outputFileName);
        for (int i = 0; i < expectedOutputLines.size(); i++) {
            String debugMessage = outputFileName + " at line " + (i + 1) + ":\nActual: \"" + actualOutputLines.get(i) + "\"\nShould: \"" + expectedOutputLines.get(i);
            assertThat(debugMessage, actualOutputLines.get(i), is(expectedOutputLines.get(i)));
        }
    }
}