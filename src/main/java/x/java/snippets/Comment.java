package x.java.snippets;

import x.ctrl.SourceCodeFile;
import x.java.IndentService;
import x.java.NodeWrapper;
import java.util.List;
import static x.java.JavaConfig.EOL;
import static x.java.JavaConfig.getIndentService;
public class Comment extends SimpleNodesJavaCodeSnippet {

    @Override
    protected String toSourceString(NodeWrapper node, SourceCodeFile file) {
        String result = node.getText().trim();
        result = result.replaceAll("^//\\s*", "// ");
        return result + EOL + getIndentService(file).calculateIndentToAppendTo(node);
    }

    @Override
    protected String afterSnippet(List<NodeWrapper> nodesInSnippet, List<NodeWrapper> allNodesInCompilationUnit, IndentService indentService) {
        return "";
    }
}