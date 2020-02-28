package me.chrispeng.dom;

import java.util.List;
import java.util.Map;

public interface NodeBuilders {
    static TextNode text(String text) {
        return new TextNode(text);
    }

    static ElementNode element(String name, Map<String, String> attrMap, List<Node<?>> children) {
        ElementNode node = new ElementNode(new ElementData(name, attrMap));
        node.setChildren(children);
        return node;
    }
}
