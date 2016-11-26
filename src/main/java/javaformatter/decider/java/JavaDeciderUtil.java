package javaformatter.decider.java;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import static org.apache.commons.lang3.StringUtils.countMatches;
import static org.apache.commons.lang3.StringUtils.join;

/**
 * First line: lineNumber == 0 !!!
 */
class JavaDeciderUtil {

    private JavaDeciderUtil() {
    }

    static boolean isAnnotation(List<String> lines, int lineNumber) {
        return isAnnotation(lines.get(lineNumber));
    }

    static boolean hasAnnotation(List<String> lines, int lineNumber) {
        return lineNumber != 0 && !isAnnotation(lines, lineNumber) && isAnnotation(lines, lineNumber - 1);
    }

    static boolean isAnnotation(String line) {
        return line.matches("^\\s*@.*");
    }

    static boolean isImport(List<String> lines, int lineNumber) {
        return isImport(lines.get(lineNumber));
    }

    private static boolean matches(String line, String regex) {
        String optionalStart = "^\\s*(/\\*.*\\*/)?\\s*";
        String optionalEnd = "\\s*(//.*|/\\*.*)?\\s*$";
        return line.matches(optionalStart + regex + optionalEnd);
    }

    private static boolean matches(List<String> lines, int lineNumber, String regex) {
        return matches(lines.get(lineNumber), regex);
    }

    static boolean isStaticImport(List<String> lines, int lineNumber) {
        return matches(lines, lineNumber, "import\\s+static\\s+\\S+\\s*;");
    }

    static boolean isMethodDeclaration(String line) {
        line = killComments(line);
        boolean isAbstract = (" " + line).contains(" abstract ");
        line = killOccurrences(line, "(public|private|protected|static|final|native|synchronized|abstract|transient|default)");
        line = killOccurrences(line, "\\[\\s*\\]");
        line = killOccurrences(line, "<[^<>]*>");
        line = killOccurrences(line, "\\.\\.\\.");
        line = killStrings(line);
        if (matches(line, ".*\\.[a-z].*\\(.*")) return false; // a method call
        if (isAbstract) {
            return matches(line.trim(), "[a-zA-Z][^\\s\\.]*\\s+\\S+\\([^\\(\\)]*\\)\\s*(throws\\s+[^\\{\\;]*)?\\;$");
        } else {
            return matches(line.trim(), "[a-zA-Z][^\\s\\.]*\\s+\\S+\\([^\\(\\)]*\\)\\s*(throws\\s+[^\\{\\;]*)?(\\{.*)?");
        }
    }

    static boolean isConstructorDeclaration(String line) {
        line = killComments(line);
        line = killOccurrences(line, "(public|private|protected|final)");
        line = killOccurrences(line, "\\[\\s*\\]");
        line = killOccurrences(line, "<[^<>]*>");
        line = killOccurrences(line, "\\.\\.\\.");
        return matches(line.trim(), "[A-Z][^\\s\\.]*\\([^\\(\\)]*\\)\\s*(throws\\s+[^\\{]*)?(\\{.*)?");
    }

    static boolean isMethodDeclaration(List<String> lines, int lineNumber) {
        return isMethodDeclaration(lines.get(lineNumber));
    }

    static boolean isFirstLineOfDoc(List<String> lines, final int lineNumber) {
        return lines.get(lineNumber).trim().matches("(/\\*|//).*");
    }

    static boolean isEndOfStatement(final String line) {
        return isAnnotation(line) || isAPureDocLine(line) || line.trim().endsWith(";") || line.trim().endsWith("{") || line.trim().endsWith("}");
    }

    static boolean isAPureDocLine(final String line) {
        String l = killComments(line.trim());
        return l.isEmpty() || l.matches("^\\*.*") || l.matches(".*\\*/\\s*$");
    }

    static boolean hasDoc(List<String> lines, final int lineNumber) {
        int i = lineNumber - 1;
        return i >= 0 && (isAnnotation(lines.get(i)) ? hasDoc(lines, i): containsDoc(lines, i) && !containsDoc(lines, i + 1));
    }

    static boolean containsDoc(String line) {
        String checkstr = killStrings(line.trim());
        if (checkstr.matches(".*\\*/")) return true;
        if (checkstr.matches(".*/\\*.*")) return true;
        if (checkstr.matches(".*//.*")) return true;
        else return false;
    }

    // TODO inaccurate and bad performance yet
    static boolean containsDoc(List<String> lines, final int lineNumber) {
        int i = lineNumber;
        while (i >= 0) {
            String line = killStrings(lines.get(i).trim());
            line = killChars(line);
            if (line.matches(".*\\*/")) return i == lineNumber;
            if (line.matches(".*/\\*.*")) return true;
            if (line.matches(".*//.*")) return i == lineNumber;
            i--;
        }
        return false;
    }

    static boolean isFieldDeclaration(List<String> lines, final int lineNumber) {
        String line = killStringsCharsAndComments(lines.get(lineNumber)).trim();
        return line.replaceAll("\\s", "").matches("[a-zA-Z0-9<>\\[\\]]+(=\\S+)?;$") && !isPartOfAMethod(lines, lineNumber) && !isPackageDeclaration(line) && !isImport(lines, lineNumber) && !isPartOfAConstructor(lines, lineNumber);
    }

    static boolean isPartOfAMethod(List<String> lines, final int lineNumber) {
        int startOfDeclaration = -1;
        for (int i = lineNumber; i > 0; i--) {
            if (isMethodDeclaration(lines, i)) {
                startOfDeclaration = i;
                break;
            }
        }
        if (startOfDeclaration >= 0) {
            int curlyBraces = 0;
            int endOfDeclaration = startOfDeclaration;
            String line = "";
            for (; curlyBraces == 0 && endOfDeclaration < lines.size(); endOfDeclaration++) {
                line = killStringsCharsAndComments(lines.get(endOfDeclaration));
                curlyBraces += countMatches(line, "{");
            }
            curlyBraces -= countMatches(line, "}");
            for (; curlyBraces != 0 && endOfDeclaration < lines.size(); endOfDeclaration++) {
                line = killStringsCharsAndComments(lines.get(endOfDeclaration));
                curlyBraces += countMatches(line, "{");
                curlyBraces -= countMatches(line, "}");
            }
            return lineNumber >= startOfDeclaration && lineNumber < endOfDeclaration;
        }
        return false;
    }

    private static boolean isPartOfAConstructor(List<String> lines, final int lineNumber) {
        int startOfDeclaration = -1;
        for (int i = lineNumber; i > 0; i--) {
            if (isConstructorDeclaration(lines, i)) {
                startOfDeclaration = i;
                break;
            }
        }
        if (startOfDeclaration >= 0) {
            int curlyBraces = 0;
            int endOfDeclaration = startOfDeclaration;
            String line = "";
            for (; curlyBraces == 0 && endOfDeclaration < lines.size(); endOfDeclaration++) {
                line = killStringsCharsAndComments(lines.get(endOfDeclaration));
                curlyBraces += countMatches(line, "{");
            }
            curlyBraces -= countMatches(line, "}");
            for (; curlyBraces != 0 && endOfDeclaration < lines.size(); endOfDeclaration++) {
                line = killStringsCharsAndComments(lines.get(endOfDeclaration));
                curlyBraces += countMatches(line, "{");
                curlyBraces -= countMatches(line, "}");
            }
            return lineNumber >= startOfDeclaration && lineNumber < endOfDeclaration;
        }
        return false;
    }

    static boolean isConstructorDeclaration(List<String> lines, int lineNumber) {
        return isConstructorDeclaration(lines.get(lineNumber));
    }

    static boolean isBlockClose(String line) {
        return !isAPureDocLine(line) && killStringsCharsAndComments(line).contains("}");
    }

    private static String killStringsCharsAndComments(String line) {
        String lineToSearchIn = killStrings(line);
        lineToSearchIn = killChars(lineToSearchIn);
        return killComments(lineToSearchIn);
    }

    private static String killChars(String line) {
        return killOccurrences(line, "'[^']*'");
    }

    private static String killOccurrences(String line, String regex) {
        String tmp = line.replaceFirst(regex, "");
        while (!tmp.equals(tmp.replaceFirst(regex, ""))) {
            tmp = tmp.replaceFirst(regex, "");
        }
        return tmp;
    }

    private static String killComments(String lineToSearchIn) {
        String regexSingleLineComment = "//.*";
        lineToSearchIn = lineToSearchIn.replaceAll(regexSingleLineComment, "");
        String regexMultiComment = "/\\*[^\\*/]*\\*/";
        String tmp = killOccurrences(lineToSearchIn, regexMultiComment);
        return tmp.replaceAll("/\\*.*", "");
    }

    static String killStrings(String line) {
        return killOccurrences(line.replaceAll("\\\\\"", ""), "\"[^\"]*[^\\\\]\"");
    }

    static boolean isPackageDeclaration(String line) {
        return line.matches("^\\s*package.*;");
    }

    /**
     * "block" means something starting and ending with curly braces.
     * on line blocks in if clauses or cases in switch statements are not detected.
     */
    static boolean isBlockStart(String line) {
        return !isAPureDocLine(line) && killStringsCharsAndComments(line).contains("{");
    }

    /**
     * typically } else {
     */
    static boolean isBlockCloseBeforeStartingNextBlock(String line) {
        return !isAPureDocLine(line) && killStringsCharsAndComments(line).matches("[^\\}]*\\}\\s*[^\\}\\{]+\\s*\\{.*");
    }

    static boolean isClassDeclaration(List<String> lines, int lineNumber) {
        return matches(lines.get(lineNumber), ".*class\\s+\\S+.*\\{");
    }

    static boolean isEnumDeclaration(List<String> lines, int lineNumber) {
        return matches(killStringsCharsAndComments(lines.get(lineNumber)), ".*enum\\s+\\S+.*\\{.*");
    }

    static boolean isInterfaceDeclaration(List<String> lines, int lineNumber) {
        return matches(lines.get(lineNumber), ".*interface\\s+\\S+.*\\{");
    }

    static boolean isFirstAnnotationOfClassEnumOrInterface(List<String> lines, int lineNumber) {
        String line = lines.get(lineNumber);
        if (isAnnotation(line) && (lineNumber == 0 || !isAnnotation(lines, lineNumber - 1))) {
            int i = lineNumber + 1;
            while (i < lines.size() && isAnnotation(lines.get(i))) {
                i++;
            }
            return isClassDeclaration(lines, i) || isEnumDeclaration(lines, i) || isInterfaceDeclaration(lines, i);
        }
        return false;
    }

    static boolean isFirstAnnotationOfMethod(List<String> lines, int lineNumber) {
        String line = lines.get(lineNumber);
        if (isAnnotation(line) && (lineNumber == 0 || !isAnnotation(lines, lineNumber - 1))) {
            int i = lineNumber + 1;
            while (i < lines.size() && isAnnotation(lines.get(i))) {
                i++;
            }
            return isMethodDeclaration(lines, i);
        }
        return false;
    }

    static boolean isFirstAnnotationOfField(List<String> lines, int lineNumber) {
        String line = lines.get(lineNumber);
        if (isAnnotation(line) && (lineNumber == 0 || !isAnnotation(lines, lineNumber - 1))) {
            int i = lineNumber + 1;
            while (i < lines.size() && isAnnotation(lines.get(i))) {
                i++;
            }
            return isFieldDeclaration(lines, i);
        }
        return false;
    }

    static boolean isStartOfIf(String line) {
        return matches(line, "if\\s*\\(.+\\).*");
    }

    static String withPartsInLineNotBeingAString(String line, Function<String, String> format) {

        // build array with even indexes == non-strings and odd indexes == strings
        List<String> lineSplitAtStrings = new ArrayList<>();
        String[] dirtySplitsAtQuotes = line.split("\"", -1);
        int i = 0;
        while (i < dirtySplitsAtQuotes.length) {
            String lineSplit = dirtySplitsAtQuotes[i];

            // put slashed quotes into its string again
            while (lineSplit.matches(".*\\\\$")) {
                if (i + 1 < dirtySplitsAtQuotes.length) {
                    i++;
                    lineSplit += "\"" + dirtySplitsAtQuotes[i];
                } else {
                    break;
                }
            }

            // put slashed quotes into its char again
            boolean falseAlarm = false;
            while (lineSplit.matches(".*'$") && !falseAlarm) {
                if (i + 1 < dirtySplitsAtQuotes.length && dirtySplitsAtQuotes[i + 1].matches("^'.*")) {
                    i++;
                    lineSplit += "\"" + dirtySplitsAtQuotes[i];
                } else {
                    falseAlarm = true;
                }
            }
            lineSplitAtStrings.add(lineSplit);
            i++;
        }

        // replace double whitespaces in non-strings (even indexes)
        List<String> splittedResults = new ArrayList<>();
        for (int j = 0; j < lineSplitAtStrings.size(); j++) {
            if (j % 2 == 0) {
                String result = lineSplitAtStrings.get(j);
                splittedResults.add(format.apply(result));
            } else {
                splittedResults.add(lineSplitAtStrings.get(j));
            }
        }
        return join(splittedResults, "\"");
    }

    static Optional<Integer> calculateNextEndOfMethod(List<String> lines, final int startLineNumber) {
        int i = startLineNumber;
        while (!isMethodDeclaration(lines.get(i)) && i < lines.size()) i++;
        if (i == lines.size()) return Optional.empty();
        else if(isOnelinerMethod(lines, i)) return Optional.of(++i);
        else while (isPartOfAMethod(lines, i)) i++;
        return Optional.of(i);
    }

    private static boolean isOnelinerMethod(List<String> lines, int lineNumber) {

        // FIXME idiot syntax false positive: "void foo() { if(true) { ... }"
        return isMethodDeclaration(lines, lineNumber) && lines.get(lineNumber).trim().matches(".+[\\}\\;]$");
    }

    public static boolean isImport(String line) {
        return matches(line, "import\\s+(static\\s+)?\\S+\\s*;");
    }
}