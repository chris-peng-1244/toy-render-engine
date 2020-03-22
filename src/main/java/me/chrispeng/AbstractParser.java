package me.chrispeng;

import java.util.function.Function;

public abstract class AbstractParser implements Parser {

    private int position;

    protected int getPosition() {
        return position;
    }

    protected void incrementPosition(int i) {
        position += i;
    }

    protected String getInput() {
        return input;
    }

    private String input;

    protected AbstractParser(int position, String input) {
        this.position = position;
        this.input = input;
    }

    @Override
    public boolean eof() {
        return position >= input.length();
    }

    @Override
    public String nextChar() {
        return input.substring(position, position+1);
    }

    @Override
    public String consumeChar() {
        String nextChar = nextChar();
        position++;
        return nextChar;
    }

    @Override
    public String consumeWhile(Function<String, Boolean> test) {
        StringBuilder sb = new StringBuilder();
        while (!eof() && test.apply(nextChar())) {
            sb.append(consumeChar());
        }
        return sb.toString();
    }

    @Override
    public void consumeWhitespace() {
        consumeWhile(s -> s.equals(" "));
    }

    @Override
    public boolean startsWith(String s) {
        return input.startsWith(s, position);
    }
}
