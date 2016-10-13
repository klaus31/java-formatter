package javaformatter;

import java.util.List;

/**
 * First line: lineNumber == 0 !!!
 */
class CodeActionDeciderJavaUtil {

    private CodeActionDeciderJavaUtil() {
    }

    static boolean isAnnotation(List<String> lines, int lineNumber) {
        return isAnnotation(lines.get(lineNumber));
    }

    static boolean hasAnnotation(List<String> lines, int lineNumber) {
        return lineNumber != 0 && !isAnnotation(lines, lineNumber) && isAnnotation(lines, lineNumber - 1);
    }

    static boolean isAnnotation(String line) {
        return line.matches("^@.*");
    }

    static boolean isMethodDeclaration(List<String> lines, int lineNumber) {
        String line = lines.get(lineNumber);
        return line.matches("^(public\\s+|private\\s+|protected\\s+)?.*(void|[A-Z]+\\S*)\\s+.*\\(.*\\)\\s*(throws\\s+[A-Z].*)?\\{");
    }

    static boolean isBlockClose(String line) {
        return line.matches(".*\\}$");
    }

    static boolean isPackageDeclaration(String line) {
        return line.matches("^package.*;");
    }

    static boolean isBlockStart(String line) {
        return line.matches("^.*\\{.*$");
    }

    static boolean isClassDeclaration(List<String> lines, int lineNumber) {
        String line = lines.get(lineNumber);
        return line.matches("^.*class\\s+\\S+.*\\{");
    }

    static boolean isEnumDeclaration(List<String> lines, int lineNumber) {
        String line = lines.get(lineNumber);
        return line.matches("^.*enum\\s+\\S+.*\\{");
    }

    static boolean isInterfaceDeclaration(List<String> lines, int lineNumber) {
        String line = lines.get(lineNumber);
        return line.matches("^.*interface\\s+\\S+.*\\{");
    }

    static boolean isFirstAnnotationOfClassEnumOrInterface(List<String> lines, int lineNumber) {
        String line = lines.get(lineNumber);
        if(isAnnotation(line) && (lineNumber == 0 || !isAnnotation(lines, lineNumber-1))) {
            int i = lineNumber + 1;
            while(i<lines.size() && isAnnotation(lines.get(i))) {
                i++;
            }
            return isClassDeclaration(lines, i) || isEnumDeclaration(lines, i) || isInterfaceDeclaration(lines, i);
        }
        return false;
    }

    static boolean isFirstAnnotationOfMethod(List<String> lines, int lineNumber) {
        String line = lines.get(lineNumber);
        if(isAnnotation(line) && (lineNumber == 0 || !isAnnotation(lines, lineNumber-1))) {
            int i = lineNumber + 1;
            while(i<lines.size() && isAnnotation(lines.get(i))) {
                i++;
            }
            return isMethodDeclaration(lines, i);
        }
        return false;
    }
}
