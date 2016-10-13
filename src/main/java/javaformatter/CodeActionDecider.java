package javaformatter;

interface CodeActionDecider {
    int tabChangeThisLine(String line);

    String getIndent();

    int tabChangeNextLine(String line);

    int blankLinesBefore(String line, String prevLine);

    int blankLinesAfter(String line);

    String getEol();
}
