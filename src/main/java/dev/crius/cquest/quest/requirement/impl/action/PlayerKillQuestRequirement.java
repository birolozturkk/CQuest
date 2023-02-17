package dev.crius.cquest.quest.requirement.impl.action;

import dev.crius.cquest.api.event.impl.customevents.impl.PlayerKillEvent;
import dev.crius.cquest.quest.Quest;
import org.bukkit.event.entity.EntityDamageEvent;

public class PlayerKillQuestRequirement extends ActionQuestRequirement<PlayerKillEvent> {

    public PlayerKillQuestRequirement(Quest quest, int progress) {
        super(quest, PlayerKillEvent.class, progress);
    }
}
