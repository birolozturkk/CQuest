package dev.crius.cquest.quest.requirement.impl.action;

import com.cryptomorin.xseries.XMaterial;
import dev.crius.cquest.CQuest;
import dev.crius.cquest.api.event.impl.customevents.impl.CraftItemEvent;
import dev.crius.cquest.database.QuestData;
import dev.crius.cquest.quest.Quest;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Map;
import java.util.Objects;

public class CraftItemQuestRequirement extends ActionQuestRequirement<CraftItemEvent> {

    private final Material type;

    public CraftItemQuestRequirement(Quest quest, Material type, int progress) {
        super(quest, CraftItemEvent.class, progress);
        this.type = type;
    }

    @Override
    public boolean isUpdatable(CraftItemEvent event) {
        if (event.getResult().getType().equals(type)) {
            QuestData questData = getQuestData(event.getPlayer());
            questData.setProgress(questData.getProgress() + event.getResultCount() - 1);
            return true;
        }
        return false;
    }
}
