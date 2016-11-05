package javaformatter;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class TestFileReadIn {

    public static Path calcPath(String lang, String fileName) {
        try {
            String resourceStr = String.format("testfiles/%s/%s.txt", lang, fileName);
            URL resource = TestFileReadIn.class.getResource(resourceStr);
            return Paths.get(resource.toURI());
            } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<String> read(String lang, String fileName) {
        try {
            Path path = calcPath(lang, fileName);
            return Files.readAllLines(path, StandardCharsets.UTF_8);
            } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}