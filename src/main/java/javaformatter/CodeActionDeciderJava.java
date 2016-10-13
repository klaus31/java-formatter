package javaformatter;

class CodeActionDeciderJava implements CodeActionDecider {

    @Override
    public String getEol() {
        return "\n";
    }

    @Override
    public String getIndent() {
        return "  ";
    }

    @Override
    public int tabChangeNextLine(String line) {
        if(line.matches("^.+\\{$")) {
            return 1;
        }
        return 0;
    }

    @Override
    public int tabChangeThisLine(String line) {
        if(line.matches(".*\\}$")) {
            return -1;
        }
        return 0;
    }

    public int blankLinesBefore(String line, String prevLine) {
        if(line.matches("^public.*") && !prevLine.matches("^@.*")) {
            return 1;
        }
        if(line.matches("^protected.*") && !prevLine.matches("^@.*")) {
            return 1;
        }
        if(line.matches("^private.*") && !prevLine.matches("^@.*")) {
            return 1;
        }
        if(line.matches("^@.*") && !prevLine.matches("^@.*")) {
            return 1;
        }
        if(line.matches("^class .*") && !prevLine.matches("^@.*")) {
            return 1;
        }
        return 0;
    }

    public int blankLinesAfter(String line) {
        if(line.matches("^package.*")) {
            return 1;
        }
        return 0;
    }
}
