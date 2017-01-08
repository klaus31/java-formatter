package x.ctrl;

import java.io.PrintStream;
public class MiserableLogger {
    private static final boolean LOG_INFO = true;
    private static final boolean LOG_DEBUG = true;
    private static final PrintStream OUT = System.out;
    public static void logDebug(String message) {
        if (LOG_DEBUG) OUT.println(message);
    }
    public static void logInfo(String message) {
        if (LOG_INFO) OUT.println(message);
    }
}