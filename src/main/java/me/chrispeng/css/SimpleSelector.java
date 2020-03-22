package me.chrispeng.css;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SimpleSelector {
    private Optional<String> tagName = Optional.empty();
    private Optional<String> id = Optional.empty();
    private List<String> classes = new ArrayList<>();

    public Specificity getSpecificity() {
        int tagNameCount = tagName.isPresent() ? 1 : 0;
        int idCount = id.isPresent() ? 1 : 0;
        int classCount = classes.size();
        return new Specificity(idCount, classCount, tagNameCount);
    }

    public void setId(String id) {
        this.id = Optional.of(id);
    }

    public void setTagName(String tagName) {
        this.tagName = Optional.of(tagName);
    }

    public void addClass(String className) {
        this.classes.add(className);
    }
}
