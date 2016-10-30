package javaformatter.decider.java;

import java.util.Collections;
import java.util.List;
import javaformatter.decider.Decider;
import static java.util.stream.Collectors.toList;

abstract class JavaDecider implements Decider {

    public List<String> preProcessLines(List<String> lines) {
        orderComponentsInFile(lines);

        return lines.stream()
        .map(String::trim)
        .map(this::killDoubleSpaces)
        .map(this::putInSingleSpaces)
        .collect(toList());
    }

    private void orderComponentsInFile(List<String> lines) {
        JavaFile extractor = new JavaFile(lines);
        List<JavaImport> imports = extractor.extractImports();
        Collections.sort(imports, this::compareImports);
        lines = extractor.replaceImports(imports);

        //        List<JavaMethod> methods = extractor.extractClasses().get(0).extractMethods();
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