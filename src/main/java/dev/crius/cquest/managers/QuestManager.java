package dev.crius.cquest.managers;

import dev.crius.cquest.CQuest;
import dev.crius.cquest.model.ActiveQuest;
import dev.crius.cquest.model.Quest;
import dev.crius.cquest.model.QuestData;
import dev.crius.cquest.model.QuestPage;
import dev.crius.cquest.model.requirement.DefaultQuestRequirement;
import dev.crius.cquest.model.requirement.QuestRequirement;
import dev.crius.cquest.model.requirement.impl.action.HarvestQuestRequirement;
import dev.crius.cquest.model.requirement.impl.action.PlantQuestRequirement;
import dev.crius.cquest.model.requirement.impl.state.ItemQuestRequirement;
import dev.crius.cquest.model.requirement.impl.state.MoneyQuestRequirement;
import dev.crius.cquest.repository.impl.ActiveQuestRepository;
import dev.crius.cquest.repository.impl.CompletedQuestRepository;
import dev.crius.cquest.repository.impl.QuestDataRepository;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.*;
import java.util.logging.Level;
import java.util.stream.Collectors;

public class QuestManager {


    private final CQuest plugin;
    private final QuestDataRepository questDataRepository;
    private final ActiveQuestRepository activeQuestRepository;
    private final CompletedQuestRepository completedQuestRepository;

    private List<QuestPage> questPages = new ArrayList<>();
    private Map<Integer, Quest> quests = new HashMap<>();

    public QuestManager(CQuest plugin) {
        this.plugin = plugin;
        this.questDataRepository = plugin.getDatabaseManager().getQuestDataRepository();
        this.activeQuestRepository = plugin.getDatabaseManager().getActiveQuestRepository();
        this.completedQuestRepository = plugin.getDatabaseManager().getCompletedQuestRepository();
    }


    public void load() {
        quests.clear();
        questPages.clear();
        if (!plugin.getDataFolder().exists())
            plugin.getDataFolder().mkdirs();

        File questPagesFolder = new File(plugin.getDataFolder(), "questpages");
        if (!questPagesFolder.exists())
            questPagesFolder.mkdirs();

        questPages = Arrays.stream(questPagesFolder.listFiles((dir, name) -> name.endsWith(".yml")))
                .map(file -> plugin.getPersist().load(QuestPage.class,
                        questPagesFolder.getName() + "/" +
                                file.getName().substring(0, file.getName().lastIndexOf('.'))))
                .collect(Collectors.toList());

        quests = questPages.stream()
                .map(questPage -> questPage.getQuests().entrySet())
                .flatMap(Collection::stream)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));


        quests.values().forEach(quest -> {
            quest.getRequirements().stream()
                    .map(requirement -> requirement.split(" "))
                    .forEach(args -> {
                        QuestRequirement questRequirement;
                        switch (args[0]) {
                            case "ITEM" -> questRequirement = new ItemQuestRequirement(quest);
                            case "MONEY" -> questRequirement = new MoneyQuestRequirement(quest);
                            case "HARVEST" -> questRequirement = new HarvestQuestRequirement(quest,
                                    XMaterial.matchXMaterial(args[1]).orElse(XMaterial.AIR), Integer.parseInt(args[2]));
                            case "PLANT" -> questRequirement = new PlantQuestRequirement(quest,
                                    XMaterial.matchXMaterial(args[1]).orElse(XMaterial.AIR), Integer.parseInt(args[2]));
                            default -> {
                                questRequirement = new DefaultQuestRequirement(quest);
                                plugin.log("requirement was not found", Level.SEVERE);
                            }
                        }
                        quest.getQuestRequirements().add(questRequirement);
                    });
        });

    }

    public void assignQuest(Player player, Quest quest) {
        if (quest == null) return;
        Optional<ActiveQuest> activeQuestOptional = getActiveQuest(player);
        if (activeQuestOptional.isEmpty()) {
            ActiveQuest activeQuest = new ActiveQuest(player.getUniqueId(), quest.getId());
            activeQuestRepository.addEntry(activeQuest);
            activeQuestRepository.save(activeQuest);
            return;
        }
        ActiveQuest activeQuest = activeQuestOptional.get();
        activeQuest.setQuestId(quest.getId());
        activeQuest.setChanged(true);
        activeQuestRepository.save(activeQuest);
    }

    public Quest getQuest(Player player) {
        Optional<ActiveQuest> activeQuest = getActiveQuest(player);
        if (activeQuest.isEmpty()) {
            Quest defaultQuest = quests.get(1);
            assignQuest(player, defaultQuest);
            return defaultQuest;
        }
        return quests.get(activeQuest.get().getQuestId());
    }


    public QuestData getQuestData(UUID playerUUID, int questId, int requirementIndex) {
        Optional<QuestData> optionalQuestData = questDataRepository.getQuestData(playerUUID, questId, requirementIndex);
        if (optionalQuestData.isPresent()) return optionalQuestData.get();
        QuestData questData = new QuestData(playerUUID, questId, requirementIndex);
        questDataRepository.addEntry(questData);
        return questData;
    }

    public Quest getQuest(int id) {
        return quests.get(id);
    }

    public List<Quest> getCompletedQuests(Player player) {
        return completedQuestRepository.getCompletedQuests(player.getUniqueId()).stream()
                .map(completedQuest -> getQuest(completedQuest.getId()))
                .toList();
    }

    public Optional<ActiveQuest> getActiveQuest(Player player) {
        return activeQuestRepository.getActiveQuest(player.getUniqueId());
    }
}
