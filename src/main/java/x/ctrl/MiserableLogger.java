package x.ctrl;

import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.time.DurationFormatUtils;
import org.apache.commons.lang3.time.StopWatch;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;
import static org.apache.commons.lang3.time.DurationFormatUtils.formatDurationHMS;
public class MiserableLogger {
    private static final boolean LOG_INFO = true;
    private static final boolean LOG_DEBUG = false;
    private static Map<String, StopWatch> stopWatches = new HashMap<>();
    private static final PrintStream OUT = System.out;
    public static void logDebug(String message) {
        if (LOG_DEBUG) OUT.println(message);
    }
    public static void logInfo(String message) {
        if (LOG_INFO) OUT.println(message);
    }
    public static void logInfoStopWatchStart(String name) {
        if (LOG_INFO) {
            StopWatch stopWatch = new StopWatch();
            stopWatch.start();
            stopWatches.put(name, stopWatch);
        }
    }
    public static void logInfoStopWatchStop(String name) {
        Validate.isTrue(stopWatches.containsKey(name));
        if (LOG_INFO) {
            StopWatch stopWatch = stopWatches.get(name);
            stopWatch.stop();
            logInfo("Duration of '" + name + "': " + formatDurationHMS(stopWatch.getTime()));
        }
    }
}