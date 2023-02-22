package dev.crius.cquest.quest.requirement.impl.action;

import dev.crius.cquest.api.event.impl.customevents.impl.PlayerPickupEvent;
import dev.crius.cquest.database.QuestData;
import dev.crius.cquest.quest.Quest;
import org.bukkit.Material;

public class PickupItemRequirement extends ActionQuestRequirement<PlayerPickupEvent> {

    private final Material type;

    public PickupItemRequirement(Quest quest, Material type, int progress) {
        super(quest, PlayerPickupEvent.class, progress);
        this.type = type;
    }

    @Override
    public boolean isUpdatable(PlayerPickupEvent event) {
        if (event.getItem().getPickupDelay() >= 39) return false;
        if (type.equals(event.getItem().getItemStack().getType())) {
            QuestData questData = getQuestData(event.getPlayer());
            questData.setProgress(questData.getProgress() + event.getItem().getItemStack().getAmount() - 1);
            return true;
        }
        return false;
    }
}
