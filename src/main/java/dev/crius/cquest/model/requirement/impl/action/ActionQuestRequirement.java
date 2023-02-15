package dev.crius.cquest.model.requirement.impl.action;

import dev.crius.cquest.CQuest;
import dev.crius.cquest.model.Quest;
import dev.crius.cquest.model.requirement.QuestRequirement;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

@Getter
public abstract class ActionQuestRequirement<T extends Event> extends QuestRequirement {

    private final Class<T> triggerClass;

    private final int progress;

    public ActionQuestRequirement(Quest quest, Class<T> triggerClass, int progress) {
        super(quest);
        this.triggerClass = triggerClass;
        this.progress = progress;
    }

    public abstract boolean isUpdatable(T event);

    @Override
    public boolean control(Player player) {
        return CQuest.getInstance().getQuestManager().getQuestData(player.getUniqueId(), quest.getId(),
                quest.getQuestRequirements().indexOf(this)).getProgress() >= progress;
    }
}
