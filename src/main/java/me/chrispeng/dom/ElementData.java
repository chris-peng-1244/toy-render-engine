package me.chrispeng.dom;

import lombok.Getter;

import java.util.Map;

@Getter
public class ElementData {
    private String tagName;
    private Map<String, String> attrMap;

    public ElementData(String tagName, Map<String, String> attrMap) {
        this.tagName = tagName;
        this.attrMap = attrMap;
    }

    @Override
    public String toString() {
        String content = tagName + " " + printAttr();
        return "<" + content.trim() + ">";
    }

    private String printAttr() {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> entry: attrMap.entrySet()) {
            sb.append(entry.getKey());
            sb.append("=");
            sb.append(entry.getValue());
            sb.append(" ");
        }
        return sb.toString();
    }
}
