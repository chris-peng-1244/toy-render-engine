package me.chrispeng.dom;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public abstract class Node<T> {
    private List<Node<?>> children = new ArrayList<>();
    private T nodeData;

    public Node(T nodeData) {
        this.nodeData = nodeData;
    }

    public void addChild(Node<?> child) {
        children.add(child);
    }

    public void setChildren(List<Node<?>> children) {
        this.children = children;
    }
}
