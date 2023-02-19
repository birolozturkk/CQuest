package dev.crius.cquest.quest.requirement.impl.action;

import dev.crius.cquest.CQuest;
import dev.crius.cquest.api.event.impl.customevents.impl.PlayerKillEvent;
import dev.crius.cquest.database.QuestData;
import dev.crius.cquest.quest.Quest;
import dev.crius.cquest.quest.requirement.QuestRequirement;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
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

    public boolean isUpdatable(T event) {
        return true;
    }

    @Override
    public boolean control(Player player) {
        return CQuest.getInstance().getQuestManager().getQuestData(player, quest.getId(),
                quest.getQuestRequirements().indexOf(this)).getProgress() >= progress;
    }

    protected QuestData getQuestData(Player player) {
        int requirementIndex = quest.getQuestRequirements().indexOf(this);
        return CQuest.getInstance().getQuestManager().getQuestData(player,
                quest.getId(), requirementIndex);

    }
}
