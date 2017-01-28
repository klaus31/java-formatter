package x.java.snippets;
import x.format.CodeSnippet;
import x.java.NodeWrapper;
import java.util.List;
public interface JavaCodeSnippet extends CodeSnippet {
    void add(NodeWrapper node, List<NodeWrapper> allNodesInCompilationUnit);
}