package me.chrispeng;

import java.util.function.Function;

public interface Parser {
    boolean eof();
    String nextChar();
    String consumeChar();
    String consumeWhile(Function<String, Boolean> test);
    boolean startsWith(String s);
    void consumeWhitespace();
}
