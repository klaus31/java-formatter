package x.java;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.ParseTreeListener;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.Arrays;

class FormatParseTreeListener implements ParseTreeListener {

    private final JavaFormatter formatter;
    private final String[] ruleNames;
    private final JavaRulePath javaRulePath;

    FormatParseTreeListener(JavaFormatter formatter, String[] ruleNames) {
        this.formatter = formatter;
        this.ruleNames = ruleNames;
        this.javaRulePath = new JavaRulePath();
    }

    @Override
    public void visitTerminal(TerminalNode node) {
        formatter.add(new NodeWrapper(node, new JavaRulePath(javaRulePath)));
    }

    @Override
    public void visitErrorNode(ErrorNode node) {
        System.err.println("TODO: Error node " + node + " / " + Arrays.toString(ruleNames));
    }

    @Override
    public void enterEveryRule(ParserRuleContext ctx) {
        javaRulePath.enter(getRuleName(ctx));
        formatter.enterRule(javaRulePath);
    }

    private String getRuleName(ParserRuleContext ctx) {
        return ruleNames[ctx.getRuleIndex()];
    }

    @Override
    public void exitEveryRule(ParserRuleContext ctx) {
        javaRulePath.exit(getRuleName(ctx));
    }
}
