package dev.crius.cquest.quest.requirement.impl.action;

import dev.crius.cquest.api.event.impl.customevents.impl.ObtainEvent;
import dev.crius.cquest.quest.Quest;

public class ObtainQuestRequirement extends ActionQuestRequirement<ObtainEvent> {
    public ObtainQuestRequirement(Quest quest, Class<ObtainEvent> triggerClass, int progress) {
        super(quest, triggerClass, progress);
    }

    @Override
    public boolean isUpdatable(ObtainEvent event) {

    }
}
