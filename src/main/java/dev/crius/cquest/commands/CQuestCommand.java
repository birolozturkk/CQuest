package dev.crius.cquest.commands;

import dev.crius.cquest.CQuest;
import dev.crius.cquest.utils.StringUtils;
import dev.triumphteam.cmd.core.BaseCommand;
import dev.triumphteam.cmd.core.annotation.Command;
import dev.triumphteam.cmd.core.annotation.SubCommand;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

@Command(value = "cquest")
public class CQuestCommand extends BaseCommand {

    private final CQuest plugin;

    public CQuestCommand(CQuest plugin) {
        this.plugin = plugin;
    }

    @SubCommand(value = "reload")
    public void reload(CommandSender sender) {
        plugin.loadConfigs();
        plugin.getQuestManager().load();
        sender.sendMessage(StringUtils.format(plugin.getConfiguration().messages.reloaded));
    }
}