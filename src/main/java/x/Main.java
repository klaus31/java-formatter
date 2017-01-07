package x;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import x.ctrl.SourceCodeFormatter;
public class Main {
    public static void main(final String ... args) throws IOException, InterruptedException {
        if (args.length != 1) {
            System.err.println("Usage java -jar formatter.jar <directory-with-sources>");
            System.exit(1610121904);
        }
        Path inputDirectory = Paths.get(args[ 0]);
        new SourceCodeFormatter(inputDirectory).start();
    }
}