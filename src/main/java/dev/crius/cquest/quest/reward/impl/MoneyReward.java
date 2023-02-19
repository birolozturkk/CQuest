package dev.crius.cquest.quest.reward.impl;

import dev.crius.cquest.CQuest;
import dev.crius.cquest.quest.reward.QuestReward;
import org.bukkit.entity.Player;

public class MoneyReward implements QuestReward {

    private final double quantity;

    public MoneyReward(double quantity) {
        this.quantity = quantity;
    }

    @Override
    public void applyTo(Player player) {
        CQuest.getInstance().getEconomyHook().add(player, quantity);
    }
}
