package javaformatter;

import org.apache.commons.io.FileUtils;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.function.Consumer;

public class Main {
    
    public static void main(final String... args) throws IOException, InterruptedException {
        if (args.length != 1) {
            System.err.println("Usage java -jar formatter.jar <directory-with-java-sources>");
            System.exit(1610121904);
        }
        Path inputDirectory = Paths.get(args[0]);
        new SourceCodeFiles(inputDirectory, "java").forEach(process());
    }
    
    private static Consumer<SourceCodeFile> process() {
        return (sourceCodeFile) -> {
            SourceCodeFormatter formatter = new SourceCodeFormatter(sourceCodeFile);
            try {
                formatter.format();
                formatter.withSource(formattedSource -> {
                    try {
                        FileUtils.writeStringToFile(sourceCodeFile.getPath().toFile(), formattedSource);
                    } catch (IOException e) {
                        System.exit(1610142042);
                    }
                } );
            } catch (IOException e) {
                System.exit(1610122032);
            }
        } ;
    }
}