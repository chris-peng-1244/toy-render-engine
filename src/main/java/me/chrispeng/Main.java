package me.chrispeng;

import me.chrispeng.dom.DomPrinter;
import me.chrispeng.dom.ElementData;
import me.chrispeng.dom.Node;
import me.chrispeng.dom.NodeBuilders;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Main {

    public static void main(String[] args) {
        Node<ElementData> h1 = NodeBuilders.element(
            "h1",
            new HashMap<>(),
            Collections.singletonList(NodeBuilders.text("Hello")));
        Node<ElementData> h2 = NodeBuilders.element(
            "h2",
            new HashMap<>(),
            Collections.singletonList(NodeBuilders.text("World")));
        Map<String, String> bodyAttr = new HashMap<>();
        bodyAttr.put("id", "container");
        Node<ElementData> root = NodeBuilders.element(
            "body",
            bodyAttr,
            Arrays.asList(h1, h2)
        );
        DomPrinter.prettyPrint(root, 0);

    }
}
