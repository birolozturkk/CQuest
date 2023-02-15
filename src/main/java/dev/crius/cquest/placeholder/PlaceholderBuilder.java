package dev.crius.cquest.placeholder;

import dev.crius.cquest.CQuest;
import dev.crius.cquest.model.ActiveQuest;
import dev.crius.cquest.model.Quest;
import dev.crius.cquest.model.QuestData;
import dev.crius.cquest.model.requirement.QuestRequirement;
import dev.crius.cquest.model.requirement.impl.action.ActionQuestRequirement;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

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
