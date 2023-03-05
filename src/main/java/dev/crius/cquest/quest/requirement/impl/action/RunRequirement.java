package dev.crius.cquest.quest.requirement.impl.action;

import com.cryptomorin.xseries.XMaterial;
import dev.crius.cquest.api.event.customevents.impl.HarvestEvent;
import dev.crius.cquest.quest.Quest;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.Objects;

public class RunRequirement extends ActionQuestRequirement<PlayerMoveEvent> {

    public RunRequirement(Quest quest, int progress) {
        super(quest, PlayerMoveEvent.class, progress);
    }

    @Override
    public boolean isUpdatable(PlayerMoveEvent event) {
        return event.getFrom().getBlockX() != Objects.requireNonNull(event.getTo()).getBlockX() ||
                event.getFrom().getBlockZ() != event.getTo().getBlockZ();
    }
}
