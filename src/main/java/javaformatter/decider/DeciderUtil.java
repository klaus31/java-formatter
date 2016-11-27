package javaformatter.decider;

import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DeciderUtil {

    private DeciderUtil() {
    }

    public static String findAndReplace(String haystack, String regex, Function<Matcher, String> exec) {
        Matcher m = Pattern.compile(regex).matcher(haystack);
        while (m.find()) {
            haystack = haystack.replaceFirst(regex, exec.apply(m));
        }
        return haystack;
    }
}
