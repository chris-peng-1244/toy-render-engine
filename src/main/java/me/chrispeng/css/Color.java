package me.chrispeng.css;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PACKAGE)
@Getter
public final class Color {
    private final int  red;
    private final int green;
    private final int blue;
    private final int alpha;
}
