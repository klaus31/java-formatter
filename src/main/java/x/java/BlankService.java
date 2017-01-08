package x.java;

import org.antlr.v4.runtime.tree.ParseTree;
import java.util.Optional;
import static java.util.Arrays.asList;
public class BlankService {
    private final EolService eolService;
    BlankService(EolService eolService) {
        this.eolService = eolService;
    }
    /**
     * Return true or false, if a blank is required (independent from where it is called from).
     * Otherwise return an Optional.empty.
     */
    public Optional<Boolean> requiresSingleBlankAfterNodeInAnyCase(NodeWrapper node) {
        if ("}".equals(node.calculateNext().getText())) {
            return Optional.of(false);
        }
        if (node.isNextNodeACommentInSameLine()) {
            return Optional.of(true);
        }
        if (eolService.requiresSingleEolAfterNodeInAnyCase(node).orElse(false)) {
            return Optional.of(false);
        }
        if (node.isSemicolonInBasicForStatement()) {
            return Optional.of(true);
        }
        if (asList(";", "::", "(", ".", "++", "--").contains(node.toSourceString())) {
            return Optional.of(false);
        }
        if (asList("new", ",", "=").contains(node.toSourceString())) {
            return Optional.of(true);
        }
        if (node.isBlockStart() && node.matchesRulePath("arrayInitializer")) {
            return Optional.of(false);
        }
        ParseTree nextNode = node.calculateNext();
        if ("(".equals(nextNode.getText())) {
            return Optional.of(asList("catch", "switch", "if", "for", "while", "+", "-", "*", "/", "%").contains(node.toSourceString()));
        }
        if (asList(";", "::", ")", ",", ".", "++", "--", "[", "]", "}").contains(nextNode.getText())) {
            return Optional.of(false);
        }
        if (node.isDoublePointInEnhancedForStatement()) {
            return Optional.of(true);
        }
        if (node.isNextADoublePointInEnhancedForStatement()) {
            return Optional.of(true);
        }
        if (node.isDoublePointInLabeledStatement()) {
            return Optional.of(true);
        }
        if (node.isNextADoublePointInLabeledStatement()) {
            return Optional.of(false);
        }
        if (node.isDoublePointInSwitchStatement()) {
            return Optional.of(true);
        }
        if (node.isNextADoublePointInSwitchStatement()) {
            return Optional.of(false);
        }
        if (")".equals(node.toSourceString())) {
            return Optional.of(! asList(".").contains(nextNode.getText()));
        }
        if (node.matchesRulePath("unannClassType_lfno_unannClassOrInterfaceType")) {
            if (asList("<", ",", ">").contains(nextNode.getText())) {
                return Optional.of(false);
            } else if (node.toSourceString().equals(",")) {
                return Optional.of(true);
            } else if (node.toSourceString().equals("<")) {
                return Optional.of(false);
            } else {
                return Optional.of(true);
            }
        }
        return Optional.empty();
    }
}