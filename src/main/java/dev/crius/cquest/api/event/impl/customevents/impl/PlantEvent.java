package dev.crius.cquest.api.event.impl.customevents.impl;

import dev.crius.cquest.api.event.impl.customevents.AbstractPlayerEvent;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class PlantEvent extends AbstractPlayerEvent {

    private final XMaterial plant;

    public PlantEvent(Player who, @NotNull XMaterial plant) {
        super(who);
        this.plant = plant;
    }

    public XMaterial getPlant() {
        return plant;
    }
}
