package javaformatter;

import org.junit.Ignore;
import org.junit.Test;
import java.util.ArrayList;
import java.util.List;
import static javaformatter.CodeActionDeciderJavaUtil.*;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class CodeActionDeciderJavaUtilTest {
    
    @Test
    public void isAPureDocLineShouldDo() {
        assertThat(isAPureDocLine("//"), is(true));
        assertThat(isAPureDocLine("// hi!"), is(true));
        assertThat(isAPureDocLine("/*"), is(true));
        assertThat(isAPureDocLine("/* hi!"), is(true));
        assertThat(isAPureDocLine("/**"), is(true));
        assertThat(isAPureDocLine("/** hi!"), is(true));
        assertThat(isAPureDocLine("* hi!"), is(true));
        assertThat(isAPureDocLine("int a; // hi!"), is(false));
        assertThat(isAPureDocLine("int a; /* hi!"), is(false));
        assertThat(isAPureDocLine("int a; /** hi!"), is(false));
        assertThat(isAPureDocLine("/* foo */ int a;"), is(false));
        assertThat(isAPureDocLine("int a = \"// no komment at all\";"), is(false));
        assertThat(isAPureDocLine("*/"), is(true));
    }
    
    @Test
    public void isFieldDeclarationShouldDo() {
        
        // given
        List<String> code = new ArrayList<>();
        code.add("public class A {");
        code.add("");
        code.add("@B");
        code.add("String c;");
        code.add("/** this is d ;*/");
        code.add("String d;");
        code.add("int e = 5;");
        code.add("private static final int F = 5_000;");
        code.add("private static final Consumer F = () -> whatever();");
        code.add("private class D {");
        code.add("public void b() {");
        code.add("public void b(@Param(\"wtf;omg\") String wtf) {");
        
        // when / then
        assertFalse(isFieldDeclaration(code, 0));
        assertFalse(isFieldDeclaration(code, 1));
        assertFalse(isFieldDeclaration(code, 2));
        assertTrue(isFieldDeclaration(code, 3));
        assertFalse(isFieldDeclaration(code, 4));
        assertTrue(isFieldDeclaration(code, 5));
        assertTrue(isFieldDeclaration(code, 6));
        assertTrue(isFieldDeclaration(code, 7));
        assertTrue(isFieldDeclaration(code, 8));
        assertFalse(isFieldDeclaration(code, 9));
        assertFalse(isFieldDeclaration(code, 10));
        assertFalse(isFieldDeclaration(code, 11));
    }
    
    @Test
    public void isPartOfAMethodShouldDo() {
        
        // given
        List<String> code = new ArrayList<>();
        code.add("public class A {"); // 0
        code.add(""); // 1
        code.add("@B"); // 2
        code.add("String c;"); // 3
        code.add("/** this is d */"); // 4
        code.add("String d;"); // 5
        code.add("public void d() {"); // 6
        code.add("int a = 5;"); // 7
        code.add("if(true){ // hihi {"); // 8
        code.add("while(getFoo()) {"); // 9
        code.add("System.out.println(3);"); // 10
        code.add("}"); // 11
        code.add("}"); // 12
        code.add("switch(HUHU) {"); // 13
        code.add("case 3:"); // 14
        code.add("bla();"); // 15
        code.add(""); // 16
        code.add("break;"); // 17
        code.add("}"); // 18
        code.add("}"); // 19
        code.add(""); // 20
        code.add("static String RICK;"); // 21
        
        // when / then
        assertFalse(isPartOfAMethod(code, 0));
        assertFalse(isPartOfAMethod(code, 1));
        assertFalse(isPartOfAMethod(code, 2));
        assertFalse(isPartOfAMethod(code, 3));
        assertFalse(isPartOfAMethod(code, 4));
        assertFalse(isPartOfAMethod(code, 5));
        assertTrue(isPartOfAMethod(code, 6));
        assertTrue(isPartOfAMethod(code, 7));
        assertTrue(isPartOfAMethod(code, 8));
        assertTrue(isPartOfAMethod(code, 9));
        assertTrue(isPartOfAMethod(code, 10));
        assertTrue(isPartOfAMethod(code, 11));
        assertTrue(isPartOfAMethod(code, 12));
        assertTrue(isPartOfAMethod(code, 13));
        assertTrue(isPartOfAMethod(code, 14));
        assertTrue(isPartOfAMethod(code, 15));
        assertTrue(isPartOfAMethod(code, 16));
        assertTrue(isPartOfAMethod(code, 17));
        assertTrue(isPartOfAMethod(code, 18));
        assertTrue(isPartOfAMethod(code, 19));
        assertFalse(isPartOfAMethod(code, 20));
        assertFalse(isPartOfAMethod(code, 21));
    }
    
    @Test
    public void isPartOfAMethodShouldDoOnCurliesOnNextLine() {
        
        // given
        List<String> code = new ArrayList<>();
        code.add("static String RICK;"); // 0
        code.add("void efg()"); // 1
        code.add("{"); // 2
        code.add("}"); // 3
        code.add("static String RICK;"); // 4
        
        // when / then
        assertFalse(isPartOfAMethod(code, 0));
        assertTrue(isPartOfAMethod(code, 1));
        assertTrue(isPartOfAMethod(code, 2));
        assertTrue(isPartOfAMethod(code, 3));
        assertFalse(isPartOfAMethod(code, 4));
    }
    
    @Test
    public void isPartOfAMethodShouldDoOnOneliner() {
        
        // given
        List<String> code = new ArrayList<>();
        code.add("static String RICK;"); // 0
        code.add("void a(){}"); // 1
        code.add("static String RICK;"); // 2
        code.add("void b(){}"); // 3
        code.add("void c(){}"); // 4
        code.add("static String BERTA;"); // 5
        
        // when / then
        assertFalse(isPartOfAMethod(code, 0));
        assertTrue(isPartOfAMethod(code, 1));
        assertFalse(isPartOfAMethod(code, 2));
        assertTrue(isPartOfAMethod(code, 3));
        assertTrue(isPartOfAMethod(code, 4));
        assertFalse(isPartOfAMethod(code, 5));
    }
    
    @Test
    public void isFirstLineOfDocShouldDo() {
        
        // given
        List<String> code = new ArrayList<>();
        code.add(" /*");
        code.add(" foo ");
        code.add("bar */");
        code.add("String a;");
        code.add("/** this is b */");
        code.add("String b;");
        code.add("// horray for c");
        code.add("String c;");
        
        // when / then
        assertTrue(isFirstLineOfDoc(code, 0));
        assertFalse(isFirstLineOfDoc(code, 1));
        assertFalse(isFirstLineOfDoc(code, 2));
        assertFalse(isFirstLineOfDoc(code, 3));
        assertTrue(isFirstLineOfDoc(code, 4));
        assertFalse(isFirstLineOfDoc(code, 5));
        assertTrue(isFirstLineOfDoc(code, 6));
        assertFalse(isFirstLineOfDoc(code, 7));
    }
    
    @Test
    public void containsDocShouldDo() {
        
        // given
        List<String> code = new ArrayList<>();
        code.add(" /*");
        code.add(" foo ");
        code.add("bar */");
        code.add("String a;");
        code.add("/** this is b */");
        code.add("String b;");
        code.add("// horray for c");
        code.add("String c;");
        
        // when / then
        assertTrue(containsDoc(code, 0));
        assertTrue(containsDoc(code, 1));
        assertTrue(containsDoc(code, 2));
        assertFalse(containsDoc(code, 3));
        assertTrue(containsDoc(code, 4));
        assertFalse(containsDoc(code, 5));
        assertTrue(containsDoc(code, 6));
        assertFalse(containsDoc(code, 7));
    }
    
    @Test
    public void hasDocShouldDo() {
        
        // given
        List<String> code = new ArrayList<>();
        code.add(" /*");
        code.add(" foo ");
        code.add("bar */");
        code.add("String a;");
        code.add("/** this is b */");
        code.add("@Deprecated");
        code.add("String b;");
        code.add("// horray for c");
        code.add("@Autowired");
        code.add("@Deprecated");
        code.add("String c;");
        
        // when / then
        assertFalse(hasDoc(code, 0));
        assertFalse(hasDoc(code, 1));
        assertFalse(hasDoc(code, 2));
        assertTrue(hasDoc(code, 3));
        assertFalse(hasDoc(code, 4));
        assertTrue(hasDoc(code, 5));
        assertTrue(hasDoc(code, 6));
        assertFalse(hasDoc(code, 7));
    }
    
    @Test
    public void isMethodDeclarationShouldDoOnPublicVoid() {
        
        // given
        List<String> code = new ArrayList<>();
        code.add("public void a(){");
        code.add("b();");
        code.add("}");
        
        // when / then
        assertTrue(isMethodDeclaration(code, 0));
        assertFalse(isMethodDeclaration(code, 1));
        assertFalse(isMethodDeclaration(code, 2));
    }
    
    @Test
    public void isMethodDeclarationShouldDoOnCurlyBraceNextLine() {
        
        // given
        List<String> code = new ArrayList<>();
        code.add("public void a()");
        code.add("{");
        code.add("b();");
        code.add("}");
        
        // when / then
        assertTrue(isMethodDeclaration(code, 0));
        assertFalse(isMethodDeclaration(code, 1));
        assertFalse(isMethodDeclaration(code, 2));
        assertFalse(isMethodDeclaration(code, 3));
    }
    
    @Test
    public void isMethodDeclarationShouldDoOnOneliner() {
        
        // given
        List<String> code = new ArrayList<>();
        code.add("public void a(){}");
        code.add("private int b;");
        code.add("public void c(){}");
        code.add("private int d;");
        code.add("public void e(){}");
        code.add("private int f;");
        
        // when / then
        assertTrue(isMethodDeclaration(code, 0));
        assertFalse(isMethodDeclaration(code, 1));
        assertTrue(isMethodDeclaration(code, 2));
        assertFalse(isMethodDeclaration(code, 3));
        assertTrue(isMethodDeclaration(code, 4));
        assertFalse(isMethodDeclaration(code, 5));
    }
    
    @Test
    public void isMethodDeclarationShouldReturnFalseOnThat() {
        assertFalse(isMethodDeclaration("public class Foo {"));
        assertFalse(isMethodDeclaration("int i = 1"));
        assertFalse(isMethodDeclaration("int i = 1,"));
        assertFalse(isMethodDeclaration("int i = 1;"));
    }
    
    @Test
    public void isConstructorDeclarationShouldDo() {
        assertFalse(isConstructorDeclaration("public class Foo {"));
        assertFalse(isConstructorDeclaration("int i = 1"));
        assertFalse(isConstructorDeclaration("public void foo()"));
        assertFalse(isConstructorDeclaration("lines.add(\"if(true){\");"));
        assertTrue(isConstructorDeclaration("Foo(){"));
        assertTrue(isConstructorDeclaration("Foo(String ... args){"));
        
        assertTrue(isConstructorDeclaration("public Foo(String ... args){"));
    }
    
    @Test
    public void isMethodDeclarationShouldDoOnProtectedFinalDate() {
        
        // given
        List<String> code = new ArrayList<>();
        code.add("protected final Date a(){");
        code.add("b();");
        code.add("}");
        
        // when / then
        assertTrue(isMethodDeclaration(code, 0));
        assertFalse(isMethodDeclaration(code, 1));
        assertFalse(isMethodDeclaration(code, 2));
    }
    
    @Test
    public void isMethodDeclarationShouldDoOnPrivateGenericProtectedFinalObject() {
        
        // given
        List<String> code = new ArrayList<>();
        code.add("public static <E extends Number> void someMethod_\uD83D\uDE0B_(List<E> a, String ... c) throws Bla, Blub<Z> {");
        code.add("b();");
        code.add("}");
        
        // when / then
        assertTrue(isMethodDeclaration(code, 0));
        assertFalse(isMethodDeclaration(code, 1));
        assertFalse(isMethodDeclaration(code, 2));
    }
    
    @Test
    public void isMethodDeclarationShouldDoOnPackagePrivateGenericProtectedFinalObject() {
        
        // given
        List<String> code = new ArrayList<>();
        code.add("Object a(){");
        code.add("b();");
        code.add("}");
        
        // when / then
        assertTrue(isMethodDeclaration(code, 0));
        assertFalse(isMethodDeclaration(code, 1));
        assertFalse(isMethodDeclaration(code, 2));
    }
    
    @Test
    public void debug201610201912() {
        assertFalse(isMethodDeclaration("lines.add(\"abc\");"));
        assertFalse(isMethodDeclaration("lines.add(\"a()\");"));
        assertFalse(isMethodDeclaration("lines.add(\"    lines.add(\\\"if(true){\\\");\");"));
    }
    
    @Test
    public void isMethodDeclarationShouldDoOnDoEverything() {
        
        // given
        List<String> code = new ArrayList<>();
        code.add("@Autowired");
        code.add("<T> static Object<U> a(final String a, final Date<WTF> b){");
        code.add("b();");
        code.add("}");
        
        // when / then
        assertFalse(isMethodDeclaration(code, 0));
        assertTrue(isMethodDeclaration(code, 1));
        assertFalse(isMethodDeclaration(code, 2));
        assertFalse(isMethodDeclaration(code, 3));
    }
    
    @Test
    public void killStringsShouldDo() {
        assertThat(killStrings("\"lala\""), is(""));
        assertThat(killStrings("\"lala\"lala"), is("lala"));
        assertThat(killStrings("\"lala\\\"lala\""), is(""));
        assertThat(killStrings("abc\"def\"ghi"), is("abcghi"));
        assertThat(killStrings("abc\"def\"ghi and 123\"456\"789"), is("abcghi and 123789"));
    }
    
    @Test
    public void isBlockCloseShouldDo() {
        assertTrue(isBlockClose("}"));
        assertFalse(isBlockClose("\"}\""));
        assertFalse(isBlockClose("// }"));
        assertFalse(isBlockClose("/* } */"));
        assertFalse(isBlockClose("/* } "));
        assertFalse(isBlockClose("/* }}} */"));
        assertFalse(isBlockClose("/* }}/* } */ */"));
        assertFalse(isBlockClose("\"\\\"}\""));
        assertTrue(isBlockClose("} \"}\""));
        assertTrue(isBlockClose("\"}\"}"));
        assertTrue(isBlockClose("}//"));
        assertTrue(isBlockClose("\"}\"}\"}\""));
        assertTrue(isBlockClose("/*}*/}\"}\""));
        assertTrue(isBlockClose("/*}*/}/*}*/"));
        assertFalse(isBlockClose("'}'"));
        assertTrue(isBlockClose("'}'}"));
        assertTrue(isBlockClose("'}'}'}'"));
        assertFalse(isBlockClose("'}' + '}'"));
    }
    
    @Test
    public void isBlockStartShouldDo() {
        assertTrue(isBlockStart("{"));
        assertFalse(isBlockStart("\"{\""));
        assertFalse(isBlockStart("// {"));
        assertFalse(isBlockStart("/* { */"));
        assertFalse(isBlockStart("/* { "));
        assertFalse(isBlockStart("/* {{{ */"));
        assertFalse(isBlockStart("/* {{/* } */ */"));
        assertFalse(isBlockStart("\"\\\"{\""));
        assertTrue(isBlockStart("{ \"{\""));
        assertTrue(isBlockStart("\"{\"{"));
        assertTrue(isBlockStart("{//"));
        assertTrue(isBlockStart("\"{\"{\"{\""));
        assertTrue(isBlockStart("/*{*/{\"{\""));
        assertTrue(isBlockStart("/*{*/{/*{*/"));
        assertFalse(isBlockStart("'{'"));
        assertTrue(isBlockStart("'{'{"));
        assertTrue(isBlockStart("'{'{'{'"));
        assertFalse(isBlockStart("'{' + '{'"));
    }
    
    @Test
    public void isFirstAnnotationOfMethodShouldDo() {
        
        // given
        List<String> lines = new ArrayList<>();
        lines.add("@Override");
        lines.add("@Bean");
        lines.add("public Mongo mongo() throws Exception {");
        lines.add("    return new MongoClient(\"127.0.0.1\");");
        lines.add("}");
        
        // when ... then
        assertThat(isFirstAnnotationOfMethod(lines, 0), is(true));
        assertThat(isFirstAnnotationOfMethod(lines, 1), is(false));
    }
    
    @Test
    public void isMethodDeclarationShouldDo() {
        
        // given
        List<String> lines = new ArrayList<>();
        lines.add("@Bean");
        lines.add("public Mongo mongo() throws Exception {");
        lines.add("System.out.print(\"public Mongo mongo() throws Exception {\"");
        lines.add("    return new MongoClient(\"127.0.0.1\");");
        lines.add("}");
        
        // when ... then
        assertThat(isMethodDeclaration(lines, 0), is(false));
        assertThat(isMethodDeclaration(lines, 1), is(true));
        assertThat(isMethodDeclaration(lines, 2), is(false));
        assertThat(isMethodDeclaration(lines, 3), is(false));
        assertThat(isMethodDeclaration(lines, 4), is(false));
    }
    
    @Test
    public void isClassDeclarationShouldDo() {
        
        // given
        List<String> lines = new ArrayList<>();
        lines.add("import com.mongodb.MongoClient;");
        lines.add("@Configuration");
        lines.add("class SpringMongoConfig extends AbstractMongoConfig {");
        
        lines.add("   public static class SpringMongoConfig extends AbstractMongoConfig { // hihi");
        lines.add("}");
        lines.add("}");
        
        // when ... then
        assertThat(isClassDeclaration(lines, 0), is(false));
        assertThat(isClassDeclaration(lines, 1), is(false));
        assertThat(isClassDeclaration(lines, 2), is(true));
        assertThat(isClassDeclaration(lines, 3), is(true));
        assertThat(isClassDeclaration(lines, 4), is(false));
        assertThat(isClassDeclaration(lines, 5), is(false));
    }
    
    @Test
    public void isAnnotationShouldDo() {
        
        // given
        List<String> lines = new ArrayList<>();
        lines.add(" import com.mongodb.MongoClient;");
        lines.add("  @Configuration");
        lines.add("class SpringMongoConfig extends AbstractMongoConfig {");
        lines.add("}");
        
        // when ... then
        assertThat(isAnnotation(lines, 1), is(true));
    }
    
    @Test
    public void hasAnnotationShouldDo() {
        
        // given
        List<String> lines = new ArrayList<>();
        lines.add("import com.mongodb.MongoClient;");
        lines.add("@Deprecated");
        lines.add("@Configuration");
        lines.add("class SpringMongoConfig extends AbstractMongoConfig {");
        lines.add("}");
        
        // when ... then
        assertThat(hasAnnotation(lines, 0), is(false));
        assertThat(hasAnnotation(lines, 1), is(false));
        assertThat(hasAnnotation(lines, 2), is(false));
        assertThat(hasAnnotation(lines, 3), is(true));
        assertThat(hasAnnotation(lines, 4), is(false));
    }
    
    @Test
    public void isImportShouldDo() {
        
        // given
        List<String> lines = new ArrayList<>();
        lines.add("import com.mongodb.MongoClient;");
        lines.add("import static a.b.c.*;");
        lines.add("  import com.mongodb.MongoClient; // lala");
        lines.add("  import static a.b.c.*; // huhu");
        lines.add("  /* mean */    import static a.b.c.*; // huhu");
        lines.add("  /* mean */import static a.b.c.*;// huhu");
        lines.add("/**/import static a.b.c.*;/**/");
        
        // when ... then
        assertThat(isImport(lines, 0), is(true));
        assertThat(isImport(lines, 1), is(true));
        assertThat(isImport(lines, 2), is(true));
        assertThat(isImport(lines, 3), is(true));
        assertThat(isImport(lines, 4), is(true));
        assertThat(isImport(lines, 5), is(true));
        assertThat(isImport(lines, 6), is(true));
    }
    
    @Test
    public void isStaticImportShouldDo() {
        
        // given
        List<String> lines = new ArrayList<>();
        lines.add("import com.mongodb.MongoClient;");
        lines.add("import static a.b.c.*;");
        
        // when ... then
        assertThat(isStaticImport(lines, 0), is(false));
        assertThat(isStaticImport(lines, 1), is(true));
    }
    
    @Test
    public void isFirstAnnotationOfClassEnumOrInterfaceShouldDo() {
        
        // given
        List<String> lines = new ArrayList<>();
        lines.add("import com.mongodb.MongoClient;");
        lines.add("@Configuration");
        lines.add("@Configuration");
        lines.add("class SpringMongoConfig extends AbstractMongoConfig {");
        lines.add("}");
        
        // when ... then
        assertThat(isFirstAnnotationOfClassEnumOrInterface(lines, 0), is(false));
        assertThat(isFirstAnnotationOfClassEnumOrInterface(lines, 1), is(true));
        assertThat(isFirstAnnotationOfClassEnumOrInterface(lines, 2), is(false));
        assertThat(isFirstAnnotationOfClassEnumOrInterface(lines, 3), is(false));
        assertThat(isFirstAnnotationOfClassEnumOrInterface(lines, 4), is(false));
    }
    
    @Test
    public void isStartOfIfShouldDo() {
        assertTrue(isStartOfIf("if(bla)"));
        assertTrue(isStartOfIf("if (bla)"));
        assertTrue(isStartOfIf("if (this.foo() != 23 + 'D')"));
        assertTrue(isStartOfIf("if (this.foo()){"));
        assertFalse(isStartOfIf("void swift (this.foo()){"));
        assertFalse(isStartOfIf("void swif(true)"));
    }
}