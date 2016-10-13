package javaformatter;

import java.util.Collections;
import java.util.List;

interface CodeActionDecider {
    int tabChangeThisLine(String line);

    default String getEol() {
        return "\n"; // 🐧
    }

    default String getIndent() {
        return "  ";
    }

    int tabChangeNextLine(String line);

    int blankLinesBefore(List<String> lines, int lineNumber);

    int blankLinesAfter(String line);

    /**
     * part of the prepare process: Do want to kill before formatting the code? return true then.
     * <p>
     * default: kill just whitespace lines
     */
    default boolean killLine(List<String> lines, int lineNumber) {
        return lines.get(lineNumber).trim().isEmpty();
    }

    /**
     * do want to split lines? do it here!
     * <p>
     * default: trim given line and let it unchanged
     */
    default List<String> preProcessSingleLine(List<String> lines, int lineNumber) {
        return Collections.singletonList(lines.get(lineNumber).trim());
    }

    /**
     * after code is formatted, you can do other things on the formatted code here (like reorder imports or destroy everything).
     * <p>
     * default: do nothing
     */
    default List<String> postProcessFormattedLines(List<String> lines) {
        return lines;
    }
}
