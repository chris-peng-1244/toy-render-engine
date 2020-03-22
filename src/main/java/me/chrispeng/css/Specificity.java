package me.chrispeng.css;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public final class Specificity implements Comparable<Specificity> {
    private int idCount;
    private int classCount;
    private int tagNameCount;

    @Override
    public int compareTo(Specificity o) {
        if (idCount != o.idCount) return Integer.compare(idCount, o.idCount);
        if (classCount != o.classCount) return Integer.compare(classCount, o.classCount);
        return Integer.compare(tagNameCount, o.tagNameCount);
    }
}
