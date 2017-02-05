package x.java;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNodeImpl;
import org.antlr.v4.runtime.tree.Trees;
import org.antlr.v4.tool.Grammar;
import org.antlr.v4.tool.LexerGrammar;
import org.apache.commons.io.IOUtils;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.runners.MockitoJUnitRunner;
import java.io.InputStreamReader;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

// TODO "do mock behaviour, not data". I could not found an easy way.
@RunWith(MockitoJUnitRunner.class)
public class NodeWrapperTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void calculateNextShouldDoOn2LevelTree() throws org.antlr.runtime.RecognitionException {

        // given
        TerminalNodeImpl root = mock(TerminalNodeImpl.class);
        TerminalNodeImpl leaf1 = mock(TerminalNodeImpl.class);
        TerminalNodeImpl leaf2 = mock(TerminalNodeImpl.class);
        given(leaf1.getParent()).willReturn(root);
        given(leaf2.getParent()).willReturn(root);
        given(root.getChild(0)).willReturn(leaf1);
        given(root.getChild(1)).willReturn(leaf2);
        given(root.getChildCount()).willReturn(2);

        // when / then
        assertThat(new NodeWrapper(leaf1, null).calculateNext(), is(leaf2));
        expectedException.expect(AssertionError.class);
        new NodeWrapper(leaf2, null).calculateNext();
    }

    @Test
    public void calculateNextShouldDoOn3LevelTree() throws org.antlr.runtime.RecognitionException {

        // given
        TerminalNodeImpl root = mock(TerminalNodeImpl.class);
        TerminalNodeImpl node1 = mock(TerminalNodeImpl.class);
        TerminalNodeImpl node2 = mock(TerminalNodeImpl.class);
        TerminalNodeImpl leaf1 = mock(TerminalNodeImpl.class);
        TerminalNodeImpl leaf2 = mock(TerminalNodeImpl.class);
        TerminalNodeImpl leaf3 = mock(TerminalNodeImpl.class);
        given(root.toString()).willReturn("root");
        given(node1.toString()).willReturn("node1");
        given(node2.toString()).willReturn("node2");
        given(leaf1.toString()).willReturn("leaf1");
        given(leaf2.toString()).willReturn("leaf2");
        given(leaf3.toString()).willReturn("leaf3");
        given(leaf1.getParent()).willReturn(node1);
        given(leaf2.getParent()).willReturn(node1);
        given(leaf3.getParent()).willReturn(node2);
        given(node1.getParent()).willReturn(root);
        given(node2.getParent()).willReturn(root);
        given(root.getChildCount()).willReturn(2);
        given(node1.getChildCount()).willReturn(2);
        given(node2.getChildCount()).willReturn(1);
        given(root.getChild(0)).willReturn(node1);
        given(root.getChild(1)).willReturn(node2);
        given(node1.getChild(0)).willReturn(leaf1);
        given(node1.getChild(1)).willReturn(leaf2);
        given(node2.getChild(0)).willReturn(leaf3);

        // when / then
        assertThat(new NodeWrapper(leaf1, null).calculateNext(), is(leaf2));
        assertThat(new NodeWrapper(leaf2, null).calculateNext(), is(leaf3));
        expectedException.expect(AssertionError.class);
        new NodeWrapper(leaf3, null).calculateNext();
    }

    @Test
    public void calculateNextShouldDoOnMixedLevelTree() throws org.antlr.runtime.RecognitionException {

        // given
        TerminalNodeImpl root = mock(TerminalNodeImpl.class);
        TerminalNodeImpl node = mock(TerminalNodeImpl.class);
        TerminalNodeImpl leaf1 = mock(TerminalNodeImpl.class);
        TerminalNodeImpl leaf2 = mock(TerminalNodeImpl.class);
        TerminalNodeImpl leaf3 = mock(TerminalNodeImpl.class);
        given(root.toString()).willReturn("root");
        given(node.toString()).willReturn("node");
        given(leaf1.toString()).willReturn("leaf1");
        given(leaf2.toString()).willReturn("leaf2");
        given(leaf3.toString()).willReturn("leaf3");
        given(leaf1.getParent()).willReturn(node);
        given(leaf2.getParent()).willReturn(node);
        given(leaf3.getParent()).willReturn(root);
        given(node.getParent()).willReturn(root);
        given(root.getChildCount()).willReturn(2);
        given(node.getChildCount()).willReturn(2);
        given(root.getChild(0)).willReturn(node);
        given(root.getChild(1)).willReturn(leaf3);
        given(node.getChild(0)).willReturn(leaf1);
        given(node.getChild(1)).willReturn(leaf2);

        // when / then
        assertThat(new NodeWrapper(leaf1, null).calculateNext(), is(leaf2));
        assertThat(new NodeWrapper(leaf2, null).calculateNext(), is(leaf3));
        expectedException.expect(AssertionError.class);
        new NodeWrapper(leaf3, null).calculateNext();
    }
}