package dev.crius.cquest.quest.requirement.impl.state;

import dev.crius.cquest.quest.Quest;
import org.bukkit.entity.Player;

public class MoneyQuestRequirement extends StateQuestRequirement {

    public MoneyQuestRequirement(Quest quest) {
        super(quest);
    }

    @Override
    public boolean control(Player player) {
        return false;
    }
}
