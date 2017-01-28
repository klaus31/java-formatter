package x.java.snippets;
import x.ctrl.SourceCodeFile;
import x.java.NodeWrapper;
import java.nio.file.Path;
import static x.java.JavaConfig.EOL;
public class PackageDeclaration extends SimpleNodesJavaCodeSnippet {
    @Override
    public String toSourceString(SourceCodeFile file) {
        return super.toSourceString(file) + EOL;
    }
    @Override
    protected String toSourceString(NodeWrapper node, SourceCodeFile file) {
        final StringBuilder result = new StringBuilder();
        result.append(node.getText());
        if (node.getText().equals("package")) {
            result.append(" ");
        }
        return result.toString();
    }
}