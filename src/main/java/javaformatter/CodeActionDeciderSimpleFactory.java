package javaformatter;

class CodeActionDeciderSimpleFactory {

    private CodeActionDeciderSimpleFactory() {
    }

    static CodeActionDecider create(String suffix) {
        switch (suffix) {
            case "java":
                return new CodeActionDeciderJava();
            default:
                throw new AssertionError(suffix + " not implemented yet");
        }
    }
}
