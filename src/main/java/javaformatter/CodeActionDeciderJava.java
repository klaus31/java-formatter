package javaformatter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import static javaformatter.CodeActionDeciderJavaUtil.*;

class CodeActionDeciderJava implements CodeActionDecider {
    
    public int tabChangeNextLine(String line) {
        return isBlockStart(line) ? 1 : 0;
    }
    
    public int tabChangeThisLine(String line) {
        return isBlockClose(line) ? -1 : 0;
    }
    
    public int blankLinesBefore(final List<String> lines, final int lineNumber) {
        if (isFirstAnnotationOfMethod(lines, lineNumber)) return 1;
        if (isFirstAnnotationOfClassEnumOrInterface(lines, lineNumber)) return 1;
        boolean hasAnnotation = hasAnnotation(lines, lineNumber);
        if (isMethodDeclaration(lines, lineNumber) && !hasAnnotation) return 1;
        if (isClassDeclaration(lines, lineNumber) && !hasAnnotation) return 1;
        if (isEnumDeclaration(lines, lineNumber) && !hasAnnotation) return 1;
        if (isInterfaceDeclaration(lines, lineNumber) && !hasAnnotation) return 1;
        return 0;
    }
    
    public int blankLinesAfter(String line) {
        return isPackageDeclaration(line) ? 1 : 0;
    }
    
    public List<String> preProcessLines(List<String> lines) {
        lines = lines.stream().map(String::trim).collect(Collectors.toList());
        List<String> resultLines = new ArrayList<>(lines.size());
        for (String line : lines) {
            if (CodeActionDeciderJavaUtil.isStartOfIf(line)) {
                line = line
                .replaceAll("if\\s*\\(", "if \\(")
                .replaceAll("\\)\\s*\\{", ") \\{");
            }
            resultLines.add(line);
        }
        return resultLines;
    }
}