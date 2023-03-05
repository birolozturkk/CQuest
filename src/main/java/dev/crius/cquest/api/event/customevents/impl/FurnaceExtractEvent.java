package dev.crius.cquest.api.event.customevents.impl;

import com.cryptomorin.xseries.XMaterial;
import dev.crius.cquest.api.event.customevents.AbstractPlayerEvent;
import lombok.Getter;
import org.bukkit.entity.Player;

@Getter
public class FurnaceExtractEvent extends AbstractPlayerEvent {

    private final XMaterial food;
    private final int amount;

    public FurnaceExtractEvent(Player who, XMaterial food, int amount) {
        super(who);
        this.food = food;
        this.amount = amount;
    }
}
