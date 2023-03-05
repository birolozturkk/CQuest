package dev.crius.cquest.quest.requirement.impl.action;

import dev.crius.cquest.api.event.customevents.impl.PlayerKillEvent;
import dev.crius.cquest.quest.Quest;

public class PlayerKilRequirement extends ActionQuestRequirement<PlayerKillEvent> {

    public PlayerKilRequirement(Quest quest, int progress) {
        super(quest, PlayerKillEvent.class, progress);
    }

    @Override
    public boolean isUpdatable(PlayerKillEvent event) {
        return true;
    }
}
