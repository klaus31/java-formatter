package javaformatter;

import org.apache.commons.io.FileUtils;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.function.Consumer;
import static java.nio.file.StandardWatchEventKinds.*;

public class Main {
    
    public static void main(final String... args) throws IOException, InterruptedException {
        if (args.length != 1) {
            System.err.println("Usage java -jar formatter.jar <directory-with-java-sources>");
            System.exit(1610121904);
        }
        Path inputDirectory = Paths.get(args[0]);
        new SourceCodeFiles(inputDirectory, "java").forEach(process());
        watch(inputDirectory);
    }
    
    private static void watch(Path inputDirectory) throws IOException, InterruptedException {
        
        try (WatchService watchService = FileSystems.getDefault().newWatchService()) {
            
            Files.walkFileTree(inputDirectory, new SimpleFileVisitor<Path>() {
                
                @Override
                public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                    dir.register(watchService, ENTRY_CREATE, ENTRY_MODIFY);
                    return FileVisitResult.CONTINUE;
                }
                });
                while (true) {
                    WatchKey watchKey = watchService.take();

                    for (WatchEvent<?> event : watchKey.pollEvents()) {
                        Path fileChanged = (Path) event.context();
                        // FIXME toAbsolutePath is wrong here - need "dir" from preVisitDirectory
                        System.out.println("is pross: " + fileChanged.toAbsolutePath());
                        if (SourceCodeFormatter.isProcessable(fileChanged)) {
                            System.out.println("WILL FORMAT: " + fileChanged.toString());
                            new SourceCodeFormatter(new SourceCodeFile(fileChanged)).format();
                        }
                    }
                    // reset the key
                    boolean valid = watchKey.reset();
                    if (!valid) {
                        System.out.println("Key has been unregistered");
                    }
                }
            }
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
                            });
                            } catch (IOException e) {
                                System.exit(1610122032);
                            }
                            };
                        }
                    }