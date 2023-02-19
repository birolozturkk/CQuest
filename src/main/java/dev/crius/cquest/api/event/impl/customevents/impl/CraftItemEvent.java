package dev.crius.cquest.api.event.impl.customevents.impl;

import dev.crius.cquest.api.event.impl.customevents.AbstractPlayerEvent;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

@Getter
public class CraftItemEvent extends AbstractPlayerEvent {

    private final ItemStack result;
    private final int resultCount;

    public CraftItemEvent(Player who, ItemStack result, int resultCount) {
        super(who);
        this.result = result;
        this.resultCount = resultCount;
    }
}
