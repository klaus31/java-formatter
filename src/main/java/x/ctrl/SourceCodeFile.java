package x.ctrl;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
public class SourceCodeFile {
    private final Path path;
    SourceCodeFile ( Path path ) {
        this . path = path ;
    }
    public List<String> readContentLines() throws IOException {
        return Files.readAllLines(path, StandardCharsets.UTF_8);
    }
    public Path getPath() {
        return path;
    }
}