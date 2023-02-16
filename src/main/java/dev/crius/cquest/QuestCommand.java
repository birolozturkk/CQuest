package dev.crius.cquest;

import dev.crius.cquest.gui.QuestGUI;
import dev.crius.cquest.quest.QuestPage;
import dev.triumphteam.cmd.core.BaseCommand;
import dev.triumphteam.cmd.core.annotation.Command;
import dev.triumphteam.cmd.core.annotation.Default;
import org.bukkit.entity.Player;

import java.util.Optional;

@Command(value = "quest")
public class QuestCommand extends BaseCommand {

    private final CQuest plugin;

    public QuestCommand(CQuest plugin) {
        this.plugin = plugin;
    }

    @Default
    public void execute(Player player) {
        Optional<QuestPage> quest = plugin.getQuestManager().getQuestPage(player);
        QuestGUI questGUI = new QuestGUI(player, quest.get());
        player.openInventory(questGUI.getInventory());
    }
}
