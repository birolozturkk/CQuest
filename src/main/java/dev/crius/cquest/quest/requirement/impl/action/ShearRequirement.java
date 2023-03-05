package dev.crius.cquest.quest.requirement.impl.action;

import dev.crius.cquest.api.event.customevents.impl.PlayerBreedEvent;
import dev.crius.cquest.quest.Quest;
import org.bukkit.entity.EntityType;
import org.bukkit.event.player.PlayerShearEntityEvent;

public class ShearRequirement extends ActionQuestRequirement<PlayerShearEntityEvent> {

    public ShearRequirement(Quest quest, int progress) {
        super(quest, PlayerShearEntityEvent.class, progress);
    }

    @Override
    public boolean isUpdatable(PlayerShearEntityEvent event) {
        return event.getEntity().getType() == EntityType.SHEEP;
    }
}
