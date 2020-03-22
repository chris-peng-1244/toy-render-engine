package me.chrispeng.css;

import me.chrispeng.AbstractParser;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Pattern;

public class CssParser extends AbstractParser {

    protected CssParser(int position, String input) {
        super(position, input);
    }
    private static final Pattern IDENTIFIER_PATTERN = Pattern.compile("[a-zA-Z0-9\\-_]");

    private List<CssRule> parseRules() {
        List<CssRule> rules = new ArrayList<>();
        while (true) {
            consumeWhitespace();
            if (eof()) break;
            rules.add(parseRule());
        }
        return rules;
    }

    private CssRule parseRule() {
        List<SimpleSelector> selectors = parseSelectors();
        List<Declaration> declarations = parseDeclarations();
        return new CssRule(selectors, declarations);
    }

    private List<SimpleSelector> parseSelectors() {
        List<SimpleSelector> selectors = new ArrayList<>();
        while (true) {
            selectors.add(parseSimpleSelector());
            consumeWhitespace();
            String nextChar = nextChar();
            if (nextChar.equals(",")) {
                consumeChar();
                consumeWhitespace();
            } else if (nextChar.equals("{")) break;
            else throw new RuntimeException("Unexpected character " + nextChar + " in selector list");
        }
        selectors.sort(Comparator.comparing(SimpleSelector::getSpecificity));
        return selectors;
    }

    private SimpleSelector parseSimpleSelector() {
        SimpleSelector simpleSelector = new SimpleSelector();
        while (!eof()) {
            String nextChar = nextChar();
            if (nextChar.equals("#")) {
                consumeChar();
                simpleSelector.setId(parseIdentifier());
            } else if (nextChar.equals(".")) {
                consumeChar();
                simpleSelector.addClass(parseIdentifier());
            } else if (nextChar.equals("*")) {
                consumeChar();
            } else if (isValidIdentifierChar(nextChar)) {
                simpleSelector.setTagName(parseIdentifier());
            } else {
                break;
            }
        }
        return simpleSelector;
    }

    private String parseIdentifier() {
        return consumeWhile(this::isValidIdentifierChar);
    }

    private boolean isValidIdentifierChar(String s) {
        return IDENTIFIER_PATTERN.matcher(s).matches();
    }

    private List<Declaration> parseDeclarations() {
        String nextChar = consumeChar();
        assert !nextChar.equals("{");
        List<Declaration> declarations = new ArrayList<>();
        while (true) {
            consumeWhitespace();
            nextChar = nextChar();
            if (nextChar.equals("}")) {
                consumeChar();
                break;
            }
            declarations.add(parseDeclaration());
        }
        return declarations;
    }

    private Declaration parseDeclaration() {
        String propertyName = parseIdentifier();
        consumeWhitespace();
        String nextChar = consumeChar();
        assert !nextChar.equals(":");
        consumeWhitespace();
        CssValue<?> value = parseValue();
        consumeWhitespace();
        nextChar = consumeChar();
        assert !nextChar.equals(";");
        return new Declaration(propertyName, value);
    }

    private CssValue<?> parseValue() {
        String nextChar = nextChar();
        if (isDigit(nextChar)) {
            return parseLength();
        } else if (nextChar.equals("#")) {
            return parseColor();
        } else {
            return new Keyword(parseIdentifier());
        }
    }

    private boolean isDigit(String c) {
        return c.matches("\\d");
    }

    private Length parseLength() {
        Length length = new Length(parseFloat());
        // TODO implement length unit
        parseUnit();
        return length;
    }

    private float parseFloat() {
        String floatStr = consumeWhile(c -> isDigit(c) || c.equals("."));
        return Float.parseFloat(floatStr);
    }

    private LengthUnit parseUnit() {
        String unit = parseIdentifier().toLowerCase();
        return LengthUnit.valueOf(unit);
    }

    private ColorValue parseColor() {
        String c = consumeChar();
        assert !c.equals("#");
        return new ColorValue(new Color(
            parseHexPair(),
            parseHexPair(),
            parseHexPair(),
            255
        ));
    }

    private int parseHexPair() {
        String s = getInput().substring(getPosition(), getPosition()+2);
        incrementPosition(2);
        return Integer.parseInt(s, 16);
    }
}
