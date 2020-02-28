package me.chrispeng.dom;

public class TextNode extends Node<String> {
    public TextNode(String nodeData) {
        super(nodeData);
    }

    @Override
    public String toString() {
        return getNodeData();
    }
}
