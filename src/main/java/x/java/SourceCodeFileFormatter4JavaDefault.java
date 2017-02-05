package x.java;

import org.antlr.v4.runtime.ANTLRFileStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.LexerInterpreter;
import org.antlr.v4.runtime.ParserInterpreter;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeListener;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.antlr.v4.tool.Grammar;
import x.ctrl.CrashStrategy;
import x.ctrl.SourceCodeFile;
import x.ctrl.SourceCodeFileFormatter;
import java.io.IOException;
import java.util.List;
import static java.util.stream.Collectors.toList;
import static x.java.JavaConfig.EOL;
public class SourceCodeFileFormatter4JavaDefault implements SourceCodeFileFormatter {

    private static final String GRAMMAR_FILE = SourceCodeFileFormatter4JavaDefault.class.getClassLoader().getResource("Java8.g4").getFile();
    private static final Grammar GRAMMAR = Grammar.load(GRAMMAR_FILE);
    private final SourceCodeFile file;

    public SourceCodeFileFormatter4JavaDefault(SourceCodeFile file) {
        this.file = file;
    }

    @Override
    public List<String> createOutputLines() {
        LexerInterpreter lexEngine = null;
        try {
            lexEngine = GRAMMAR.createLexerInterpreter(new ANTLRFileStream(file.getPath().toAbsolutePath().toString()));
        } catch (IOException e) {
            CrashStrategy.reportToUserAndExit(e, 1612171648);
        }
        CommonTokenStream tokens = new CommonTokenStream(lexEngine);
        ParserInterpreter parser = GRAMMAR.createParserInterpreter(tokens);
        ParseTree t = parser.parse(GRAMMAR.getRule("compilationUnit").index);
        ParseTreeWalker walker = new ParseTreeWalker();
        JavaFormatter formatter = new JavaFormatter();
        ParseTreeListener listener = new FormatParseTreeListener(formatter, parser.getRuleNames());
        walker.walk(listener, t);

        // TODO the post processing part ("   " > "") is ugly and breaks with the concept
        return formatter.getFormattedSourceCode().getCode(EOL, file).stream().map(s -> s.trim().isEmpty() ? "" : s).collect(toList());
    }
}