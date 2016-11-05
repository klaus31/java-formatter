package javaformatter.decider.java;

import java.util.List;

class JavaMethod {

    private final List<String> lines;

    JavaMethod(List<String> lines) {
        this.lines = lines;
    }

    String extractMethodDeclaration() {
        return lines.stream().filter(JavaDeciderUtil::isMethodDeclaration).findFirst().orElseThrow(() -> new AssertionError("Method without Method Declaration"));
    }

    public List<String> getLines() {
        return lines;
    }
}