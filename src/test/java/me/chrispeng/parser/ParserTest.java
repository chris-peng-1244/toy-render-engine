package me.chrispeng.parser;

import me.chrispeng.dom.ElementData;
import me.chrispeng.dom.Node;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ParserTest {

    @Test
    void parse() {
        Node<ElementData> root = Parser.parse("<html><h1>Hello</h1><div id=\"title\"><p>Nice " +
            "Paragraph</p></div></html>");
        assertEquals("html", root.getNodeData().getTagName());
        assertEquals(2, root.getChildren().size());
        Node<ElementData> div = (Node<ElementData>) root.getChildren().get(1);
        assertEquals("title", div.getNodeData().getAttrMap().get("id"));
    }
}