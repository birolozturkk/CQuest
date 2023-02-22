package dev.crius.cquest.quest.requirement.impl.action;

import dev.crius.cquest.api.event.impl.customevents.impl.ObtainEvent;
import dev.crius.cquest.database.QuestData;
import dev.crius.cquest.quest.Quest;
import org.bukkit.Material;

public class ObtainRequirement extends ActionQuestRequirement<ObtainEvent> {

    private final Material type;
    public ObtainRequirement(Quest quest, Material type, int progress) {
        super(quest, ObtainEvent.class, progress);
        this.type = type;
    }

    @Override
    public boolean isUpdatable(ObtainEvent event) {
        if (event.getItem().getItemStack().getType().equals(type)) {
            QuestData questData = getQuestData(event.getPlayer());
            questData.setProgress(questData.getProgress() + event.getItem().getItemStack().getAmount() - 1);
            return true;
        }
        return false;
    }
}
