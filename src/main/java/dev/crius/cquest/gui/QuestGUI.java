package dev.crius.cquest.gui;

import com.github.scropytr.legendinventoryapi.gui.PaginatedGUI;
import com.github.scropytr.legendinventoryapi.item.Item;
import com.github.scropytr.legendinventoryapi.item.ItemBuilder;
import dev.crius.cquest.CQuest;
import dev.crius.cquest.quest.Quest;
import dev.crius.cquest.quest.QuestPage;
import dev.crius.cquest.quest.requirement.QuestRequirement;
import dev.crius.cquest.placeholder.Placeholder;
import dev.crius.cquest.placeholder.PlaceholderBuilder;
import dev.crius.cquest.utils.ItemUtils;
import dev.crius.cquest.utils.StringUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;

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
        return StringUtils.colorize(questPage.getGui().title);
    }

    @Override
    public int getSize() {
        return questPage.getGui().size;
    }

    public List<Integer> getSlots() {
        return questPage.getGui().questItem.slots;
    }

    @Override
    public List<Quest> getPaginatedObjects() {
        return questPage.getQuests().values().stream().toList();
    }

    @Override
    public Item getItem(Quest quest) {
        Item item;
        List<Placeholder> placeholders = new PlaceholderBuilder().applyForQuest(quest, player).build();

        Optional<Quest> activeQuestOptional = CQuest.getInstance().getQuestManager().getQuest(player);
        List<Quest> completedQuests = CQuest.getInstance().getQuestManager().getCompletedQuests(player);
        if (activeQuestOptional.isPresent() && activeQuestOptional.get() == quest) {
            item = ItemUtils.makeItem(questPage.getGui().questItem.canCompleteQuest, placeholders);
        } else if (completedQuests.contains(quest)) {
            item = ItemUtils.makeItem(questPage.getGui().questItem.completedQuest, placeholders);
        } else {
            item = ItemUtils.makeItem(questPage.getGui().questItem.canNotCompleteQuest, placeholders);
        }

        return item;
    }

    @Override
    public void addContent() {
        super.addContent();
        questPage.getGui().items.values()
                .forEach(itemConfig -> setItem(ItemUtils.makeItem(itemConfig, new PlaceholderBuilder().build()),
                        itemConfig.slots));
    }

    @Override
    public void onOpen(InventoryOpenEvent event) {

    }

    @Override
    public void onClose(InventoryCloseEvent event) {

    }

    @Override
    public void onClick(InventoryClickEvent event) {
        event.setCancelled(true);
    }


}
