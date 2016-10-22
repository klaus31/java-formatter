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
        lines.add("\"if(true){\"");
        lines.add("foo;bar");
        lines.add("for(int i=0;i<a.length;i++){");
        lines.add("do{");
        lines.add("while(false)");
        lines.add("createGif()");
        lines.add("createWhile()");
        lines.add("createAndDo()");
        lines.add("for(;i>=a.length;i++){");
        lines.add("for(;i<=a.length;i++){");
        lines.add("if(a==b)");

        // when
        List<String> preprocessedLines = new CodeActionDeciderJava().preProcessLines(lines);
        
        // then
        assertThat(preprocessedLines.get(0), is("if (true) {"));
        assertThat(preprocessedLines.get(1), is("\"if(true){\""));
        assertThat(preprocessedLines.get(2), is("foo; bar"));
        assertThat(preprocessedLines.get(3), is("for (int i = 0; i < a.length; i++) {"));
        assertThat(preprocessedLines.get(4), is("do {"));
        assertThat(preprocessedLines.get(5), is("while (false)"));
        assertThat(preprocessedLines.get(6), is("createGif()"));
        assertThat(preprocessedLines.get(7), is("createWhile()"));
        assertThat(preprocessedLines.get(8), is("createAndDo()"));
        assertThat(preprocessedLines.get(9), is("for (; i >= a.length; i++) {"));
        assertThat(preprocessedLines.get(10), is("for (; i <= a.length; i++) {"));
        assertThat(preprocessedLines.get(11), is("if (a == b)"));
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
    public void preProcessLinesShouldKillDoubleSpaces() {

        // given
        List<String> lines = new ArrayList<>();
        lines.add("final     String             s;");
        lines.add("final String b = \"  \";");
        lines.add("final String c = \"  \\\"  \";");
        lines.add("\"  \",");
        lines.add("final String b = \"\"");
        lines.add("doSomething('\"',     5)");

        // when
        List<String> preprocessedLines = new CodeActionDeciderJava().preProcessLines(lines);
        
        // then
        assertThat(preprocessedLines.get(0), is("final String s;"));
        assertThat(preprocessedLines.get(1), is("final String b = \"  \";"));
        assertThat(preprocessedLines.get(2), is("final String c = \"  \\\"  \";"));
        assertThat(preprocessedLines.get(3), is("\"  \","));
        assertThat(preprocessedLines.get(4), is("final String b = \"\""));
        assertThat(preprocessedLines.get(5), is("doSomething('\"', 5)"));
    }
    
    @Test
    public void debug201610221709() {
        
        // given
        List<String> lines = new ArrayList<>();
        lines.add("assertFalse(isBlockClose(\"'}'\"));");
        
        // when
        List<String> preprocessedLines = new CodeActionDeciderJava().preProcessLines(lines);
        
        // then no endless loop
        assertThat(preprocessedLines.get(0), is("assertFalse(isBlockClose(\"'}'\"));"));
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