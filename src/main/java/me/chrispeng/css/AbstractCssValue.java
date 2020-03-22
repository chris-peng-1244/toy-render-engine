package me.chrispeng.css;

import lombok.Getter;

public abstract class AbstractCssValue<T> implements CssValue<T> {
    @Getter private T value;

    AbstractCssValue(T value) {
        this.value = value;
    }
}
