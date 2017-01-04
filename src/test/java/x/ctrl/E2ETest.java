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
        doItWith("java", 1);
    }
    @Test
    public void example2ShouldHaveExpectedOutput() throws IOException {
        doItWith("java", 2);
    }
    @Test
    public void example3ShouldHaveExpectedOutput() throws IOException {
        doItWith("java", 3);
    }
    @Test
    @Ignore // want to do a refactoring before
    public void example4ShouldHaveExpectedOutput() throws IOException {
        doItWith("java", 4);
    }
    private void doItWith(String language, int fileId) throws IOException {
        // given
        String inputFileName = "test-" + fileId + "-input";
        String expectedOutputFileName = "test-" + fileId + "-output";
        SourceCodeFile sourceCodeFile = new SourceCodeFile(calcPath(language, inputFileName));
        SourceCodeFileFormatter formatter = new SourceCodeFileFormatter4JavaDefault(sourceCodeFile);
        // when
        List<String> actualOutputLines = formatter.createOutputLines();
        //then
        List<String> expectedOutputLines = read(language, expectedOutputFileName);
        for (int i = 0; i < expectedOutputLines.size(); i++) {
            String debugMessage = expectedOutputFileName + " at line " + (i + 1) + ":\nActual: \"" + actualOutputLines.get(i) + "\"\nShould: \"" + expectedOutputLines.get(i);
            assertThat(debugMessage, actualOutputLines.get(i), is(expectedOutputLines.get(i)));
        }
    }
    ;
}