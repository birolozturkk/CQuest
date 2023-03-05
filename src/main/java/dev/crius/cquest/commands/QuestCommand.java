package dev.crius.cquest.commands;

import dev.crius.cquest.CQuest;
import dev.crius.cquest.gui.QuestGUI;
import dev.crius.cquest.quest.Quest;
import dev.crius.cquest.quest.QuestPage;
import dev.crius.cquest.utils.StringUtils;
import dev.triumphteam.cmd.core.BaseCommand;
import dev.triumphteam.cmd.core.annotation.Command;
import dev.triumphteam.cmd.core.annotation.Default;
import dev.triumphteam.cmd.core.annotation.SubCommand;
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

    @SubCommand("next")
    public void nextQuest(Player player) {
        if(!player.hasPermission("cquest.next"))
            player.sendMessage(StringUtils.format(plugin.getConfiguration().messages.hasNotPermission));
        Optional<Quest> questOptional = plugin.getQuestManager().getQuest(player);
        questOptional.ifPresent(quest -> quest.finish(player));
    }
}
