package dev.crius.cquest.quest;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dev.crius.cquest.CQuest;
import dev.crius.cquest.database.QuestData;
import dev.crius.cquest.quest.requirement.QuestRequirement;
import dev.crius.cquest.quest.requirement.impl.action.ActionQuestRequirement;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@NoArgsConstructor
@Data
public class Quest {

    private int id;
    private String description;
    private List<String> requirements = new ArrayList<>();

    @JsonIgnore
    private final List<QuestRequirement> questRequirements = new ArrayList<>();


    public Quest(int id) {
        this.id = id;
    }

    public Quest(int id, String description) {
        this.id = id;
        this.description = description;
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
    public List<ActionQuestRequirement> getActionRequirements() {
        return questRequirements.stream()
                .filter(requirement -> requirement instanceof ActionQuestRequirement<?>)
                .map(requirement -> (ActionQuestRequirement) requirement)
                .toList();
    }

    private void finish(Player player) {
        player.sendMessage("Congrats Quest is finished");
        CQuest.getInstance().getQuestManager().assignQuest(player, getNext());
        CQuest.getInstance().getQuestManager().completeQuest(player, this);
    }

    public void accept(Event event, Player player) {
        if(getActionRequirements(event).isEmpty()) return;
        increment(event, player);
        accept(player);
    }

    public void accept(Player player) {
        if (control(player)) finish(player);
    }

    private Quest getNext() {
        Optional<Quest> next = CQuest.getInstance().getQuestManager().getQuest(id + 1);
        return next.orElse(new Quest(id + 1));
    }
}
