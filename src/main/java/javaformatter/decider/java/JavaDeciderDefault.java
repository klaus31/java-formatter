package javaformatter.decider.java;

import java.util.List;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import static java.util.stream.Collectors.toList;

public class JavaDeciderDefault extends JavaDecider {

    public int tabChangeNextLine(String line) {
        return JavaDeciderUtil.isBlockStart(line) ? 1 : 0;
    }

    public int tabChangeThisLine(String line) {
        return JavaDeciderUtil.isBlockClose(line) ? -1 : 0;
    }

    public int blankLinesBefore(final List<String> lines, final int lineNumber) {
        if (JavaDeciderUtil.isFirstLineOfDoc(lines, lineNumber)) return 1;
        boolean hasDoc = JavaDeciderUtil.hasDoc(lines, lineNumber);
        if (JavaDeciderUtil.isFirstAnnotationOfMethod(lines, lineNumber) && !hasDoc) return 1;
        if (JavaDeciderUtil.isFirstAnnotationOfField(lines, lineNumber) && !hasDoc) return 1;
        if (JavaDeciderUtil.isFirstAnnotationOfClassEnumOrInterface(lines, lineNumber) && !hasDoc) return 1;
        boolean hasAnnotation = JavaDeciderUtil.hasAnnotation(lines, lineNumber);
        if (JavaDeciderUtil.isConstructorDeclaration(lines, lineNumber) && !hasAnnotation && !hasDoc) return 1;
        if (JavaDeciderUtil.isFieldDeclaration(lines, lineNumber) && !hasAnnotation && !hasDoc) return 1;
        if (JavaDeciderUtil.isMethodDeclaration(lines, lineNumber) && !hasAnnotation && !hasDoc) return 1;
        if (JavaDeciderUtil.isClassDeclaration(lines, lineNumber) && !hasAnnotation && !hasDoc) return 1;
        if (JavaDeciderUtil.isEnumDeclaration(lines, lineNumber) && !hasAnnotation && !hasDoc) return 1;
        if (JavaDeciderUtil.isInterfaceDeclaration(lines, lineNumber) && !hasAnnotation && !hasDoc) return 1;
        return 0;
    }

    public int blankLinesAfter(String line) {
        return JavaDeciderUtil.isPackageDeclaration(line) ? 1 : 0;
    }

    @Override
    int compareImports(JavaImport importA, JavaImport importB) {
        if (importA.isStatic()) return importB.isStatic() ? importA.toString().compareTo(importB.toString()) : 1;
        if (importB.isStatic()) return -1;
        return importA.toString().compareTo(importB.toString());
    }

    String putInSingleSpaces(String line) {
        if (JavaDeciderUtil.isAPureDocLine(line)) return line;
        return JavaDeciderUtil.withPartsInLineNotBeingAString(line, part -> {

            /*
             * "<"             ==> "< "
             *
             * E.g.:
             * if(a<b)         ==> if(a< b)
             * for(;a<b;)      ==> for(;a< b;)
             * boolean a=b<c;  ==> boolean a=b< c;
             *
             * do not change:
             * List<String>
             * a <= b
             */
            part = findAndReplace(part, "<([^=\\s>])", m -> "< " + m.group(1));
            part = findAndReplace(part, "< ([A-Za-z0-9\\s,\\?]*)>", m -> "<" + m.group(1) + ">");

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
             * a="b"   ==> a= "b"
             */
            part = findAndReplace(part, "=([^=\\s])", m -> "= " + m.group(1));
            if (part.matches(".*=$")) part += " ";

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
             * List<List<String>>
             */
            part = findAndReplace(part, "([^\\s])<", m -> m.group(1) + " <");
            part = findAndReplace(part, " <([A-Za-z0-9\\s,\\?<>]+)>", m -> "<" + m.group(1).replaceAll("\\s", "") + ">");
            part = part.replaceAll(" <>", "<>");
            part = findAndReplace(part, ">>([a-z])", m -> ">> " + m.group(1));

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
             * List<List<String>>
             */
            part = findAndReplace(part, "([^\\s-<>\\?])>", m -> m.group(1) + " >");
            part = findAndReplace(part, "<([A-Za-z0-9\\s,\\?<>]*) >", m -> "<" + m.group(1) + ">");

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
            part = findAndReplace(part, "([^><=\\s-+\\*/%\\|\\&\\!])=", m -> m.group(1) + " =");

            /*
             * "+"       ==> "+ "
             *
             * E.g.:
             * a+b       ==> a+ b
             * "a"+"b"   ==> "a"+ "b"
             *
             * but not:
             * a+=b
             * a++;
             * for(;;i++)
             * int a = +34;
             */
            part = findAndReplace(part, "\\+([^\\s=\\+\\);])", m -> "+ " + m.group(1));

            // quite same with -
            part = findAndReplace(part, "\\-([^\\s=\\-\\);>\\d])", m -> "- " + m.group(1));

            // quite same with *
            part = findAndReplace(part, "\\*([^\\s=;])", m -> "* " + m.group(1));

            // quite same with /
            part = findAndReplace(part, "/([^\\s=/])", m -> "/ " + m.group(1));

            // quite same with %
            part = findAndReplace(part, "%([^\\s=])", m -> "% " + m.group(1));

            /*
             * "+"       ==> " +"
             *
             * E.g.:
             * a+b       ==> a +b
             * "a"+"b"   ==> "a" +"b"
             * a+=b      ==> a +=b
             *
             * but not:
             * a++
             */
            part = findAndReplace(part, "([^\\s\\+])\\+([^\\+])", m -> m.group(1) + " +" + m.group(2));
            part = findAndReplace(part, "^\\+", m -> " +");
            part = findAndReplace(part, "\\+$", m -> "+ ");

            // quite same with -
            part = findAndReplace(part, "([^\\s\\-\\(\\)])\\-([^\\-])", m -> m.group(1) + " -" + m.group(2));
            part = findAndReplace(part, "\\)\\-", m -> ") - ");

            // quite same with *
            part = findAndReplace(part, "([^\\s\\.])\\*", m -> m.group(1) + " *");

            // quite same with /
            part = findAndReplace(part, "([^\\s/])/", m -> m.group(1) + " /");
            part = findAndReplace(part, "//([^\\s])", m -> "// " + m.group(1));

            // quite same with %
            part = findAndReplace(part, "([^\\s])%", m -> m.group(1) + " %");

            /*
             * "|"       ==> "| "
             *
             * E.g.:
             * a||b       ==> a|| b
             * a|b   ==> a| b
             *
             * but not:
             * a|=b
             */
            part = findAndReplace(part, "\\|([^\\s=\\|])", m -> "| " + m.group(1));

            // quite same with &
            part = findAndReplace(part, "\\&([^\\s=\\&])", m -> "& " + m.group(1));

            /*
             * "|"       ==> " |"
             *
             * E.g.:
             * a||b       ==> a ||b
             * a|b   ==> a |b
             * a |=b
             */
            part = findAndReplace(part, "([^\\s\\|])\\|", m -> m.group(1) + " |");

            // quite same with &
            part = findAndReplace(part, "([^\\s\\&])\\&", m -> m.group(1) + " &");

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

            // quite same with "for"
            part = findAndReplace(part, "^for\\(", m -> "for (");

            // quite same with "while"
            part = findAndReplace(part, "^while\\(", m -> "while (");

            // quite same with "catch"
            part = findAndReplace(part, "catch\\(", m -> "catch (");

            // quite same with "return"
            part = findAndReplace(part, "return([^\\s])", m -> "return " + m.group(1));
            part = findAndReplace(part, "return$", m -> "return ");

            /*
             * "?"             ==> " ? "
             *
             * E.g.:
             * int a=b?c:d;   ==> int a=b ? c:d;
             *
             * but not:
             * Foo<?> foo;
             */
            part = findAndReplace(part, "([^\\s\\<])\\?([^\\s\\>])", m -> m.group(1) + " ? " + m.group(2));

            /*
             * ":"             ==> " : "
             *
             * E.g.:
             * int a=b?c:d;   ==> int a=b?c : d;
             *
             * but not:
             * list.stream().filter(this::bar);
             */
            part = findAndReplace(part, "([^\\s:])\\:([^\\s:])", m -> m.group(1) + " : " + m.group(2));

            /*
             * "}"             ==> "} "
             *
             * E.g.:
             * }else{      ==> } else{
             *
             * but not:
             * }
             */
            part = findAndReplace(part, "\\}([^\\s])", m -> "} " + m.group(1));
            part = findAndReplace(part, ",([^\\s])", m -> ", " + m.group(1));
            part = findAndReplace(part, ",$", m -> ", ");
            part = findAndReplace(part, "([^\\s])\\!=", m -> m.group(1) + " !=");

            // repair stuff
            part = part.replaceAll("; ;", ";;");
            part = part.replaceAll(" ;", ";");
            part = part.replaceAll("\\} \\)", "})");
            part = part.replaceAll("\\) \\}", ")}");
            part = part.replaceAll(" \\]", "]");
            part = part.replaceAll("\\[ ", "[");
            return part;
        }).trim();
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

        return lines.stream()
        .map(line -> line.trim().isEmpty() ? "" : line)
        .map(line -> line.trim().matches("^\\*.*") ? " " + line : line)
        .collect(toList());
    }
}