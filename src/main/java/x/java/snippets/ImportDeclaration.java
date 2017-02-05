package x.java.snippets;

import x.ctrl.SourceCodeFile;
import x.java.IndentService;
import x.java.NodeWrapper;
import java.util.List;
import static x.java.JavaConfig.EOL;

public class ImportDeclaration extends SimpleNodesJavaCodeSnippet {

    @Override
    protected String toSourceString(NodeWrapper node, SourceCodeFile file) {
        final StringBuilder result = new StringBuilder();
        result.append(node.getText());
        if (node.isNodeTextAnyOf("import", "static")) {
            result.append(" ");
        }
        if (node.isSemicolonAtEnd()) {
            result.append(node.isNextNodeACommentInSameLine() ? " " : EOL);
        }
        return result.toString();
    }

    @Override
    protected String afterSnippet(List<NodeWrapper> nodesInSnippet, List<NodeWrapper> allNodesInCompilationUnit, IndentService indentService) {
        NodeWrapper lastNodeInSnippet = nodesInSnippet.get(nodesInSnippet.size() - 1);
        if (lastNodeInSnippet.isNextNodeACommentInSameLine()) {
            return "";
        }
        if (lastNodeInSnippet.isNextNodeAComment()) {
            return EOL + indentService.getCurrentIndent();
        }
        NodeWrapper nextNode = allNodesInCompilationUnit.get(allNodesInCompilationUnit.indexOf(lastNodeInSnippet) + 1);
        return nextNode.getJavaRulePath().isPartOf("importDeclaration") ? "" : EOL + indentService.getCurrentIndent();
    }
}