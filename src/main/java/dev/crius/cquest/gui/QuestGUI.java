package dev.crius.cquest.gui;

import com.github.scropytr.legendinventoryapi.gui.PaginatedGUI;
import com.github.scropytr.legendinventoryapi.item.Item;
import com.github.scropytr.legendinventoryapi.item.ItemBuilder;
import dev.crius.cquest.CQuest;
import dev.crius.cquest.config.inventory.QuestGUIConfig;
import dev.crius.cquest.model.ActiveQuest;
import dev.crius.cquest.model.Quest;
import dev.crius.cquest.model.QuestData;
import dev.crius.cquest.model.QuestPage;
import dev.crius.cquest.model.requirement.QuestRequirement;
import dev.crius.cquest.placeholder.Placeholder;
import dev.crius.cquest.placeholder.PlaceholderBuilder;
import dev.crius.cquest.utils.ItemUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class QuestGUI extends PaginatedGUI<Quest> {

    private final Player player;
    private final QuestPage questPage;

    public QuestGUI(Player player, QuestPage questPage) {
        this.player = player;
        this.questPage = questPage;
    }

    @Override
    public String getTitle() {
        return questPage.getGui().title;
    }

    @Override
    public int getSize() {
        return questPage.getGui().size;
    }

    public List<Integer> getSlots() {
        return questPage.getGui().questItem.canCompleteQuest.slots;
    }

    @Override
    public List<Quest> getPaginatedObjects() {
        return questPage.getQuests().values().stream().toList();
    }

    @Override
    public Item getItem(Quest quest) {

        QuestGUIConfig questGUIConfig = CQuest.getInstance().getQuestGUIConfig();
        ItemBuilder itemBuilder;

        List<Placeholder> placeholders = new PlaceholderBuilder().applyForQuest(quest).build();

        Optional<ActiveQuest> activeQuestOptional = CQuest.getInstance().getQuestManager().getActiveQuest(player);
        List<Quest> completedQuests = CQuest.getInstance().getQuestManager().getCompletedQuests(player);
        if (activeQuestOptional.isPresent()) {
            itemBuilder = ItemUtils.makeItem(questGUIConfig.questItem.canCompleteQuest);
        } else if (completedQuests.contains(quest)) {
            itemBuilder.setType(questGUIConfig.questItem.completedQuest.material);
        } else {
            itemBuilder.setType(questGUIConfig.questItem.canNotCompleteQuest.material);
        }

        return new ItemBui;
    }

    public Map<Integer, Quest> getQuests() {
        return questPage.getQuests();
    }

    public Item getItem(Quest quest) {
        List<QuestData> questData = quest.getQuestRequirements().stream()
                .map(questRequirement -> CQuest.getInstance().getQuestManager().getQuestData(player.getUniqueId(),
                        quest.getId(), getQuestRequirementIndex(questRequirement)))
                .toList();
        List<Placeholder> placeholders = new PlaceholderBuilder()
                .applyForQuest(quest, questData)
                .build();
        return ItemUtils.makeItem(questPage.getGui().questItem.completedQuest, placeholders);
    }

    private int getQuestRequirementIndex(QuestRequirement questRequirement) {
        return questRequirement.getQuest().getQuestRequirements().indexOf(questRequirement);
    }

    @Override
    public void addContent() {

    }

    @Override
    public void onOpen(InventoryOpenEvent event) {

    }

    @Override
    public void onClose(InventoryCloseEvent event) {

    }

    @Override
    public void onClick(InventoryClickEvent event) {

    }


}
