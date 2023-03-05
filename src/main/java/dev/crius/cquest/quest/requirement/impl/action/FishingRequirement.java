package dev.crius.cquest.quest.requirement.impl.action;

import dev.crius.cquest.quest.Quest;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.event.player.PlayerFishEvent;

public class FishingRequirement extends ActionQuestRequirement<PlayerFishEvent> {

    private final Material fishType;
    public FishingRequirement(Quest quest, Material fishType, int progress) {
        super(quest, PlayerFishEvent.class, progress);
        this.fishType = fishType;
    }

    @Override
    public boolean isUpdatable(PlayerFishEvent event) {
        if(event.getCaught() == null) return false;
        System.out.println(((Item) event.getCaught()).getItemStack().getType());
        return ((Item) event.getCaught()).getItemStack().getType().equals(fishType);
    }
}
