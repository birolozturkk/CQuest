package dev.crius.cquest.quest.requirement.impl.action;

import dev.crius.cquest.CQuest;
import dev.crius.cquest.database.QuestData;
import dev.crius.cquest.quest.Quest;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.CraftItemEvent;
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
        ItemStack result = event.getRecipe().getResult();
        System.out.println("debug1");
        if(!Objects.requireNonNull(result).isSimilar(itemStack)) return false;
        System.out.println("debug2");
        return Objects.requireNonNull(event.getCurrentItem()).isSimilar(itemStack);
    }
}
