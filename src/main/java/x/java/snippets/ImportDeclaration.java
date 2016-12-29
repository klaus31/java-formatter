package x.java.snippets;

import x.java.JavaConfig;
import x.java.JavaRulePath;
import x.java.NodeWrapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.joining;

public class ImportDeclaration extends SimpleNodesJavaCodeSnippet {

    private static final List<String> WHITESPACE_WORDS = Arrays.asList("import", "static");

    @Override
    public String toSourceString() {
        return super.toSourceString() + JavaConfig.EOL;
    }

    @Override
    protected String toSourceString(NodeWrapper node) {
    if (node.matchesRulePath(rp->rp.isCurrentRuleA("comment"))) {
        Comment comment=new Comment();
        comment.add(node);
        return comment.toSourceString();
    }else{
        final StringBuilder result = new StringBuilder();
        result.append(node.toSourceString());
        if(WHITESPACE_WORDS.contains(node.toSourceString())) {
            result.append(" ");
        }
        return result.toString();
    }
    }

}
