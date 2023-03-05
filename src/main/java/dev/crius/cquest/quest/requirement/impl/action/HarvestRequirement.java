package dev.crius.cquest.quest.requirement.impl.action;

import com.cryptomorin.xseries.XMaterial;
import dev.crius.cquest.api.event.customevents.impl.HarvestEvent;
import dev.crius.cquest.quest.Quest;

public class HarvestRequirement extends ActionQuestRequirement<HarvestEvent> {

    private final XMaterial crop;

    public HarvestRequirement(Quest quest, XMaterial crop, int progress) {
        super(quest, HarvestEvent.class, progress);
        this.crop = crop;
    }

    @Override
    public boolean isUpdatable(HarvestEvent event) {
        return event.getCrop().equals(crop);
    }
}
