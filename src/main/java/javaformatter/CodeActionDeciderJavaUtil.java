package javaformatter;

import org.apache.commons.lang3.StringUtils;
import java.util.List;
import static org.apache.commons.lang3.StringUtils.countMatches;
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
        return line.matches("^\\s*@.*");
    }
    static boolean isImport(List<String> lines, int lineNumber) {
        return matches(lines, lineNumber, "import\\s+(static\\s+)?\\S+\\s*;");
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
    static boolean isMethodDeclaration(List<String> lines, int lineNumber) {
        return matches(lines, lineNumber, "(public\\s+|private\\s+|protected\\s+)?.*(void|[A-Z]+\\S*)\\s+.*\\(.*\\)\\s*(throws\\s+[A-Z].*)?\\{");
        }
        static boolean isBlockClose(String line) {
            return killStringsAndComments(line).contains("}");
        }
        
        private static String killStringsAndComments(String line) {
            String lineToSearchIn = killStrings(line);
            return killComments(lineToSearchIn);
        }
        
        private static String killComments(String lineToSearchIn) {
            String regexSingleLineComment = "//.*";
            lineToSearchIn = lineToSearchIn.replaceAll(regexSingleLineComment, "");
            String regexMultiComment = "/\\*(^\\*/)\\*/";
            String tmp =lineToSearchIn.replaceFirst(regexMultiComment, "");
            while(!tmp.equals(tmp.replaceFirst(regexMultiComment, ""))) {
                tmp =tmp.replaceFirst(regexMultiComment, "");
            }
            lineToSearchIn = tmp.replaceAll("/\\*.*", "");
            return lineToSearchIn;
        }
        
        private static String killStrings(String line) {
            String regexString = "\".*\"";
            return line.replaceAll(regexString, "");
        }
        static boolean isPackageDeclaration(String line) {
            return line.matches("^\\s*package.*;");
        }
        static boolean isBlockStart(String line) {
            return line.matches("^.*\\{.*$");
            }
            static boolean isClassDeclaration(List<String> lines, int lineNumber) {
                return matches(lines.get(lineNumber), ".*class\\s+\\S+.*\\{");
                }
                static boolean isEnumDeclaration(List<String> lines, int lineNumber) {
                    return matches(lines.get(lineNumber), ".*enum\\s+\\S+.*\\{");
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
                        public static boolean isStartOfIf(String line) {
                            return matches(line, "if\\s*\\(.+\\).*");
                        }
                    }