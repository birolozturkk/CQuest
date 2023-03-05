package dev.crius.cquest.api.event.customevents.impl;

import dev.crius.cquest.api.event.customevents.AbstractPlayerEvent;
import lombok.Getter;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

@Getter
public class PlayerBreedEvent extends AbstractPlayerEvent {

    private final EntityType entityType;

    public PlayerBreedEvent(Player player, EntityType entityType) {
        super(player);
        this.entityType = entityType;
    }
}
