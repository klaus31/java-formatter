package javaformatter;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.function.Consumer;

public class Main {

    public static void main(final String... args) throws IOException {
        if (args.length != 1) {
            System.err.println("Usage java -jar formatter.jar <directory-with-java-sources>");
            System.exit(1610121904);
        }
        Path inputDirectory = Paths.get(args[0]);
        new SourceCodeFiles(inputDirectory, "java").forEach(process());
    }

    private static Consumer<SourceCodeFile> process() {
        return (source) -> {
            System.out.println("\n\n---------------------------------------------------");
            System.out.println(source.getPath().getFileName());
            System.out.println("---------------------------------------------------\n\n");

            SourceCodeFormatter formatter = new SourceCodeFormatter(source);
            try {
                formatter.format();
            } catch (IOException e) {
                System.exit(1610122032);
            }
            formatter.withSource(System.out::println);
            System.out.println("\n\n---------------------------------------------------\n\n");
        };
    }


}
