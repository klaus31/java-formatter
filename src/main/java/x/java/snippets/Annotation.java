package x.java.snippets;
import x.java.NodeWrapper;
import static x.java.JavaConfig.EOL;
public class Annotation extends SimpleNodesJavaCodeSnippet {
    private boolean isNextNodeACommentInSameLine;
    @Override
    public String toSourceString() {
        return super.toSourceString() + (isNextNodeACommentInSameLine ? " ":
        EOL + indentCurrent());
    }
    @Override
    protected String toSourceString(NodeWrapper node) {
        final StringBuilder result = new StringBuilder();
        result.append(node.toSourceString());
        if (node.toSourceString().equals(",")) {
            result.append(" ");
        }
        isNextNodeACommentInSameLine = node.isNextNodeACommentInSameLine();
        return result.toString();
    }
}