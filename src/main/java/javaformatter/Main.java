package javaformatter;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import javaformatter.ctrl.SourceCodeFormatter;
import org.antlr.v4.runtime.ANTLRFileStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.LexerInterpreter;
import org.antlr.v4.runtime.ParserInterpreter;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.tool.Grammar;

public class Main {

    public static void main(final String... args) throws IOException, InterruptedException {
        if (args.length != 1) {
            System.err.println("Usage java -jar formatter.jar <directory-with-sources>");
            System.exit(1610121904);
        }
        Path inputDirectory = Paths.get(args[0]);
        new SourceCodeFormatter(inputDirectory).start();
    }
}