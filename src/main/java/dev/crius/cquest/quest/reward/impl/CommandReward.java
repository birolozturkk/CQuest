package dev.crius.cquest.quest.reward.impl;

import dev.crius.cquest.placeholder.PlaceholderBuilder;
import dev.crius.cquest.quest.reward.QuestReward;
import dev.crius.cquest.utils.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class CommandReward implements QuestReward {
    private final String command;

    public CommandReward(String command) {
        this.command = command;
    }

    @Override
    public void applyTo(Player player) {
        String command = StringUtils.format(this.command, new PlaceholderBuilder().apply("%player%", player.getName()).build());
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command);
        System.out.println(command);
    }
}
