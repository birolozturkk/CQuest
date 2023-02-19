package dev.crius.cquest.quest.requirement.impl.action;

import com.cryptomorin.xseries.XMaterial;
import dev.crius.cquest.api.event.impl.customevents.impl.PlayerBlockBreakEvent;
import dev.crius.cquest.quest.Quest;

public class BlockBreakRequirement extends ActionQuestRequirement<PlayerBlockBreakEvent> {

    private final XMaterial block;

    public BlockBreakRequirement(Quest quest, XMaterial block, int progress) {
        super(quest, PlayerBlockBreakEvent.class, progress);
        this.block = block;
    }

    @Override
    public boolean isUpdatable(PlayerBlockBreakEvent event) {
        return event.getMaterial().equals(block);
    }
}
