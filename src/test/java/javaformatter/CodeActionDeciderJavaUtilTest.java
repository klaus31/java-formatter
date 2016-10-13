package javaformatter;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import static javaformatter.CodeActionDeciderJavaUtil.*;

public class CodeActionDeciderJavaUtilTest {

    @Test
    public void isFirstAnnotationOfMethodShouldDo() throws Exception {
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
    public void isMethodDeclarationShouldDo() throws Exception {
        // given
        List<String> lines = new ArrayList<>();
        lines.add("@Override");
        lines.add("@Bean");
        lines.add("public Mongo mongo() throws Exception {");
        lines.add("    return new MongoClient(\"127.0.0.1\");");
        lines.add("}");
        // when ... then
        assertThat(isMethodDeclaration(lines, 2), is(true));
    }

    @Test
    public void isClassDeclarationShouldDo() throws Exception {
        // given
        List<String> lines = new ArrayList<>();
        lines.add("import com.mongodb.MongoClient;");
        lines.add("@Configuration");
        lines.add("class SpringMongoConfig extends AbstractMongoConfig {");
        lines.add("}");
        // when ... then
        assertThat(isClassDeclaration(lines, 2), is(true));
    }

    @Test
    public void isAnnotationShouldDo() throws Exception {
        // given
        List<String> lines = new ArrayList<>();
        lines.add("import com.mongodb.MongoClient;");
        lines.add("@Configuration");
        lines.add("class SpringMongoConfig extends AbstractMongoConfig {");
        lines.add("}");
        // when ... then
        assertThat(isAnnotation(lines, 1), is(true));
    }

    @Test
    public void hasAnnotationShouldDo() throws Exception {
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
    public void isFirstAnnotationOfClassEnumOrInterfaceShouldDo() throws Exception {
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

}