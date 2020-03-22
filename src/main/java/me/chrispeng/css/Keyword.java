package me.chrispeng.css;

import lombok.Getter;

public class Keyword extends AbstractCssValue<String> {
    Keyword(String value) {
        super(value);
    }
}
