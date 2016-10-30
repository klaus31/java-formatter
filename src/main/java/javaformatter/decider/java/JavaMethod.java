package javaformatter.decider.java;

import java.util.List;

class JavaMethod {

    private final List<String> lines;

    public JavaMethod(List<String> lines) {
        this.lines = lines;
    }

    public String extractMethodDeclaration() {
        for (String line : lines) {
            if (JavaDeciderUtil.isMethodDeclaration(line)) return line;
        }
        throw new AssertionError("Method without Method Declaration");
    }

    public List<String> getLines() {
        return lines;
    }
}