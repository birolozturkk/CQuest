package dev.crius.cquest.quest;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dev.crius.cquest.CQuest;
import dev.crius.cquest.database.QuestData;
import dev.crius.cquest.quest.requirement.QuestRequirement;
import dev.crius.cquest.quest.requirement.impl.action.ActionQuestRequirement;
import dev.crius.cquest.quest.reward.QuestReward;
import dev.crius.cquest.utils.StringUtils;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.sound.Sound;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@NoArgsConstructor
@Data
public class Quest {

    private int id;
    private String name;
    private String description;
    private List<String> requirements = new ArrayList<>();
    private List<String> rewards = new ArrayList<>();

    @JsonIgnore
    private final List<QuestRequirement> questRequirements = new ArrayList<>();
    @JsonIgnore
    private final List<QuestReward> questRewards = new ArrayList<>();


    public Quest(int id) {
        this.id = id;
    }

    public Quest(String name, String description) {
        this.name = name;
        this.description = description;
    }

    private void finish(Player player) {
        questRewards.forEach(questReward -> questReward.applyTo(player));
        CQuest.getInstance().getQuestManager().assignQuest(player, getNext());
        CQuest.getInstance().getQuestManager().completeQuest(player, this);
        Audience receiver = CQuest.getInstance().adventure().player(player);
        String title = StringUtils.colorize(CQuest.getInstance().getConfiguration().messages.completedTitle);
        String subtitle = StringUtils.colorize(CQuest.getInstance().getConfiguration().messages.completedSubtitle);
        receiver.showTitle(Title.title(Component.text(title), Component.text(subtitle)));
    }

    private boolean control(Player player) {
        return questRequirements.stream().allMatch(questRequirement -> questRequirement.control(player));
    }

    private <T extends Event> void increment(T event, Player player) {
        getActionRequirements(event).stream()
                .filter(actionQuestRequirement -> actionQuestRequirement.isUpdatable(event))
                .filter(actionQuestRequirement -> !actionQuestRequirement.control(player))
                .forEach(actionQuestRequirement -> {
                    int requirementIndex = questRequirements.indexOf(actionQuestRequirement);
                    QuestData questData = CQuest.getInstance().getQuestManager().getQuestData(player,
                            id, requirementIndex);
                    questData.setProgress(questData.getProgress() + 1);
                    questData.setChanged(true);
                    CQuest.getInstance().getDatabaseManager().getQuestDataRepository().save(questData);
                });
    }

    @SuppressWarnings("unchecked")
    private <T extends Event> List<ActionQuestRequirement<T>> getActionRequirements(T event) {
        return questRequirements.stream()
                .filter(requirement -> requirement instanceof ActionQuestRequirement<?> &&
                        ((ActionQuestRequirement<?>) requirement).getTriggerClass().equals(event.getClass()))
                .map(requirement -> (ActionQuestRequirement<T>) requirement)
                .toList();
    }

    @SuppressWarnings("unchecked")
    private List<ActionQuestRequirement> getActionRequirements() {
        return questRequirements.stream()
                .filter(requirement -> requirement instanceof ActionQuestRequirement<?>)
                .map(requirement -> (ActionQuestRequirement) requirement)
                .toList();
    }


    private int getRequirementIndex(QuestRequirement questRequirement) {
        return questRequirements.indexOf(questRequirement);
    }

    public int getProgress(Player player) {
        return questRequirements.stream()
                .map(this::getRequirementIndex)
                .map(requirementIndex ->
                        CQuest.getInstance().getQuestManager().getQuestData(player, id, requirementIndex))
                .map(QuestData::getProgress)
                .reduce(0, Integer::sum);
    }


    public int getRequirementProgress() {
        return getActionRequirements().stream()
                .map(ActionQuestRequirement::getProgress)
                .reduce(0, Integer::sum);
    }

    public void accept(Event event, Player player) {
        if(getActionRequirements(event).isEmpty()) return;
        increment(event, player);
        CQuest.getInstance().getBossBarManager().update(player);
        accept(player);
    }

    private boolean isCompleted(Player player) {
        return CQuest.getInstance().getQuestManager().getCompletedQuests(player).contains(this);
    }

    public void accept(Player player) {
        if (control(player)) finish(player);
    }

    private Quest getNext() {
        Optional<Quest> next = CQuest.getInstance().getQuestManager().getQuest(id + 1);
        return next.orElse(new Quest(id + 1));
    }
}
