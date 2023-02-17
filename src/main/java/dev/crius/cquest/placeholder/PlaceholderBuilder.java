package dev.crius.cquest.placeholder;

import dev.crius.cquest.CQuest;
import dev.crius.cquest.database.QuestData;
import dev.crius.cquest.quest.Quest;
import dev.crius.cquest.quest.requirement.QuestRequirement;
import dev.crius.cquest.quest.requirement.impl.action.ActionQuestRequirement;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class PlaceholderBuilder {

    private final List<Placeholder> placeholders = new ArrayList<>();

    public PlaceholderBuilder() {
        placeholders.add(new Placeholder("%prefix%", String.valueOf(CQuest.getInstance().getConfiguration().prefix)));
    }

    public PlaceholderBuilder applyForQuest(Quest quest, Player player) {
        placeholders.add(new Placeholder("%quest_description%", quest.getDescription()));

        int progress = quest.getProgress(player);
        int requirementProgress = quest.getRequirementProgress(player);
        placeholders.add(new Placeholder("%progress%", String.valueOf(progress)));
        placeholders.add(new Placeholder("%requirement_progress%", String.valueOf(requirementProgress)));
        placeholders.add(new Placeholder("%left_progress%", String.valueOf(requirementProgress - progress)));
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
