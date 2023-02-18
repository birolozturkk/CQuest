package dev.crius.cquest.quest.requirement.impl.action;

import dev.crius.cquest.quest.Quest;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;

public class PickupItemQuestRequirement extends ActionQuestRequirement<PlayerPickupItemEvent> {

    private final ItemStack itemStack;

    public PickupItemQuestRequirement(Quest quest, ItemStack itemStack, int progress) {
        super(quest, PlayerPickupItemEvent.class, progress);
        this.itemStack = itemStack;
    }

    @Override
    public boolean isUpdatable(PlayerPickupItemEvent event) {
        event.getItem().
        return event.getItem().getItemStack().isSimilar(itemStack);
    }
}
