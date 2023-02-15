package dev.crius.cquest.model.requirement.impl.action;

import dev.crius.cquest.api.event.impl.customevents.impl.HarvestEvent;
import dev.crius.cquest.model.Quest;

public class HarvestQuestRequirement extends ActionQuestRequirement<HarvestEvent> {

    private final XMaterial crop;

    public HarvestQuestRequirement(Quest quest, XMaterial crop, int progress) {
        super(quest, HarvestEvent.class, progress);
        this.crop = crop;
    }

    @Override
    public boolean isUpdatable(HarvestEvent event) {
        return event.getCrop().equals(crop);
    }
}
