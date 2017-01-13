package x.java.snippets;

import x.ctrl.SourceCodeFile;
import x.java.NodeWrapper;
import java.nio.file.Path;
import static x.java.JavaConfig.EOL;
import static x.java.JavaConfig.getIndentService;
public class Comment extends SimpleNodesJavaCodeSnippet {
    @Override
    protected String toSourceString(NodeWrapper node, SourceCodeFile file) {
        return node.getText() + EOL + getIndentService(file).calculateIndentToAppendTo(node);
    }
}