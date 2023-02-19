package dev.crius.cquest.quest.reward.impl;

import dev.crius.cquest.quest.reward.QuestReward;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ItemReward implements QuestReward {

    private final ItemStack itemStack;
    private final int amount;

    public ItemReward(ItemStack itemStack, int amount) {
        this.itemStack = itemStack;
        this.amount = amount;
    }

    @Override
    public void applyTo(Player player) {
        for (int i = 0; i < amount; i++) {
            player.getInventory().addItem(itemStack);
        }
    }
}
