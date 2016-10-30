package javaformatter;

import javaformatter.ctrl.SourceCodeFormatter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {

    public static void main(final String... args) throws IOException, InterruptedException {
        if (args.length != 1) {
            System.err.println("Usage java -jar formatter.jar <directory-with-java-sources>");
            System.exit(1610121904);
        }
        Path inputDirectory = Paths.get(args[0]);
        SourceCodeFormatter.start(inputDirectory);
    }
}