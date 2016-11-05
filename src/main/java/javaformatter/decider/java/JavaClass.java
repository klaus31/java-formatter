package javaformatter.decider.java;

import java.util.ArrayList;
import java.util.List;
import static javaformatter.decider.java.JavaDeciderUtil.isMethodDeclaration;

class JavaClass {

    private final List<String> lines;

    JavaClass(List<String> lines) {
        this.lines = lines;
    }

    List<JavaMethod> extractMethods() {
        List<JavaMethod> result = new ArrayList<>();
        for (int lineNumber = 0; lineNumber < lines.size(); lineNumber++) {
            String line = lines.get(lineNumber);
            int lineNumberOfStartOfMethod = lineNumber;
            if (isMethodDeclaration(line)) {
                List<String> methodLines = new ArrayList<>();
                if (JavaDeciderUtil.hasDoc(lines, lineNumber)) {
                    while (!JavaDeciderUtil.isFirstLineOfDoc(lines, --lineNumberOfStartOfMethod));
                } else if (JavaDeciderUtil.hasAnnotation(lines, lineNumber)) {
                    while (!JavaDeciderUtil.isFirstAnnotationOfMethod(lines, --lineNumberOfStartOfMethod));
                }
                int lineNumberOfEndOfMethod = JavaDeciderUtil.calculateNextEndOfMethod(lines, lineNumber).orElseThrow(AssertionError::new);
                while (lineNumberOfStartOfMethod < lineNumberOfEndOfMethod) {
                    methodLines.add(lines.get(lineNumberOfStartOfMethod++));
                }
                result.add(new JavaMethod(methodLines));
            }
        }
        return result;
    }
}