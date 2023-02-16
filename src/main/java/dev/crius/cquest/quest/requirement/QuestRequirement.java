package dev.crius.cquest.quest.requirement;

import dev.crius.cquest.quest.Quest;
import lombok.Getter;
import org.bukkit.entity.Player;

@Getter
public abstract class QuestRequirement {

    protected final Quest quest;

    public QuestRequirement(Quest quest) {
        this.quest = quest;
    }

    public abstract boolean control(Player player);
}
