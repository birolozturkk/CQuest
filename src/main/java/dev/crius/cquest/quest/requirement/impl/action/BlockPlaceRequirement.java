package dev.crius.cquest.quest.requirement.impl.action;

import com.cryptomorin.xseries.XMaterial;
import dev.crius.cquest.api.event.customevents.impl.PlayerBlockPlaceEvent;
import dev.crius.cquest.quest.Quest;

public class BlockPlaceRequirement extends ActionQuestRequirement<PlayerBlockPlaceEvent> {

    private final XMaterial block;

    public BlockPlaceRequirement(Quest quest, XMaterial block, int progress) {
        super(quest, PlayerBlockPlaceEvent.class, progress);
        this.block = block;
    }

    @Override
    public boolean isUpdatable(PlayerBlockPlaceEvent event) {
        return event.getMaterial().equals(block);
    }
}
