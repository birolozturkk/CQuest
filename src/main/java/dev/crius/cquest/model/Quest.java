package dev.crius.cquest.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dev.crius.cquest.CQuest;
import dev.crius.cquest.model.requirement.QuestRequirement;
import dev.crius.cquest.model.requirement.impl.action.ActionQuestRequirement;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Data
public class Quest {

    private int id;
    private String description;
    private List<String> requirements = new ArrayList<>();

    @JsonIgnore
    private final List<QuestRequirement> questRequirements = new ArrayList<>();

    public Quest(int id, String description) {
        this.id = id;
        this.description = description;
    }

    private boolean control(Player player) {
        return questRequirements.stream().allMatch(questRequirement -> questRequirement.control(player));
    }

    @SuppressWarnings("unchecked")
    private <T extends Event> void increment(T event, Player player) {
        questRequirements.stream()
                .filter(requirement -> requirement instanceof ActionQuestRequirement<?> &&
                        ((ActionQuestRequirement<?>) requirement).getTriggerClass().equals(event.getClass()))
                .map(requirement -> (ActionQuestRequirement<T>) requirement)
                .filter(actionQuestRequirement -> actionQuestRequirement.isUpdatable(event))
                .filter(actionQuestRequirement -> CQuest.getInstance().getQuestManager().getQuestData(player.getUniqueId(),
                        actionQuestRequirement.getQuest().getId(),
                        actionQuestRequirement.getQuest().getQuestRequirements().indexOf(actionQuestRequirement)).getProgress() < actionQuestRequirement.getProgress())
                .forEach(actionQuestRequirement -> {
                    int requirementIndex = questRequirements.indexOf(actionQuestRequirement);
                    QuestData questData = CQuest.getInstance().getQuestManager().getQuestData(player.getUniqueId(),
                            id, requirementIndex);
                    questData.setProgress(questData.getProgress() + 1);
                    questData.setChanged(true);
                    CQuest.getInstance().getDatabaseManager().getQuestDataRepository().save(questData);
                });
    }

    private void finish(Player player) {
        player.sendMessage("Congrats Quest is finished");
        CQuest.getInstance().getQuestManager().assignQuest(player, getNext());
    }

    public void accept(Event event, Player player) {
        increment(event, player);
        accept(player);
    }

    public void accept(Player player) {
        if (control(player)) finish(player);
    }

    private Quest getNext() {
        return CQuest.getInstance().getQuestManager().getQuest(id + 1);
    }
}
