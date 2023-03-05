package dev.crius.cquest.quest.requirement.impl.action;

import com.cryptomorin.xseries.XMaterial;
import dev.crius.cquest.api.event.customevents.impl.FurnaceExtractEvent;
import dev.crius.cquest.database.QuestData;
import dev.crius.cquest.quest.Quest;

public class FurnaceExtractRequirement extends ActionQuestRequirement<FurnaceExtractEvent> {

    private final XMaterial type;

    public FurnaceExtractRequirement(Quest quest, XMaterial type, int progress) {
        super(quest, FurnaceExtractEvent.class, progress);
        this.type = type;
    }

    @Override
    public boolean isUpdatable(FurnaceExtractEvent event) {
        if (event.getFood().equals(type)) {
            QuestData questData = getQuestData(event.getPlayer());
            questData.setProgress(questData.getProgress() + event.getAmount() - 1);
            return true;
        }
        return false;
    }
}
