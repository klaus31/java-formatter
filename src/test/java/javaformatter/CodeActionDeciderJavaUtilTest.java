package javaformatter;

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
//        assertTrue(isBlockClose("/*}*/}/*}*/"));
//        assertTrue(isBlockClose("/*}*/}\"}\""));
//        assertTrue(isBlockClose("\"}\"}\"}\""));
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