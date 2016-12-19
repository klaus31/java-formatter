package x.java;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.ParseTreeListener;
import org.antlr.v4.runtime.tree.TerminalNode;

class MyParseTreeListener implements ParseTreeListener {
    private final JavaFormatter formatter;
    private final String[] ruleNames;
    private final RulePath rulePath;

    public MyParseTreeListener(JavaFormatter formatter, String[] ruleNames) {
        this.formatter = formatter;
        this.ruleNames = ruleNames;
        this.rulePath = new RulePath();
    }

    @Override
    public void visitTerminal(TerminalNode node) {
        formatter.add(node, rulePath);
    }

    @Override
    public void visitErrorNode(ErrorNode node) {
        formatter.add(node, rulePath);
    }

    @Override
    public void enterEveryRule(ParserRuleContext ctx) {
        rulePath.enter(getRuleName(ctx));
        formatter.startNewRule(rulePath);
    }

    private String getRuleName(ParserRuleContext ctx) {
        return ruleNames[ctx.getRuleIndex()];
    }

    @Override
    public void exitEveryRule(ParserRuleContext ctx) {
        formatter.endRule(rulePath);
        rulePath.exit(getRuleName(ctx));
    }
}
