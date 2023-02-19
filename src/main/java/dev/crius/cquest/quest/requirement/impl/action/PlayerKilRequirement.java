package dev.crius.cquest.quest.requirement.impl.action;

import dev.crius.cquest.api.event.impl.customevents.impl.PlayerKillEvent;
import dev.crius.cquest.quest.Quest;

public class PlayerKilRequirement extends ActionQuestRequirement<PlayerKillEvent> {

    public PlayerKilRequirement(Quest quest, int progress) {
        super(quest, PlayerKillEvent.class, progress);
    }
}
