package dev.crius.cquest.quest.requirement.impl.action;

import com.cryptomorin.xseries.XMaterial;
import dev.crius.cquest.api.event.impl.customevents.impl.PlayerBlockPlaceEvent;
import dev.crius.cquest.quest.Quest;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlockPlaceQuestRequirement extends ActionQuestRequirement<PlayerBlockPlaceEvent> {

    private final XMaterial block;

    public BlockPlaceQuestRequirement(Quest quest, XMaterial block, int progress) {
        super(quest, PlayerBlockPlaceEvent.class, progress);
        this.block = block;
    }

    @Override
    public boolean isUpdatable(PlayerBlockPlaceEvent event) {
        return event.getMaterial().equals(block);
    }
}
