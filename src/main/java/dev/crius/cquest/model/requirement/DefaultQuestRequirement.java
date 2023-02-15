package dev.crius.cquest.model.requirement;

import dev.crius.cquest.model.Quest;
import org.bukkit.entity.Player;

public class DefaultQuestRequirement extends QuestRequirement{

    public DefaultQuestRequirement(Quest quest) {
        super(quest);
    }

    @Override
    public boolean control(Player player) {
        return false;
    }
}
