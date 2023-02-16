package dev.crius.cquest.quest.requirement;

import dev.crius.cquest.quest.Quest;
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
