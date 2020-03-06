package me.chrispeng.parser;

import com.sun.org.apache.xml.internal.utils.DOMBuilder;
import com.sun.xml.internal.xsom.impl.Ref;
import lombok.AllArgsConstructor;
import me.chrispeng.dom.ElementData;
import me.chrispeng.dom.Node;
import me.chrispeng.dom.NodeBuilders;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.regex.Pattern;

public class Parser {
    private int position;
    private String input;
    private static final Pattern TAG_PATTERN = Pattern.compile("[a-zA-Z0-9]");

    private Parser(int position, String input) {
        this.position = position;
        this.input = input;
    }

    public static Node<ElementData> parse(String source) {
        Parser parser = new Parser(0, source);
        List<Node<?>> nodes = parser.parseNodes();
        // If the document contains a root element, return it; otherwise create one, and put all node inside it.
        if (nodes.size() == 1) {
            return (Node<ElementData>) nodes.get(0);
        }
        return NodeBuilders.element("html", new HashMap<>(), nodes);
    }

    private String nextChar() {
        return input.substring(position, position+1);
    }

    private boolean startsWith(String s) {
        return input.substring(position).startsWith(s);
    }

    private boolean eof() {
        return position >= input.length();
    }

    private String consumeChar() {
        String nextChar = nextChar();
        position++;
        return nextChar;
    }

    private String consumeWhile(Function<String, Boolean> test) {
        StringBuilder sb = new StringBuilder();
        while (!eof() && test.apply(nextChar())) {
            sb.append(consumeChar());
        }
        return sb.toString();
    }

    private void consumeWhitespace() {
        consumeWhile(s -> s.equals(" "));
    }

    private String parseTagName() {
        return consumeWhile(s -> TAG_PATTERN.matcher(s).matches());
    }

    private Node<?> parseNode() {
        String nextChar = nextChar();
        if (nextChar.equals("<")) {
            return parseElement();
        }
        return parseText();
    }

    private Node<String> parseText() {
        return NodeBuilders.text(consumeWhile(s -> !s.equals("<")));
    }

    private Node<ElementData> parseElement() {
        assert consumeChar().equals("<");
        String tagName = parseTagName();
        Map<String, String> attrs = parseAttributes();
        assert consumeChar().equals(">");

        List<Node<?>> children = parseNodes();
        assert consumeChar().equals("<");
        assert consumeChar().equals("/");
        assert parseTagName().equals(tagName);
        assert consumeChar().equals(">");
        return NodeBuilders.element(tagName, attrs, children);
    }

    public List<Node<?>> parseNodes() {
        List<Node<?>> nodes = new ArrayList<>();
        while (true) {
            consumeWhitespace();
            if (eof() || startsWith("</")) {
                break;
            }
            nodes.add(parseNode());
        }
        return nodes;
    }

    private Map<String, String> parseAttributes() {
        Map<String, String> attrs = new HashMap<>();
        while (true) {
            consumeWhitespace();
            if (nextChar().equals(">")) {
                break;
            }
            DomAttribute attribute = parseAttribute();
            attrs.put(attribute.name, attribute.value);
        }
        return attrs;
    }

    private DomAttribute parseAttribute() {
        String name = parseTagName();
        assert consumeChar().equals("=");
        String value = parseAttributeValue();
        return new DomAttribute(name, value);
    }

    private String parseAttributeValue() {
        String openQuote = consumeChar();
        assert openQuote.equals("\"") || openQuote.equals("'");
        String value = consumeWhile(c -> !c.equals(openQuote));
        assert consumeChar().equals(openQuote);
        return value;
    }

    @AllArgsConstructor
    private static class DomAttribute {
        String name;
        String value;
    }
}
