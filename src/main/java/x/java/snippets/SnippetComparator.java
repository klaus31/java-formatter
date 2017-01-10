package x.java.snippets;

import java.util.Comparator;

public class SnippetComparator implements Comparator<JavaCodeSnippet> {
    @Override
    public int compare(JavaCodeSnippet jcs1, JavaCodeSnippet jcs2) {
        // TODO make it possible to:
        // TODO order imports
        // TODO order fields
        // TODO order methods
        // TODO main problem: there is no possibility to place the comments right
        return 0;
    }
}
