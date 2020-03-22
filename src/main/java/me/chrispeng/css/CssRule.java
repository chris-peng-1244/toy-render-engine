package me.chrispeng.css;

import java.util.Collections;
import java.util.List;

public final class CssRule {
    private final List<SimpleSelector> selectors;
    private final List<Declaration> declarations;

    public CssRule(List<SimpleSelector> selectors, List<Declaration> declarations) {
        this.selectors = selectors;
        this.declarations = declarations;
    }

    public List<SimpleSelector> getSelectors() {
        return Collections.unmodifiableList(selectors);
    }

    public List<Declaration> getDeclarations() {
        return Collections.unmodifiableList(declarations);
    }
}
