package javaformatter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;

class JavaSources {

    private final Iterator<Path> javaFiles;

    JavaSources(Path directory) throws IOException {
        if (!directory.toFile().isDirectory()) {
            System.err.println(directory + " is not a directory");
            System.exit(1610121905);
        }
        this.javaFiles = Files.find(directory, 999, this::isFileOfInterest).iterator();
    }

    private boolean isFileOfInterest(Path path, BasicFileAttributes basicFileAttributes) {
        return basicFileAttributes.isRegularFile() && path.getFileName().toString().matches("^.+\\.java$") && path.toFile().canRead() && path.toFile().canWrite();
    }

    void forEach(Consumer<JavaSource> consumer) throws IOException {
        while(javaFiles.hasNext()) {
            consumer.accept(new JavaSource(javaFiles.next()));
        }
    }

}
