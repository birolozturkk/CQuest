package dev.crius.cquest.quest.requirement.impl.action;

import dev.crius.cquest.CQuest;
import dev.crius.cquest.api.event.impl.customevents.impl.PlayerPickupEvent;
import dev.crius.cquest.database.QuestData;
import dev.crius.cquest.quest.Quest;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;

public class PickupItemQuestRequirement extends ActionQuestRequirement<PlayerPickupEvent> {

    private final ItemStack itemStack;

    public PickupItemQuestRequirement(Quest quest, ItemStack itemStack, int progress) {
        super(quest, PlayerPickupEvent.class, progress);
        this.itemStack = itemStack;
    }

    @Override
    public boolean isUpdatable(PlayerPickupEvent event) {
        if (event.getItem().getPickupDelay() >= 39) return false;

        QuestData questData = getQuestData(event.getPlayer());
        questData.setProgress(questData.getProgress() + event.getItem().getItemStack().getAmount() - 1);
        return event.getItem().getItemStack().isSimilar(itemStack);
    }
}
