package javaformatter;

import java.util.ArrayList;
import java.util.List;
import java.util.function.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        .map(this::putInSingleSpaces)
        .collect(toList());
    }
    
    private String putInSingleSpaces(String line) {
        if(CodeActionDeciderJavaUtil.isAPureDocLine(line)) return line;
        return withPartsInLineNotBeingAString(line, part -> {

            /*
            * "<"          ==> "< "
            *
            * E.g.:
            * if(a<b)      ==> if(a< b)
            * for(;a<b;)   ==> for(;a< b;)
            *
            * do not change:
            * List<String>
            */
            part = findAndReplace(part, "(;|\\()([^\\)]+)<([^=\\s>])", m -> m.group(1) + m.group(2) + "< " + m.group(3));

            /*
            * ">"                ==> "> "
            *
            * E.g.:
            * if(a>b)            ==> if(a> b)
            * for(;a>b;)         ==> for(;a> b;)
            * foo(()->this::bar) ==> foo(()-> this::bar)
            * List<String>a      ==> List<String> a
            *
            * do not change:
            * new ArrayList<>()
            */
            part = findAndReplace(part, ">([^=\\s\\(])", m -> "> " + m.group(1));

            /*
            * "="     ==> "= "
            *
            * E.g.:
            * a=b     ==> a= b
            * a== b   ==> a== b
            */
            part = findAndReplace(part, "=([^=\\s])", m -> "= " + m.group(1));

            /*
            * ";"                 ==> "; "
            *
            * E.g.:
            * for(i=0;i<7;i++)    ==> for(i=0; i<7; i++)
            */
            part = findAndReplace(part, ";([^\\s])", m -> "; " + m.group(1));

            /*
            * "<"             ==> " <"
            *
            * E.g.:
            * if(a<b)         ==> if(a <b)
            * for(;a<b;)      ==> for(;a <b;)
            *
            * do not change:
            * new ArrayList<>()
            * List<String>
            */
            part = findAndReplace(part, "(;|\\()([^\\)]*)([^\\s])<", m -> m.group(1) + m.group(2) + m.group(3) + " <");

            /*
            * ">"             ==> " >"
            *
            * E.g.:
            * if(a>b)         ==> if(a >b)
            * for(;a>b;)      ==> for(;a >b;)
            *
            * do not change:
            * new ArrayList<>()
            * () -> this.foo()
            * List<String>
            */
            part = findAndReplace(part, "([^\\s-<])>", m -> m.group(1) + " >");
            part = findAndReplace(part, "<([A-Za-z0-9\\s,]*) >", m -> "<" + m.group(1) + ">");

            /*
            * "->"      ==> " ->"
            */
            part = findAndReplace(part, "([^\\s])->", m -> m.group(1) + " ->");

            /*
            * "{"       ==> " {"
            */
            part = findAndReplace(part, "([^\\s])\\{", m -> m.group(1) + " {");

            /*
            * "="       ==> " ="
            */
            part = findAndReplace(part, "([^><=\\s])=", m -> m.group(1) + " =");

            /*
            * "if("     ==> "if ("
            *
            * E.g.:
            * if(a>b)   ==> if (a>b)
            *
            * but not:
            * gif(true)
            */
            part = findAndReplace(part, "^if\\(", m -> "if (");

            /*
            * "for("             ==> "for ("
            */
            part = findAndReplace(part, "^for\\(", m -> "for (");

            /*
            * "while("             ==> "while ("
            */
            part = findAndReplace(part, "^while\\(", m -> "while (");

            return part;
        });
    }

    private String findAndReplace(String haystack, String regex, Function<Matcher, String> exec) {
        Matcher m = Pattern.compile(regex).matcher(haystack);
        while(m.find()) {
            haystack = haystack.replaceAll(regex, exec.apply(m));
        }
        return haystack;
    }

    private String killDoubleSpaces(String line) {
        if(CodeActionDeciderJavaUtil.isAPureDocLine(line)) return line;
        return withPartsInLineNotBeingAString(line, part -> {
            String tmp;
            do {
                tmp = part;
                part = part.replaceAll("\\s\\s", " ");
            } while (!tmp.equals(part));
            return part;
        });
    }
    private String withPartsInLineNotBeingAString(String line, Function<String, String> format) {
        
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
                splittedResults.add(format.apply(result));
            } else {
                splittedResults.add(lineSplitAtStrings.get(j));
            }
        }
        return join(splittedResults, "\"");
    }
}