package dev.crius.cquest.quest.requirement.impl.action;

import dev.crius.cquest.api.event.impl.customevents.impl.MobKillEvent;
import dev.crius.cquest.quest.Quest;
import org.bukkit.entity.EntityType;

public class MobKilRequirement extends ActionQuestRequirement<MobKillEvent> {

    private final EntityType mob;

    public MobKilRequirement(Quest quest, EntityType mob, int progress) {
        super(quest, MobKillEvent.class, progress);
        this.mob = mob;
    }

    @Override
    public boolean isUpdatable(MobKillEvent event) {
        return event.getVictim().getType().equals(mob);
    }
}
