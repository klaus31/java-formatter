package javaformatter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Iterator;
import java.util.function.Consumer;

class SourceCodeFiles {
    
    private final Iterator<Path> javaFiles;
    
    private final String suffix;
    
    SourceCodeFiles(Path directory, String suffix) throws IOException {
        if (!directory.toFile().isDirectory()) {
            System.err.println(directory + " is not a directory");
            System.exit(1610121905);
        }
        this.javaFiles = Files.find(directory, 999, this::isFileOfInterest).iterator();
        this.suffix = suffix;
    }
    
    private boolean isFileOfInterest(Path path, BasicFileAttributes basicFileAttributes) {
        return basicFileAttributes.isRegularFile() && path.getFileName().toString().matches("^.+\\." + suffix + "$") && path.toFile().canRead() && path.toFile().canWrite();
    }
    
    void forEach(Consumer<SourceCodeFile> consumer) throws IOException {
        while (javaFiles.hasNext()) {
            consumer.accept(new SourceCodeFile(javaFiles.next()));
        }
    }
}