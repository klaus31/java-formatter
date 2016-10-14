package javaformatter;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class CodeActionDeciderJavaTest {

    @Test
    public void preProcessLinesShouldAddSpaces() {
        // given
        List<String> lines = new ArrayList<>();
        lines.add("if(true){");
        // when
        List<String> preprocessedLines = new CodeActionDeciderJava().preProcessLines(lines);
        // then
        assertThat(1, is(1));
        assertThat(preprocessedLines.get(0), is("if (true) {"));
    }

    @Test
    public void preProcessLinesShouldTrim() {
        // given
        List<String> lines = new ArrayList<>();
        lines.add("  import com.mongodb.MongoClient;  ");
        // when
        List<String> preprocessedLines = new CodeActionDeciderJava().preProcessLines(lines);
        // then
        assertThat(preprocessedLines.size(), is(1));
        assertThat(preprocessedLines.get(0), is("import com.mongodb.MongoClient;"));
    }
}