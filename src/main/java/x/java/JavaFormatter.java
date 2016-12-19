package x.java;

import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class JavaFormatter {
    private final List<CodeLine> result;
    private CodeLine currentLine;

    public JavaFormatter() {
        result = new ArrayList<>();
    }

    public List<String> getCode() {
        return result.stream().map(CodeLine::getLine).collect(toList());
    }

    public void add(TerminalNode node, RulePath rulePath) {
        if (rulePath.isPartOfPackage()) {
            currentLine.addPart(node);
            if (currentLine.countParts() == 1) {
                currentLine.addWhitspace();
            }
        } else if (rulePath.isPartOfImport()) {
            currentLine.addPart(node);
            if (currentLine.countParts() == 1 || currentLine.countParts() == 3 && rulePath.isPartOfStaticImport()) {
                currentLine.addWhitspace();
            }
        } else {
            // TODO throw unkonwn rule exception
            currentLine.addPart(node);
        }
    }

    public void endRule(RulePath rulePath) {
        String currentRule = rulePath.getCurrentRule();
        if (finishLineOnEndRule(rulePath)) {
            result.add(currentLine);
            // TODO remove
            // System.out.println("End " + currentRule + ": " + currentLine.toString());
        }
    }

    private boolean finishLineOnEndRule(RulePath rulePath) {
        return rulePath.isCurrentRuleAnImport() ||
                rulePath.isCurrentRuleAPackage() ||
                rulePath.isCurrentRuleACompilationUnit();
    }

    private boolean startANewLine(RulePath rulePath) {
        return rulePath.isCurrentRuleAnImport() ||
                rulePath.isCurrentRuleAPackage() ||
                rulePath.isCurrentRuleACompilationUnit();
    }

    public void startNewRule(RulePath rulePath) {
        String currentRule = rulePath.getCurrentRule();
        if(startANewLine(rulePath)) {
        currentLine = new CodeLine(rulePath);
        }
        // TODO remove
        // System.out.println("Start: " + currentRule);
    }
}
