package dev.crius.cquest.api.event.impl.customevents.impl;

import com.cryptomorin.xseries.XMaterial;
import dev.crius.cquest.api.event.impl.customevents.AbstractPlayerEvent;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

@Getter
public class CraftItemEvent extends AbstractPlayerEvent {

    private final ItemStack result;

    public CraftItemEvent(Player who, ItemStack result) {
        super(who);
        this.result = result;
    }
}
