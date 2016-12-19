package x.java;

import x.ctrl.CrashStrategy;
import x.ctrl.SourceCodeFile;
import x.ctrl.SourceCodeFileFormatter;
import org.antlr.v4.runtime.ANTLRFileStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.LexerInterpreter;
import org.antlr.v4.runtime.ParserInterpreter;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.tool.Grammar;

import java.io.IOException;
import java.util.List;

public class SourceCodeFileFormatter4JavaDefault implements SourceCodeFileFormatter {
    private final SourceCodeFile file;

    public SourceCodeFileFormatter4JavaDefault(SourceCodeFile file) {
        this.file = file;
    }

    @Override
    public List<String> createOutputLines() {
        try {
            parse();
            return file.readContentLines();
        } catch (IOException e) {
            CrashStrategy.reportToUserAndExit(e, 1612171741);
            return null;
        }
    }

    private ParseTree parse() {
        String combinedGrammarFileName = getClass().getClassLoader().getResource("Java8.g4").getFile();
        final Grammar g = Grammar.load(combinedGrammarFileName);
        LexerInterpreter lexEngine = null;
        try {
            lexEngine = g.createLexerInterpreter(new ANTLRFileStream(file.getPath().toAbsolutePath().toString()));
        } catch (IOException e) {
            CrashStrategy.reportToUserAndExit(e, 1612171648);
        }
        CommonTokenStream tokens = new CommonTokenStream(lexEngine);
        ParserInterpreter parser = g.createParserInterpreter(tokens);
        ParseTree t = parser.parse(g.getRule("compilationUnit").index);
        System.out.println("parse tree: " + t.toStringTree(parser));
        return t;
    }

}
