package dev.crius.cquest.api.event.customevents.impl;

import dev.crius.cquest.api.event.customevents.AbstractPlayerEvent;
import lombok.Getter;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;

@Getter
public class PlayerPickupEvent extends AbstractPlayerEvent {

    private final Item item;

    public PlayerPickupEvent(Player player, Item item) {
        super(player);
        this.player = player;
        this.item = item;
    }
}
