package x.java.snippets;
import x.ctrl.SourceCodeFile;
import x.java.NodeWrapper;
import java.nio.file.Path;
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
}