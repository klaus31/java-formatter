package x.java.snippets;
import x.java.NodeWrapper;
import static x.java.JavaConfig.EOL;
import static x.java.JavaConfig.getIndentService;
public class Comment extends SimpleNodesJavaCodeSnippet {
    @Override
    protected String toSourceString(NodeWrapper node) {
        return node.toSourceString() + EOL + getIndentService().calculateIndentToAppendTo(node);
    }
}