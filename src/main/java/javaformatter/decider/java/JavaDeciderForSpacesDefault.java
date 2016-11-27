package javaformatter.decider.java;

import static javaformatter.decider.DeciderUtil.findAndReplace;

class JavaDeciderForSpacesDefault {

    JavaDeciderForSpacesDefault() {
    }

    /**
     * @param part of a code line <b>without strings and comments</b>
     */
    String modifySpacesInPartOfLine(String part) {
        part = modifySpacesAfterLesserThan(part);
        part = modifySpacesAfterGreaterThan(part);
        part = modifySpacesAtEqual(part);
        part = modifySpacesAtSemicolon(part);
        part = modifySpacesBeforeLesserThan(part);
        part = modifySpacesAtGreaterThan2(part);
        part = modifySpacesAtLambdaArrow(part);
        part = modifySpacesAtStartingCurlyBrace(part);
        part = modifySpacesBeforeEqualSign(part);
        part = modifySpacesAfterPlusSign(part);
        part = modifySpacesAtMinusSign(part);
        part = modifySpacesAfterStarSign(part);
        part = modifySpacesAfterSlash(part);
        part = modifySpacesAfterPercentageSign(part);
        part = modifySpacesAtPlusSign(part);
        part = modifySpacesBeforeStarSign(part);
        part = modifySpacesBeforeSlash(part);
        part = modifySpacesAfterOneLineComment(part);
        part = modifySpacesBefore(part);
        part = modifySpacesAfterPipeSign(part);
        part = modifySpacesAfterAmpersandSign(part);
        part = modifySpacesBeforePipeSign(part);
        part = modifySpacesBeforeAmpersandSign(part);
        part = modifySpacesAfterIf(part);
        part = modifySpacesAfterFor(part);
        part = modifySpacesAfterWhile(part);
        part = modifySpacesAfterCatch(part);
        part = modifySpacesAfterReturn(part);
        part = modifySpacesAtQuestionmark(part);
        part = modifySpacesAtColon(part);
        part = modifySpacesAfterClosingCurlyBraces(part);
        part = modifySpacesAfterComma(part);
        part = modifySpacesBeforeNotEqualSign(part);

        // TODO repair code smell

        // repair stuff
        part = part.replaceAll("; ;", ";;");
        part = part.replaceAll(" ;", ";");
        part = part.replaceAll("\\} \\)", "})");
        part = part.replaceAll("\\) \\}", ")}");
        part = part.replaceAll(" \\]", "]");
        part = part.replaceAll("\\[ ", "[");
        part = part.replaceAll("\\s+", " ");
        return part;
    }

    private String modifySpacesBeforeNotEqualSign(String part) {
        part = findAndReplace(part, "([^\\s])\\!=", m -> m.group(1) + " !=");
        return part;
    }

    private String modifySpacesAfterComma(String part) {
        part = findAndReplace(part, ",([^\\s])", m -> ", " + m.group(1));
        part = findAndReplace(part, ",$", m -> ", ");
        return part;
    }

    /*
     * "}"             ==> "} "
     *
     * E.g.:
     * }else{      ==> } else{
     *
     * but not:
     * }
     */
    private String modifySpacesAfterClosingCurlyBraces(String part) {
        part = findAndReplace(part, "\\}([^\\s])", m -> "} " + m.group(1));
        return part;
    }

    /*
     * ":"             ==> " : "
     *
     * E.g.:
     * int a=b?c:d;   ==> int a=b?c : d;
     *
     * but not:
     * list.stream().filter(this::bar);
     */
    private String modifySpacesAtColon(String part) {
        part = findAndReplace(part, "([^\\s:])\\:([^:])", m -> m.group(1) + " :" + m.group(2));
        part = findAndReplace(part, "([^:])\\:([^\\s:])", m -> m.group(1) + ": " + m.group(2));
        return part;
    }

    /*
     * "?"             ==> " ? "
     *
     * E.g.:
     * int a=b?c:d;   ==> int a=b ? c:d;
     *
     * but not:
     * Foo<?> foo;
     */
    private String modifySpacesAtQuestionmark(String part) {
        part = findAndReplace(part, "([^\\s\\<])\\?", m -> m.group(1) + " ?");
        part = findAndReplace(part, "\\?([^\\s\\>])", m -> "? " + m.group(1));
        return part;
    }

    private String modifySpacesAfterReturn(String part) {
        part = findAndReplace(part, "return([^\\s])", m -> "return " + m.group(1));
        part = findAndReplace(part, "return$", m -> "return ");
        return part;
    }

    private String modifySpacesAfterCatch(String part) {
        part = findAndReplace(part, "catch\\(", m -> "catch (");
        return part;
    }

    private String modifySpacesAfterWhile(String part) {
        part = findAndReplace(part, "^while\\(", m -> "while (");
        return part;
    }

    private String modifySpacesAfterFor(String part) {
        part = findAndReplace(part, "^for\\(", m -> "for (");
        return part;
    }

    /*
     * "if("     ==> "if ("
     *
     * E.g.:
     * if(a>b)   ==> if (a>b)
     *
     * but not:
     * gif(true)
     */
    private String modifySpacesAfterIf(String part) {
        part = findAndReplace(part, "^if\\(", m -> "if (");
        return part;
    }

    private String modifySpacesBeforeAmpersandSign(String part) {
        part = findAndReplace(part, "([^\\s\\&])\\&", m -> m.group(1) + " &");
        return part;
    }

    /*
     * "|"       ==> " |"
     *
     * E.g.:
     * a||b       ==> a ||b
     * a|b   ==> a |b
     * a |=b
     */
    private String modifySpacesBeforePipeSign(String part) {
        part = findAndReplace(part, "([^\\s\\|])\\|", m -> m.group(1) + " |");
        return part;
    }

    private String modifySpacesAfterAmpersandSign(String part) {
        part = findAndReplace(part, "\\&([^\\s=\\&])", m -> "& " + m.group(1));
        return part;
    }

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
    private String modifySpacesAfterPipeSign(String part) {
        part = findAndReplace(part, "\\|([^\\s=\\|])", m -> "| " + m.group(1));
        return part;
    }

    private String modifySpacesBefore(String part) {
        part = findAndReplace(part, "([^\\s])%", m -> m.group(1) + " %");
        return part;
    }

    private String modifySpacesAfterOneLineComment(String part) {
        part = findAndReplace(part, "//([^\\s])", m -> "// " + m.group(1));
        return part;
    }

    private String modifySpacesBeforeSlash(String part) {
        part = findAndReplace(part, "([^\\s/])/", m -> m.group(1) + " /");
        return part;
    }

    private String modifySpacesBeforeStarSign(String part) {
        part = findAndReplace(part, "([^\\s\\.])\\*", m -> m.group(1) + " *");
        return part;
    }

    private String modifySpacesAtMinusSign(String part) {
        part = findAndReplace(part, "([^\\s\\-\\(\\)])\\-([^\\-])", m -> m.group(1) + " -" + m.group(2));
        part = findAndReplace(part, "\\)\\-", m -> ") - ");
        part = findAndReplace(part, "([^\\-]|^)\\-([^\\s=\\-\\);>\\d])", m -> m.group(1) + "- " + m.group(2));
        part = findAndReplace(part, "([a-zA-Z\\d])\\s?\\-([a-zA-Z\\d])", m -> m.group(1) + " - " + m.group(2));
        part = findAndReplace(part, "return \\- ([\\d])", m -> "return -" + m.group(1));
        return part;
    }

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
    private String modifySpacesAtPlusSign(String part) {
        part = findAndReplace(part, "([^\\s\\+])\\+([^\\+])", m -> m.group(1) + " +" + m.group(2));
        part = findAndReplace(part, "^\\+", m -> " +");
        part = findAndReplace(part, "\\+$", m -> "+ ");
        return part;
    }

    private String modifySpacesAfterPercentageSign(String part) {
        part = findAndReplace(part, "%([^\\s=])", m -> "% " + m.group(1));
        return part;
    }

    private static String modifySpacesAfterSlash(String part) {
        part = findAndReplace(part, "/([^\\s=/])", m -> "/ " + m.group(1));
        return part;
    }

    private static String modifySpacesAfterStarSign(String part) {
        part = findAndReplace(part, "\\*([^\\s=;])", m -> "* " + m.group(1));
        return part;
    }

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
     * foo(++i)
     * int a = +34;
     */
    private static String modifySpacesAfterPlusSign(String part) {
        part = findAndReplace(part, "([^\\+]|^)\\+([^\\s=\\+\\);])", m -> m.group(1) + "+ " + m.group(2));
        return part;
    }

    /*
     * "="       ==> " ="
     */
    private static String modifySpacesBeforeEqualSign(String part) {
        part = findAndReplace(part, "([^><=\\s-+\\*/%\\|\\&\\!])=", m -> m.group(1) + " =");
        return part;
    }

    /*
     * "{"       ==> " {"
     */
    private static String modifySpacesAtStartingCurlyBrace(String part) {
        part = findAndReplace(part, "([^\\s])\\{", m -> m.group(1) + " {");
        return part;
    }

    /*
     * "->"      ==> " ->"
     */
    private static String modifySpacesAtLambdaArrow(String part) {
        part = findAndReplace(part, "([^\\s])->", m -> m.group(1) + " ->");
        return part;
    }

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
    private static String modifySpacesAtGreaterThan2(String part) {
        part = findAndReplace(part, "([^\\s-<>\\?])>", m -> m.group(1) + " >");
        part = findAndReplace(part, "<([A-Za-z0-9\\s,\\?<>]*) >", m -> "<" + m.group(1) + ">");
        return part;
    }

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
    private static String modifySpacesBeforeLesserThan(String part) {
        part = findAndReplace(part, "([^\\s])<", m -> m.group(1) + " <");
        part = findAndReplace(part, " <([A-Za-z0-9\\s,\\?<>]+)>", m -> "<" + m.group(1).replaceAll("\\s", "") + ">");
        part = part.replaceAll(" <>", "<>");
        part = findAndReplace(part, ">>([a-z])", m -> ">> " + m.group(1));
        return part;
    }

    /*
     * ";"                 ==> "; "
     *
     * E.g.:
     * for(i=0;i<7;i++)    ==> for(i=0; i<7; i++)
     */
    private static String modifySpacesAtSemicolon(String part) {
        part = findAndReplace(part, ";([^\\s])", m -> "; " + m.group(1));
        return part;
    }

    /*
     * "="     ==> "= "
     *
     * E.g.:
     * a=b     ==> a= b
     * a== b   ==> a== b
     * a="b"   ==> a= "b"
     */
    private static String modifySpacesAtEqual(String part) {
        part = findAndReplace(part, "=([^=\\s])", m -> "= " + m.group(1));
        if (part.matches(".*=$")) part += " ";
        return part;
    }

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
    private static String modifySpacesAfterGreaterThan(String part) {
        part = findAndReplace(part, ">([^=\\s\\(])", m -> "> " + m.group(1));
        return part;
    }

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
    private static String modifySpacesAfterLesserThan(String part) {
        part = findAndReplace(part, "<([^=\\s>])", m -> "< " + m.group(1));
        part = findAndReplace(part, "< ([A-Za-z0-9\\s,\\?]*)>", m -> "<" + m.group(1) + ">");
        return part;
    }
}