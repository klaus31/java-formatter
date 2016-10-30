package javaformatter.decider.java;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
}
