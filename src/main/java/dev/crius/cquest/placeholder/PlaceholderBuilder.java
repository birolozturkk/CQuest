package dev.crius.cquest.placeholder;

import dev.crius.cquest.CQuest;
import dev.crius.cquest.quest.Quest;

import java.util.ArrayList;
import java.util.List;

public class PlaceholderBuilder {

    private final List<Placeholder> placeholders = new ArrayList<>();

    public PlaceholderBuilder() {
        placeholders.add(new Placeholder("%prefix%", String.valueOf(CQuest.getInstance().getConfiguration().prefix)));
    }

    public PlaceholderBuilder applyForQuest(Quest quest) {
        placeholders.add(new Placeholder("%quest_description%", quest.getDescription()));
        return this;
    }

    public PlaceholderBuilder apply(String key, String value) {
        placeholders.add(new Placeholder(key, value));
        return this;
    }

    public List<Placeholder> build() {
        return placeholders;
    }
}
