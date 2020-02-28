package me.chrispeng.dom;

public class DomPrinter {
    public static void prettyPrint(Node<?> node, int depth) {
        for (int i = 0; i < depth; i++) {
            System.out.print("  ");
        }
        System.out.print(node.getNodeData());
        if (hasSingleTextNode(node)) {
            System.out.println(node.getChildren().get(0));
        } else {
            System.out.println();
            for (Node<?> child : node.getChildren()) {
                prettyPrint(child, depth + 1);
            }
        }
    }

    private static boolean hasSingleTextNode(Node<?> node) {
        if (node.getChildren().size() > 1) return false;
        return node.getChildren().get(0) instanceof TextNode;
    }
}
