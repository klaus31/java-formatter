package javaformatter.decider.java;

import java.util.Iterator;
import java.util.List;
import org.apache.commons.lang3.Validate;
import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.toList;

class JavaFile {

    private final List<String> lines;

    JavaFile(List<String> lines) {
        this.lines = lines;
    }

    List<JavaClass> extractFirstLevelClasses() {

        // TODO implement me - a java file may have many classes
        return singletonList(new JavaClass(lines));
    }

    List<JavaImport> extractImports() {
        return lines.stream().filter(JavaDeciderUtil::isImport).map(JavaImport::new).collect(toList());
    }

    List<String> replaceImports(List<JavaImport> imports) {
        Iterator<JavaImport> iterator = imports.iterator();
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            if (JavaDeciderUtil.isImport(line)) {
                Validate.isTrue(iterator.hasNext());
                lines.set(i, iterator.next().toString());
            }
        }
        Validate.isTrue(!iterator.hasNext());
        return lines;
    }
}