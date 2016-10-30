package javaformatter.decider;


import javaformatter.decider.java.JavaDeciderDefault;

public class DeciderSimpleFactory {

    private DeciderSimpleFactory() {
    }

    public static Decider create(String suffix) {
        switch (suffix) {
            case "java":
            return new JavaDeciderDefault();
            default:
            throw new AssertionError(suffix + " not implemented yet");
        }
    }
}