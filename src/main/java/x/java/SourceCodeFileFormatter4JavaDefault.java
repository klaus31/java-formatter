package x.java;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.ATN;
import org.antlr.v4.runtime.atn.ATNDeserializer;
import org.antlr.v4.runtime.atn.ATNSerializer;
import org.antlr.v4.runtime.tree.TerminalNodeImpl;
import org.antlr.v4.runtime.tree.Trees;
import x.ctrl.SourceCodeFile;
import x.ctrl.SourceCodeFileFormatter;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.tool.Grammar;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.antlr.v4.parse.ANTLRLexer.EOF;

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
            e.printStackTrace();
            System.err.println("1612171741");
            System.exit(1612171741);
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
            e.printStackTrace();
            System.err.println("1612171648");
            System.exit(1612171648);
        }
        CommonTokenStream tokens = new CommonTokenStream(lexEngine);
        ParserInterpreter parser = createParserInterpreter(g, tokens);
        ParseTree t = parser.parse(g.getRule("compilationUnit").index);
//        System.out.println("parse tree: " + t.toStringTree(parser));
        System.out.println(getText(t, parser));


        CommonTokenStream cts = new CommonTokenStream(lexEngine, 2);
        List<Token> tokenz = new ArrayList<Token>();
        while (cts.LA(1) != EOF) {
            tokenz.add(cts.LT(1));
            cts.consume();
        }
        System.out.println(tokenz);

        return t;
    }

    private String getText(ParseTree context, ParserInterpreter parser) {
            StringBuilder builder = new StringBuilder();
        if(context.getChildCount() == 0) {
            builder.append(Trees.toStringTree(context, parser) + ": " + context.getText() + "\n");
//            builder.append(((TerminalNodeImpl)context).getPayload() + ": " + context.getText() + "\n");
//            builder.append(((TerminalNodeImpl)context).getSourceInterval() + ": " + context.getText() + "\n");
//            builder.append(((InterpreterRuleContext)context.getParent()).getRuleIndex() + ": " + context.getText() + "\n");
//            builder.append(((InterpreterRuleContext)context.getParent()).getRuleContext().getText() + ": " + context.getText() + "\n");
//            builder.append(((InterpreterRuleContext)context.getParent()).getParent().getClass() + ": " + context.getText() + "\n");
            builder.append("--------------------\n");
        } else {
            for(int i = 0; i < context.getChildCount(); ++i) {
                System.out.println("> " + Trees.toStringTree(context, parser) + context.getText() + "\n");
                builder.append(getText(context.getChild(i), parser));
            }
        }
            return builder.toString();
    }

    public ParserInterpreter createParserInterpreter(Grammar g, TokenStream tokenStream) {
        if(g.isLexer()) {
            throw new IllegalStateException("A parser interpreter can only be created for a parser or combined grammar.");
        } else {
            char[] serializedAtn = ATNSerializer.getSerializedAsChars(g.atn);
            ATN deserialized = (new ATNDeserializer()).deserialize(serializedAtn);
            return new FreakyCoolParserInterpreter(g.fileName, g.getVocabulary(), Arrays.asList(g.getRuleNames()), deserialized, tokenStream);
        }
    }

}

class FreakyCoolParserInterpreter extends ParserInterpreter {

    public FreakyCoolParserInterpreter(String grammarFileName, Vocabulary vocabulary, Collection<String> ruleNames, ATN atn, TokenStream input) {
        super(grammarFileName, vocabulary, ruleNames, atn, input);
    }

    @Override
    public ParserRuleContext parse(int startRuleIndex) {
        return super.parse(startRuleIndex);
    }
}
