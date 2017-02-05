package x.ctrl;

import org.apache.commons.lang3.StringUtils;
import java.io.IOException;
public class CrashStrategy {

    private static void exit(int id) {
        System.err.println("");
        System.err.println(StringUtils.repeat("\uD83D\uDCA3", 20));
        System.err.println("\uD83D\uDEAA \uD83C\uDFC3 \uD83D\uDCA8 Exit now");
        System.exit(id);
    }
    public static void reportToUserAndExit(IOException e, int id) {
        reportToUserAndExit(e, id, "");
    }
    public static void reportToUserAndExit(IOException e, int id, String additionalMessage) {
        reportToUser(e, id, additionalMessage);
        exit(id);
    }
    public static void reportToUser(IOException e, int id, String additionalMessage) {
        e.printStackTrace();
        System.err.println("");
        System.err.println(StringUtils.repeat("\uD83D\uDCA3", 20));
        System.err.println("Sorry for that \uD83D\uDCA9");
        System.err.println("Please report to https://github.com/klaus31/x-formatter/issues");
        System.err.println("Error " + id + ": " + e.getMessage());
        if (StringUtils.isNotEmpty(additionalMessage)) {
            System.err.println(additionalMessage);
        }
    }
}