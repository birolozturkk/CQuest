package dev.crius.cquest.model.requirement;

import dev.crius.cquest.model.Quest;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

@Getter
public abstract class QuestRequirement {

    protected final Quest quest;

    public QuestRequirement(Quest quest) {
        this.quest = quest;
    }

    public abstract boolean control(Player player);
}
