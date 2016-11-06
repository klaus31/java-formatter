package javaformatter.decider.java;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import javaformatter.decider.Decider;
import static java.util.stream.Collectors.toList;
import static javax.swing.UIManager.get;

abstract class JavaDecider implements Decider {

    public List<String> preProcessLines(List<String> lines) {
        lines = meltThingsTogetherInOneLine(lines);
        orderComponentsInFile(lines);
        return lines.stream().map(String::trim).map(this::killDoubleSpaces).map(this::putInSingleSpaces).collect(toList());
    }

    protected abstract List<String> meltThingsTogetherInOneLine(List<String> lines);

    private void orderComponentsInFile(List<String> lines) {
        JavaFile javaFile = new JavaFile(lines);
        List<JavaImport> imports = javaFile.extractImports();
        Collections.sort(imports, this::compareImports);
        lines = javaFile.replaceImports(imports);
        List<JavaClass> firstLevelClasses = javaFile.extractFirstLevelClasses();
        for (JavaClass firstLevelClass : firstLevelClasses) {
            List<JavaMethod> methods = firstLevelClass.extractMethods();
        }
    }

    abstract int compareImports(JavaImport importA, JavaImport importB);

    abstract String putInSingleSpaces(String line);

    String killDoubleSpaces(String line) {
        if (JavaDeciderUtil.isAPureDocLine(line)) return line;
        return JavaDeciderUtil.withPartsInLineNotBeingAString(line, part -> {
            String tmp;
            do {
                tmp = part;
                part = part.replaceAll("\\s\\s", " ");
            } while (!tmp.equals(part));
            return part;
        });
    }
}