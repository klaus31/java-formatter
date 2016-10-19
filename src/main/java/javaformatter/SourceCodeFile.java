package javaformatter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

class SourceCodeFile {
    
    private final Path path;
    
    private List<String> formattedLines;
    
    SourceCodeFile(Path path) {
        this.path = path;
    }
    
    List<String> readContentLines() throws IOException {
        return Files.readAllLines(path, StandardCharsets.UTF_8);
    }
    
    Path getPath() {
        return path;
    }
    
    void setFormattedLines(List<String> formattedLines) {
        this.formattedLines = formattedLines;
    }
    
    List<String> getFormattedLines() {
        return formattedLines;
    }
    
    String getSuffix() {
        return path.toString().replaceAll(".*\\.", "");
    }
}