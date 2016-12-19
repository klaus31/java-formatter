package x.java;

import org.antlr.v4.runtime.ParserInterpreter;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.ParseTreeListener;
import org.antlr.v4.runtime.tree.TerminalNode;

class MyParseTreeListener implements ParseTreeListener {
    private final JavaFormatter formatter;
    private final String[] ruleNames;

    public MyParseTreeListener(JavaFormatter formatter, String[] ruleNames) {
        this.formatter = formatter;
        this.ruleNames = ruleNames;
    }

    @Override
    public void visitTerminal(TerminalNode node) {
        System.out.println("regular: " + node.toString());
    }

    @Override
    public void visitErrorNode(ErrorNode node) {
        System.out.println("ERROR:   " + node.toString());
    }

    @Override
    public void enterEveryRule(ParserRuleContext ctx) {
        System.out.println("enter:   " + getRuleName(ctx));
    }

    private String getRuleName(ParserRuleContext ctx) {
        return ruleNames[ctx.getRuleIndex()];
    }

    @Override
    public void exitEveryRule(ParserRuleContext ctx) {
        System.out.println("exit:    " + getRuleName(ctx));
    }
}
