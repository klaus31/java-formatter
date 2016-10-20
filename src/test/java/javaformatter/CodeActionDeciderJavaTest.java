package javaformatter;

import org.junit.Test;
import java.util.ArrayList;
import java.util.List;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class CodeActionDeciderJavaTest {
    
    @Test
    public void blankLinesBeforeShouldDoOnMultiMethods() {
        
        // given
        List<String> lines = new ArrayList<>();
        lines.add("public void a() {}");
        lines.add("private static final void b(String b) {}");
        lines.add("void c() {}");
        
        // when
        CodeActionDeciderJava decider = new CodeActionDeciderJava();
        
        // then
        assertThat(decider.blankLinesBefore(lines, 0), is(1));
        assertThat(decider.blankLinesBefore(lines, 1), is(1));
        assertThat(decider.blankLinesBefore(lines, 2), is(1));
    }
    
    @Test
    public void preProcessLinesShouldAddSpaces() {
        
        // given
        List<String> lines = new ArrayList<>();
        lines.add("if(true){");
        
        // when
        List<String> preprocessedLines = new CodeActionDeciderJava().preProcessLines(lines);
        
        // then
        assertThat(preprocessedLines.get(0), is("if (true) {"));
    }
    
    @Test
    public void debug201610201905() {
        List<String> lines = new ArrayList<>();
        lines.add("@Test");
        lines.add("public void preProcessLinesShouldAddSpaces() {");
        lines.add("");
        lines.add("    // given");
        lines.add("    List<String> lines = new ArrayList<>();");
        lines.add("    lines.add(\"if(true){\");");
        lines.add("lines.add(\"    lines.add(\\\"if(true){\\\");\");");
        lines.add("lines.add(\"lines.add(\\\"    lines.add(\\\\\\\"if(true){\\\\\\\");\\\");\");");
        lines.add("    // when");
        lines.add("    List<String> preprocessedLines = new CodeActionDeciderJava().preProcessLines(lines);");
        lines.add("}");
        
        // when / then
        assertThat(new CodeActionDeciderJava().blankLinesAfter(lines.get(4)), is(0));
        assertThat(new CodeActionDeciderJava().blankLinesBefore(lines, 5), is(0));
        assertThat(new CodeActionDeciderJava().blankLinesBefore(lines, 6), is(0));
        assertThat(new CodeActionDeciderJava().blankLinesBefore(lines, 7), is(0));
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