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
        assertThat(preProcessLine("if(true){"), is("if (true) {"));

        assertThat(preProcessLine("\"if(true){\""), is("\"if(true){\""));
        assertThat(preProcessLine("foo;bar"), is("foo; bar"));
        assertThat(preProcessLine("for(int i=0;i<a.length;i++){"), is("for (int i = 0; i < a.length; i++) {"));
        assertThat(preProcessLine("do{"), is("do {"));
        assertThat(preProcessLine("while(false)"), is("while (false)"));
        assertThat(preProcessLine("createGif()"), is("createGif()"));
        assertThat(preProcessLine("createWhile()"), is("createWhile()"));
        assertThat(preProcessLine("createAndDo()"), is("createAndDo()"));
        assertThat(preProcessLine("for(;i>=a.length;i++){"), is("for (; i >= a.length; i++) {"));
        assertThat(preProcessLine("for(;i<=a.length;i++){"), is("for (; i <= a.length; i++) {"));
        assertThat(preProcessLine("if(a==b)"), is("if (a == b)"));
        assertThat(preProcessLine("andDo(()->hossa::dieWaldFee)"), is("andDo(() -> hossa::dieWaldFee)"));
        assertThat(preProcessLine("// if(true){"), is("// if(true){"));
        assertThat(preProcessLine("/* if(true){"), is("/* if(true){"));

        assertThat(preProcessLine("* if(true){"), is("* if(true){"));
        assertThat(preProcessLine("int a=3;"), is("int a = 3;"));
        assertThat(preProcessLine("List<String>c=new ArrayList<>();"), is("List<String> c = new ArrayList<>();"));
        assertThat(preProcessLine("boolean a=c<d;"), is("boolean a = c < d;"));
        assertThat(preProcessLine("boolean a=c>d;"), is("boolean a = c > d;"));
        assertThat(preProcessLine("boolean a=c>=d;"), is("boolean a = c >= d;"));
        assertThat(preProcessLine("boolean a=c<=d;"), is("boolean a = c <= d;"));
        assertThat(preProcessLine("for(;i<=a.length;i+=1){"), is("for (; i <= a.length; i += 1) {"));
        assertThat(preProcessLine("for(;i<=a.length;i-=1){"), is("for (; i <= a.length; i -= 1) {"));
        assertThat(preProcessLine("a++;"), is("a++;"));
        assertThat(preProcessLine("a--;"), is("a--;"));
        assertThat(preProcessLine("for(;i<=a.length;i*=1){"), is("for (; i <= a.length; i *= 1) {"));
        assertThat(preProcessLine("int a*=b*c;"), is("int a *= b * c;"));
        assertThat(preProcessLine("int a+=b+c;"), is("int a += b + c;"));
        assertThat(preProcessLine("int a-=b-c;"), is("int a -= b - c;"));
        assertThat(preProcessLine("String a+=\"b\"+\"c\";"), is("String a += \"b\" + \"c\";"));
        assertThat(preProcessLine("for(;i<=a.length;i/=1){"), is("for (; i <= a.length; i /= 1) {"));
        assertThat(preProcessLine("a/=b/c;"), is("a /= b / c;"));
        assertThat(preProcessLine("for(;i<=a.length;i%=1){"), is("for (; i <= a.length; i %= 1) {"));
        assertThat(preProcessLine("int a%=b%c;"), is("int a %= b % c;"));
        assertThat(preProcessLine("boolean a=b||c;"), is("boolean a = b || c;"));
        assertThat(preProcessLine("boolean a=b&&c;"), is("boolean a = b && c;"));
        assertThat(preProcessLine("boolean a=b&c;"), is("boolean a = b & c;"));
        assertThat(preProcessLine("boolean a=b|c;"), is("boolean a = b | c;"));
        assertThat(preProcessLine("boolean a|=b|c;"), is("boolean a |= b | c;"));
        assertThat(preProcessLine("boolean a=b?c:d;"), is("boolean a = b ? c : d;"));
        assertThat(preProcessLine("Foo<?>foo;"), is("Foo<?> foo;"));
        assertThat(preProcessLine("boolean a=(b?c:d)?e:f;"), is("boolean a = (b ? c : d) ? e : f;"));
        assertThat(preProcessLine("for(;;)"), is("for (;;)"));
        assertThat(preProcessLine("boolean a=a()<b();"), is("boolean a = a() < b();"));
        assertThat(preProcessLine("boolean a=a()>b();"), is("boolean a = a() > b();"));
        assertThat(preProcessLine("boolean a=a()>=b();"), is("boolean a = a() >= b();"));
        assertThat(preProcessLine("boolean a=a()<=b();"), is("boolean a = a() <= b();"));
        assertThat(preProcessLine("}else{"), is("} else {"));
        assertThat(preProcessLine("*/"), is("*/"));
        assertThat(preProcessLine("import static foo.bar.Rab.*;"), is("import static foo.bar.Rab.*;"));
        assertThat(preProcessLine("return a;//ok"), is("return a; // ok"));
        assertThat(preProcessLine("foo(a,b)"), is("foo(a, b)"));
        assertThat(preProcessLine("foo(String a,Object b,List c)"), is("foo(String a, Object b, List c)"));
        assertThat(preProcessLine("int a=-2"), is("int a = -2"));
        assertThat(preProcessLine("foo(a!=-5)"), is("foo(a != -5)"));
        assertThat(preProcessLine("foo(\"a\",\"b\")"), is("foo(\"a\", \"b\")"));
        assertThat(preProcessLine("int a ;"), is("int a;"));
        assertThat(preProcessLine("} ) ;"), is("});"));
        assertThat(preProcessLine("return\"\""), is("return \"\""));
        assertThat(preProcessLine("int a=b+2"), is("int a = b + 2"));
        assertThat(preProcessLine("a+=\"\\\"\"+b[ i ];"), is("a += \"\\\"\" + b[i];"));
        assertThat(preProcessLine("}catch("), is("} catch ("));
        assertThat(preProcessLine("Optional<List<String>>findObj(String name){"), is("Optional<List<String>> findObj(String name) {"));
//        assertThat(preProcessLine("int spaceHere,int noSpaceAtEnd,"), is("int spaceHere, int noSpaceAtEnd,"));
//        assertThat(preProcessLine("foo(-1)"), is("foo(-1)"));
//        assertThat(preProcessLine("foo(result()-12)"), is("foo(result() - 12)"));

        // TODO (wtf syntax): assertThat(preProcessLine("int a=+2"), is("int a = +2"));
    }

    private String preProcessLine(String line) {
        List<String> lines = new ArrayList<>();
        lines.add(line);
        List<String> preprocessedLines = new CodeActionDeciderJava().preProcessLines(lines);
        return preprocessedLines.get(0);
    }

    @Test
    public void postProcessShouldDoFormatDocCorrectly() {
        // given
        List<String> lines = new ArrayList<>();
        lines.add("    /*");
        lines.add("    * hi ho ha");
        lines.add("    * this is wunderbar");
        lines.add("    */");
        // when
        List<String> postProcessed = new CodeActionDeciderJava().postProcessFormattedLines(lines);
        // then
        assertThat(postProcessed.get(0), is("    /*"));
        assertThat(postProcessed.get(1), is("     * hi ho ha"));
        assertThat(postProcessed.get(2), is("     * this is wunderbar"));
        assertThat(postProcessed.get(3), is("     */"));
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
        lines.add("// javadoc      => beast");
        lines.add("/* javadoc      => beast");
        lines.add("* javadoc      => beast");

        // when
        List<String> preprocessedLines = new CodeActionDeciderJava().preProcessLines(lines);

        // then
        assertThat(preprocessedLines.get(0), is("final String s;"));
        assertThat(preprocessedLines.get(1), is("final String b = \"  \";"));
        assertThat(preprocessedLines.get(2), is("final String c = \"  \\\"  \";"));
        assertThat(preprocessedLines.get(3), is("\"  \", "));
        assertThat(preprocessedLines.get(4), is("final String b = \"\""));
        assertThat(preprocessedLines.get(5), is("doSomething('\"', 5)"));
        assertThat(preprocessedLines.get(6), is("// javadoc      => beast"));
        assertThat(preprocessedLines.get(7), is("/* javadoc      => beast"));
        assertThat(preprocessedLines.get(8), is("* javadoc      => beast"));
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