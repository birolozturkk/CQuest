package dev.crius.cquest.api.event.customevents.impl;

import com.cryptomorin.xseries.XMaterial;
import dev.crius.cquest.api.event.customevents.AbstractPlayerEvent;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

@Getter
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
