package dev.crius.cquest.model.requirement.impl.state;

import dev.crius.cquest.model.Quest;
import dev.crius.cquest.model.requirement.QuestRequirement;

public abstract class StateQuestRequirement extends QuestRequirement {
    public StateQuestRequirement(Quest quest) {
        super(quest);
    }
}
