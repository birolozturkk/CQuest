package dev.crius.cquest.listener;

import dev.crius.cquest.CQuest;
import dev.crius.cquest.api.event.impl.customevents.impl.HarvestEvent;
import dev.crius.cquest.api.event.impl.customevents.impl.PlantEvent;
import dev.crius.cquest.quest.Quest;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.plugin.RegisteredListener;

import java.util.Optional;

public class QuestListener implements Listener {

    private final CQuest plugin;

    public QuestListener(CQuest plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void plant(PlantEvent event) {
        this.action(event);
    }

    @EventHandler
    public void harvest(HarvestEvent event) {
        this.action(event);
    }

    public void action(PlayerEvent event) {
        Player player = event.getPlayer();
        Optional<Quest> quest = plugin.getQuestManager().getQuest(player);
        if(quest.isEmpty()) return;

        quest.get().accept(event, player);
    }

    public void register() {
        RegisteredListener registered = new RegisteredListener(
                this,
                ((listener, event) -> {
                    if (event instanceof PlayerEvent)
                        this.action((PlayerEvent) event);
                }),
                EventPriority.NORMAL,
                CQuest.getInstance(),
                false
        );

        for (HandlerList handler : HandlerList.getHandlerLists())
            handler.register(registered);
    }

}
