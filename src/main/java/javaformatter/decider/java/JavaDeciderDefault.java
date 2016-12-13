package javaformatter.decider.java;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import static java.util.stream.Collectors.toList;
import static javaformatter.decider.java.JavaDeciderUtil.*;

public class JavaDeciderDefault extends JavaDecider {

    private JavaDeciderForSpacesDefault javaDeciderForSpacesDefault = new JavaDeciderForSpacesDefault();

    public int tabChangeNextLine(String line) {
        return isBlockStart(line) && !isBlockClose(line) || isBlockCloseBeforeStartingNextBlock(line) ? 1 : 0;
    }

    public int tabChangeThisLine(String line) {
        return isBlockClose(line) && !isBlockStart(line) || isBlockCloseBeforeStartingNextBlock(line) ? -1 : 0;
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

    @Override
    public int blankLinesAfter(List<String> lines, int lineNumber) {
        if (lineNumber < lines.size() - 1 && lines.get(lineNumber + 1).trim().isEmpty()) return 0;
        return isPackageDeclaration(lines.get(lineNumber)) && lineNumber < lines.size() - 1 && !isFirstLineOfDoc(lines, lineNumber + 1) && !isClassDeclaration(lines, lineNumber + 1) && !isInterfaceDeclaration(lines, lineNumber + 1) && !isEnumDeclaration(lines, lineNumber + 1) ? 1 : 0;
    }

    @Override
    protected List<String> meltThingsTogetherInOneLine(final List<String> lines) {
        final List<String> result = new ArrayList<>();
        StringBuilder oneLinerBuilder = new StringBuilder();
        for (String line : lines) {
            oneLinerBuilder.append(line.trim());

            // append " " if necessary
            if (line.trim().matches(".*([a-zA-Z0-9\\)])$")) oneLinerBuilder.append(" ");

            // finish melted line when line is ready
            if (isEndOfStatement(line) || containsDoc(line) || line.trim().isEmpty()) {
                String meltedLine = oneLinerBuilder.toString().trim();
                if (!isAPureDocLine(meltedLine)) {
                    meltedLine = withPartsInLineNotBeingAString(meltedLine, part -> part = part.replaceAll("\\s+", " "));
                    meltedLine = withPartsInLineNotBeingAString(meltedLine, part -> findAndReplace(part, "([^a-zA-Z0-9])\\s([^a-zA-Z0-9])", m -> m.group(1) + m.group(2)));
                }
                result.add(meltedLine);
                oneLinerBuilder = new StringBuilder();
            }
        }

        // put last line for killing nothing (invalid end)
        if (oneLinerBuilder.length() > 0) result.add(oneLinerBuilder.toString().trim());
        return result;
    }

    @Override
    int compareImports(JavaImport importA, JavaImport importB) {
        if (importA.isStatic()) return importB.isStatic() ? importA.toString().compareTo(importB.toString()) : 1;
        if (importB.isStatic()) return -1;
        return importA.toString().compareTo(importB.toString());
    }

    String putInSingleSpaces(String line) {
        if (JavaDeciderUtil.isAPureDocLine(line)) return line;
        return withPartsInLineNotBeingAString(line, javaDeciderForSpacesDefault::modifySpacesInPartOfLine).trim();
    }

    private String findAndReplace(String haystack, String regex, Function<Matcher, String> exec) {
        Matcher m = Pattern.compile(regex).matcher(haystack);
        while (m.find()) {
            haystack = haystack.replaceFirst(regex, exec.apply(m));
        }
        return haystack;
    }

    @Override
    public List<String> postProcessFormattedLines(List<String> lines) {
        return lines.stream().map(line -> line.trim().isEmpty() ? "" : line).map(line -> line.trim().matches("^\\*.*") ? " " + line : line).collect(toList());
    }

    @Override
    public List<String> forceLineBreaks(List<String> lines) {
        return lines; // TODO implement me
    }
}