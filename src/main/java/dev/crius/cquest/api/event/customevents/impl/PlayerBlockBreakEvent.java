package dev.crius.cquest.api.event.customevents.impl;

import com.cryptomorin.xseries.XMaterial;
import dev.crius.cquest.api.event.customevents.AbstractPlayerEvent;
import lombok.Getter;
import org.bukkit.entity.Player;

@Getter
public class PlayerBlockBreakEvent extends AbstractPlayerEvent {

    private final XMaterial material;

    public PlayerBlockBreakEvent(Player who, XMaterial material) {
        super(who);
        this.material = material;
    }
}
