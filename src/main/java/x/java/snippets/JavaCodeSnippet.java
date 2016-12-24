package x.java.snippets;

import x.format.CodeSnippet;
import x.java.JavaRulePath;
import x.java.NodeWrapper;

public interface JavaCodeSnippet extends CodeSnippet {
    void add(NodeWrapper node);
    void enterRule(JavaRulePath rulePath);
}
