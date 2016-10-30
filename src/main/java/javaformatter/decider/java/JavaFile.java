package javaformatter.decider.java;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.lang3.Validate;
import static javaformatter.decider.java.JavaDeciderUtil.isMethodDeclaration;

class JavaFile {

    private final List<String> lines;

    public JavaFile(List<String> lines) {
        this.lines = lines;
    }

    public List<JavaClass> extractClasses() {

        // TODO implement me - a java file may have many classes
        return Arrays.asList(new JavaClass(lines));
    }

    public List<JavaImport> extractImports() {
        List<JavaImport> imports = new ArrayList<>();
        for (String line : lines) {
            if (JavaDeciderUtil.isImport(line)) {
                imports.add(new JavaImport(line));
            }
        }
        return imports;
    }

    public List<String> replaceImports(List<JavaImport> imports) {
        Iterator<JavaImport> iterator = imports.iterator();
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            if (JavaDeciderUtil.isImport(line)) {
                Validate.isTrue(iterator.hasNext());
                lines.set(i, iterator.next().toString());
            }
        }
        Validate.isTrue(!iterator.hasNext());
        return null;
    }
}