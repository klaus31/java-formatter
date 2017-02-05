package x.java.snippets;

import x.ctrl.SourceCodeFile;
import x.java.IndentService;
import x.java.NodeWrapper;
import java.util.List;
import static x.java.JavaConfig.EOL;
public class Annotation extends SimpleNodesJavaCodeSnippet {

    private boolean isNextNodeACommentInSameLine;

    @Override
    protected String afterSnippet(List<NodeWrapper> nodesInSnippet, List<NodeWrapper> allNodesInCompilationUnit, IndentService indentService) {
        return "";
    }

    @Override
    public String toSourceString(SourceCodeFile file) {
        return super.toSourceString(file) + (isNextNodeACommentInSameLine ? " " : EOL + indentCurrent(file));
    }

    @Override
    protected String toSourceString(NodeWrapper node, SourceCodeFile file) {
        final StringBuilder result = new StringBuilder();
        result.append(node.getText());
        if (nextNodeIsANewAnnotation(node)) {
            result.append(EOL);
        }
        if (node.getText().equals(",")) {
            result.append(" ");
        }
        isNextNodeACommentInSameLine = node.isNextNodeACommentInSameLine();
        return result.toString();
    }

    private boolean nextNodeIsANewAnnotation(NodeWrapper node) {
        NodeWrapper nextNode = node.calculateNextNode(getAllNodesInCompilationUnit());
        return "@".equals(nextNode.getText());
    }
}