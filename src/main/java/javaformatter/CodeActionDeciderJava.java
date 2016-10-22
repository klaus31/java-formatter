package javaformatter;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static javaformatter.CodeActionDeciderJavaUtil.*;
import static org.apache.commons.lang3.StringUtils.join;

class CodeActionDeciderJava implements CodeActionDecider {

    public int tabChangeNextLine(String line) {
        return isBlockStart(line) ? 1 : 0;
    }

    public int tabChangeThisLine(String line) {
        return isBlockClose(line) ? -1 : 0;
    }

    public int blankLinesBefore(final List<String> lines, final int lineNumber) {
        if (isFirstLineOfDoc(lines, lineNumber)) return 1;
        boolean hasDoc = hasDoc(lines, lineNumber);
        if (isFirstAnnotationOfMethod(lines, lineNumber) && !hasDoc) return 1;
        if (isFirstAnnotationOfField(lines, lineNumber) && !hasDoc) return 1;
        if (isFirstAnnotationOfClassEnumOrInterface(lines, lineNumber) && !hasDoc) return 1;
        boolean hasAnnotation = hasAnnotation(lines, lineNumber);
        if (isConstructorDeclaration(lines, lineNumber) && !hasAnnotation && !hasDoc) return 1;
        if (isFieldDeclaration(lines, lineNumber) && !hasAnnotation && !hasDoc) return 1;
        if (isMethodDeclaration(lines, lineNumber) && !hasAnnotation && !hasDoc) return 1;
        if (isClassDeclaration(lines, lineNumber) && !hasAnnotation && !hasDoc) return 1;
        if (isEnumDeclaration(lines, lineNumber) && !hasAnnotation && !hasDoc) return 1;
        if (isInterfaceDeclaration(lines, lineNumber) && !hasAnnotation && !hasDoc) return 1;
        return 0;
    }

    public int blankLinesAfter(String line) {
        return isPackageDeclaration(line) ? 1 : 0;
    }

    public List<String> preProcessLines(List<String> lines) {
        return lines.stream()
                .map(String::trim)
                .map(this::killDoubleSpaces)
                .collect(toList());
    }

    private String killDoubleSpaces(String line) {
        // build array with even indexes == non-strings and odd indexes == strings
        List<String> lineSplitAtStrings = new ArrayList<>();
        String[] dirtySplitsAtQuotes = line.split("\"", -1);
        int i=0;
        while (i<dirtySplitsAtQuotes.length) {
            String lineSplit = dirtySplitsAtQuotes[i];
            // put slashed quotes into its string again
            while(lineSplit.matches(".*\\\\$")) {
                // FIXME potential IndexOutOfBoundsException (in wtf syntax)
                i++;
                lineSplit += "\"" + dirtySplitsAtQuotes[i];
            }
            // put slashed quotes into its char again
            boolean falseAlarm = false;
            while(lineSplit.matches(".*'$") && !falseAlarm) {
                    // FIXME potential IndexOutOfBoundsException (in wtf syntax)
                if(dirtySplitsAtQuotes[i+1].matches("^'.*")) {
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
        for (int j=0; j<lineSplitAtStrings.size();j++) {
            if (j % 2 == 0) {
                String result = lineSplitAtStrings.get(j);
                String tmp;
                do {
                    tmp = result;
                    result = result.replaceAll("\\s\\s", " ");
                } while (!tmp.equals(result));
                splittedResults.add(result);
            } else {
                splittedResults.add(lineSplitAtStrings.get(j));
            }
        }
        return join(splittedResults, "\"");
    }
}