package dev.crius.cquest.quest.requirement.impl.action;

import dev.crius.cquest.api.event.customevents.impl.CraftItemEvent;
import dev.crius.cquest.api.event.customevents.impl.PlayerBlockBreakEvent;
import dev.crius.cquest.api.event.customevents.impl.PlayerBreedEvent;
import dev.crius.cquest.database.QuestData;
import dev.crius.cquest.quest.Quest;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;

public class BreedRequirement extends ActionQuestRequirement<PlayerBreedEvent> {

    private final EntityType type;

    public BreedRequirement(Quest quest, EntityType type, int progress) {
        super(quest, PlayerBreedEvent.class, progress);
        this.type = type;
    }

    @Override
    public boolean isUpdatable(PlayerBreedEvent event) {
        return event.getEntityType().equals(type);
    }
}
