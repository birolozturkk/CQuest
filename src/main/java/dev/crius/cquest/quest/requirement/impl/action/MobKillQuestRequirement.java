package dev.crius.cquest.quest.requirement.impl.action;

import dev.crius.cquest.api.event.impl.customevents.impl.MobKillEvent;
import dev.crius.cquest.api.event.impl.customevents.impl.PlayerKillEvent;
import dev.crius.cquest.quest.Quest;
import org.bukkit.entity.EntityType;
import org.bukkit.event.entity.EntityDamageEvent;

public class MobKillQuestRequirement extends ActionQuestRequirement<MobKillEvent> {

    private final EntityType mob;

    public MobKillQuestRequirement(Quest quest, EntityType mob, int progress) {
        super(quest, MobKillEvent.class, progress);
        this.mob = mob;
    }

    @Override
    public boolean isUpdatable(MobKillEvent event) {
        return event.getVictim().getType().equals(mob);
    }
}
