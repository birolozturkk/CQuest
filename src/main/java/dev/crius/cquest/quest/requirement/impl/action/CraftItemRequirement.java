package dev.crius.cquest.quest.requirement.impl.action;

import dev.crius.cquest.api.event.impl.customevents.impl.CraftItemEvent;
import dev.crius.cquest.database.QuestData;
import dev.crius.cquest.quest.Quest;
import org.bukkit.Material;

public class CraftItemRequirement extends ActionQuestRequirement<CraftItemEvent> {

    private final Material type;

    public CraftItemRequirement(Quest quest, Material type, int progress) {
        super(quest, CraftItemEvent.class, progress);
        this.type = type;
    }

    @Override
    public boolean isUpdatable(CraftItemEvent event) {
        if (event.getResult().getType().equals(type)) {
            QuestData questData = getQuestData(event.getPlayer());
            questData.setProgress(questData.getProgress() + event.getResultCount() - 1);
            return true;
        }
        return false;
    }
}
