package dev.crius.cquest.model.requirement.impl.state;

import dev.crius.cquest.model.Quest;
import dev.crius.cquest.model.requirement.QuestRequirement;
import org.bukkit.entity.Player;

public class ItemQuestRequirement extends StateQuestRequirement {
    public ItemQuestRequirement(Quest quest) {
        super(quest);
    }

    @Override
    public boolean control(Player player) {
        return false;
    }
}
