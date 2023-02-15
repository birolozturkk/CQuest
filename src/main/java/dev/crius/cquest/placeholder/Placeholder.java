package dev.crius.cquest.placeholder;

import lombok.Getter;

@Getter
public class Placeholder {

    private final String key;
    private final String value;

    public Placeholder(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String process(String content) {
        if (content == null || content.isEmpty()) return "";
        return content.replace(key, value);
    }

    @Override
    public String toString() {
        return "Placeholder{" +
                "key='" + key + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
