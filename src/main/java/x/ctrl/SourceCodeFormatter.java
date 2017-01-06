package x.ctrl;
import org.apache.commons.io.FileUtils;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.function.Consumer;
import static java.util.stream.Collectors.joining;
import static x.ctrl.KnownSourceFileType.JAVA;
public class SourceCodeFormatter {
    private final Path inputDirectory;
    public SourceCodeFormatter ( Path inputDirectory ) {
        this . inputDirectory = inputDirectory ;
    }
    public static CharSequence getEol() {
        return System.getProperty("line.separator");
    }
    public void start() throws IOException {
        new SourceCodeFiles(inputDirectory, JAVA).forEach(process(JAVA));
    }
    private static Consumer<SourceCodeFile> process(KnownSourceFileType type) {
        return sourceCodeFile -> {
            System.out.println("next file to process: " + sourceCodeFile.getPath());
            List<String> outputLines = createOutputLines(sourceCodeFile, type);
            write(sourceCodeFile, outputLines);
        };
    }
    private static void write(SourceCodeFile sourceCodeFile, List<String> outputLines) {
        try {
            FileUtils.writeStringToFile(sourceCodeFile.getPath().toFile(), outputLines.stream().collect(joining(getEol())));
        } catch (IOException e) {
            CrashStrategy.reportToUserAndExit(e, 1610142042, "Error while writing");
        }
    }
    private static List<String> createOutputLines(SourceCodeFile sourceCodeFile, KnownSourceFileType type) {
        SourceCodeFileFormatter formatter = SourceCodeFileFormatterFactory.get(type, sourceCodeFile);
        return formatter.createOutputLines();
    }
}