package x.ctrl;

import java.io.PrintStream;
public class MiserableLogger {
    private static final boolean LOG_DEBUG = false;
    private static final PrintStream OUT = System . out;
    public static void logDebug(String message) {
        if (LOG_DEBUG) OUT.println(message);
    }
}