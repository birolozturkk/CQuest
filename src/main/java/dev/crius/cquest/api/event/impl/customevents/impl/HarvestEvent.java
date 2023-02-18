package dev.crius.cquest.api.event.impl.customevents.impl;

import com.cryptomorin.xseries.XMaterial;
import dev.crius.cquest.api.event.impl.customevents.AbstractPlayerEvent;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

@Getter
public class HarvestEvent extends AbstractPlayerEvent {

    private final XMaterial crop;

    public HarvestEvent(Player who, @NotNull XMaterial crop) {
        super(who);
        this.crop = crop;
    }
}
