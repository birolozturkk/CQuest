package dev.crius.cquest.quest.requirement.impl.action;

import dev.crius.cquest.CQuest;
import dev.crius.cquest.api.event.impl.customevents.impl.CraftItemEvent;
import dev.crius.cquest.database.QuestData;
import dev.crius.cquest.quest.Quest;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Objects;

public class CraftItemQuestRequirement extends ActionQuestRequirement<CraftItemEvent> {

    private final ItemStack itemStack;

    public CraftItemQuestRequirement(Quest quest, ItemStack itemStack, int progress) {
        super(quest, CraftItemEvent.class, progress);
        this.itemStack = itemStack;
    }

    @Override
    public boolean isUpdatable(CraftItemEvent event) {
        int requirementIndex = quest.getQuestRequirements().indexOf(this);
        QuestData questData = CQuest.getInstance().getQuestManager().getQuestData(event.getPlayer(),
                quest.getId(), requirementIndex);
        questData.setProgress(questData.getProgress() + event.getResult().getAmount() - 1);
        return Objects.requireNonNull(event.getResult()).isSimilar(itemStack);
    }
}
