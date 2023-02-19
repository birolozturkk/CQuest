package dev.crius.cquest.managers;

import com.cryptomorin.xseries.XMaterial;
import dev.crius.cquest.CQuest;
import dev.crius.cquest.database.ActiveQuest;
import dev.crius.cquest.database.CompletedQuest;
import dev.crius.cquest.database.QuestData;
import dev.crius.cquest.quest.*;
import dev.crius.cquest.quest.requirement.DefaultQuestRequirement;
import dev.crius.cquest.quest.requirement.QuestRequirement;
import dev.crius.cquest.quest.requirement.impl.action.*;
import dev.crius.cquest.quest.requirement.impl.state.ItemQuestRequirement;
import dev.crius.cquest.quest.requirement.impl.state.MoneyQuestRequirement;
import dev.crius.cquest.quest.reward.QuestReward;
import dev.crius.cquest.quest.reward.impl.DefaultQuestReward;
import dev.crius.cquest.quest.reward.impl.ItemReward;
import dev.crius.cquest.quest.reward.impl.MoneyReward;
import dev.crius.cquest.repository.impl.ActiveQuestRepository;
import dev.crius.cquest.repository.impl.CompletedQuestRepository;
import dev.crius.cquest.repository.impl.QuestDataRepository;
import org.bukkit.entity.EntityType;
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
                            case "PLACE" -> questRequirement = new BlockPlaceRequirement(quest,
                                    XMaterial.matchXMaterial(args[1]).orElse(XMaterial.AIR), Integer.parseInt(args[2]));
                            case "PICKUP" -> questRequirement = new PickupItemQuestRequirement(quest,
                                    XMaterial.matchXMaterial(args[1]).orElse(XMaterial.AIR).parseMaterial(), Integer.parseInt(args[2]));
                            case "BREAK" -> questRequirement = new BlockBreakRequirement(quest,
                                    XMaterial.matchXMaterial(args[1]).orElse(XMaterial.AIR), Integer.parseInt(args[2]));
                            case "CRAFT_ITEM" -> questRequirement = new CraftItemQuestRequirement(quest,
                                    XMaterial.matchXMaterial(args[1]).orElse(XMaterial.AIR).parseMaterial(), Integer.parseInt(args[2]));
                            case "KILL_PLAYER" ->
                                    questRequirement = new PlayerKilRequirement(quest, Integer.parseInt(args[1]));
                            case "KILL_MOB" -> questRequirement = new MobKilRequirement(quest,
                                    EntityType.valueOf(args[1]), Integer.parseInt(args[2]));
                            case "HARVEST" -> questRequirement = new HarvestRequirement(quest,
                                    XMaterial.matchXMaterial(args[1]).orElse(XMaterial.AIR), Integer.parseInt(args[2]));
                            case "PLANT" -> questRequirement = new PlantRequirement(quest,
                                    XMaterial.matchXMaterial(args[1]).orElse(XMaterial.AIR), Integer.parseInt(args[2]));
                            default -> {
                                questRequirement = new DefaultQuestRequirement(quest);
                                plugin.log("requirement was not found", Level.SEVERE);
                            }
                        }
                        quest.getQuestRequirements().add(questRequirement);
                    });

            quest.getRewards().stream()
                    .map(reward -> reward.split(" "))
                    .forEach(args -> {
                        QuestReward questReward;
                        switch (args[0]) {
                            case "ITEM" -> questReward = new ItemReward(XMaterial.matchXMaterial(args[1])
                                    .orElse(XMaterial.AIR)
                                    .parseItem(), Integer.parseInt(args[2]));
                            case "MONEY" -> questReward = new MoneyReward(Double.parseDouble(args[1]));
                            default -> {
                                questReward = new DefaultQuestReward();
                                plugin.log("reward was not found", Level.SEVERE);
                            }
                        }
                        quest.getQuestRewards().add(questReward);
                    });
        });

    }

    public void assignQuest(Player player, Quest quest) {
        Optional<ActiveQuest> activeQuestOptional = getActiveQuest(player);
        if (activeQuestOptional.isEmpty()) {
            ActiveQuest activeQuest = new ActiveQuest(player.getUniqueId(), quest.getId());
            activeQuestRepository.addEntry(activeQuest);
            activeQuestRepository.save(activeQuest);
            plugin.getBossBarManager().update(player);
            return;
        }
        ActiveQuest activeQuest = activeQuestOptional.get();
        activeQuest.setQuestId(quest.getId());
        activeQuest.setChanged(true);
        activeQuestRepository.save(activeQuest);
        plugin.getBossBarManager().update(player);
    }

    public void completeQuest(Player player, Quest quest) {
        CompletedQuest completedQuest = new CompletedQuest(player.getUniqueId(), quest.getId());
        completedQuestRepository.addEntry(completedQuest);
        completedQuestRepository.save(completedQuest);
    }

    public Optional<Quest> getQuest(Player player) {
        Optional<ActiveQuest> activeQuest = getActiveQuest(player);
        if (activeQuest.isEmpty()) {
            Quest defaultQuest = quests.get(1);
            assignQuest(player, defaultQuest);
            return Optional.of(defaultQuest);
        }
        return getQuest(activeQuest.get().getQuestId());
    }


    public QuestData getQuestData(Player player, int questId, int requirementIndex) {
        Optional<QuestData> optionalQuestData = questDataRepository.getQuestData(player.getUniqueId(), questId, requirementIndex);
        if (optionalQuestData.isPresent()) return optionalQuestData.get();
        QuestData questData = new QuestData(player.getUniqueId(), questId, requirementIndex);
        questDataRepository.addEntry(questData);
        return questData;
    }

    public Optional<Quest> getQuest(int id) {
        return Optional.ofNullable(quests.get(id));
    }

    public List<Quest> getCompletedQuests(Player player) {
        return completedQuestRepository.getCompletedQuests(player.getUniqueId()).stream()
                .map(completedQuest -> getQuest(completedQuest.getId()))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();
    }

    public Optional<ActiveQuest> getActiveQuest(Player player) {
        return activeQuestRepository.getActiveQuest(player.getUniqueId());
    }

    public Optional<QuestPage> getQuestPage(Player player) {
        Optional<ActiveQuest> activeQuest = getActiveQuest(player);
        Optional<Quest> quest = getQuest(player);
        if (quest.isEmpty() && activeQuest.isPresent())
            quest = getQuest(activeQuest.get().getQuestId() - 1);
        Quest finalQuest = quest.get();
        return questPages.stream()
                .filter(questPage -> questPage.getQuests().containsValue(finalQuest))
                .findFirst();
    }
}
