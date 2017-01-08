package x.java;

import org.antlr.v4.runtime.tree.ParseTree;
import java.util.Optional;
import static java.util.Arrays.asList;
public class EolService {
    public Optional<Boolean> requiresSingleEolAfterNodeInAnyCase(NodeWrapper node) {
        if (node.isNextNodeACommentInSameLine()) {
            return Optional.of(false);
        }
        if (node.isBlockEnd() && node.isNextNodeElseCatchOrWhile() || node.isNextNodeText(";") || node.isNextNodeText(")")) {
            return Optional.of(false);
        }
        if (node.isBlockStart() && node.matchesRulePath("arrayInitializer")) {
            return Optional.of(false);
        }
        if (node.isBlockStartOrEnd() || node.isSemicolonAtEnd() || node.isDoublePointInSwitchStatement()) {
            return Optional.of(true);
        }
        return Optional.empty();
    }
}