package javaformatter.decider.java;

public class JavaImport {

    private final String line;

    public JavaImport(String line) {
        this.line = line;
    }

    public boolean isStatic() {
        return line.contains(" static ");
    }

    @Override
    public String toString() {
        return line;
    }
}