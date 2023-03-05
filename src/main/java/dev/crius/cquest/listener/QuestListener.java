package dev.crius.cquest.listener;

import dev.crius.cquest.CQuest;
import dev.crius.cquest.api.event.customevents.impl.*;
import dev.crius.cquest.quest.Quest;
import nbtapi.NBTItem;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.RegisteredListener;

import java.util.Optional;

public class QuestListener implements Listener {

    private final CQuest plugin;

    public QuestListener(CQuest plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        plugin.getBossBarManager().show(event.getPlayer());
    }

    @EventHandler
    public void dropItem(PlayerDropItemEvent event) {
        event.getItemDrop().setMetadata("isPlayer", new FixedMetadataValue(plugin, true));
    }


    @EventHandler
    public void pickup(PlayerPickupEvent event) {
        ItemStack itemStack = event.getItem().getItemStack();
        NBTItem nbtItem = new NBTItem(itemStack);
        if(nbtItem.hasKey("is-drops")) {
            nbtItem.removeKey("is-drops");
            nbtItem.applyNBT(itemStack);
            Bukkit.getPluginManager().callEvent(new ObtainEvent(event.getPlayer(), event.getItem()));
            return;
        }
        this.action(event);
    }

    @EventHandler
    public void fishing(PlayerFishEvent event) {
        this.action(event);
    }

    @EventHandler
    public void breed(PlayerBreedEvent event) {
        this.action(event);
    }

    @EventHandler
    public void furnaceExtract(FurnaceExtractEvent event) {
        this.action(event);
    }

    @EventHandler
    public void obtain(ObtainEvent event) {
        this.action(event);
    }

    @EventHandler
    public void craft(CraftItemEvent event) {
        this.action(event);
    }

    @EventHandler
    public void killPlayer(PlayerKillEvent event) {
        this.action(event);
    }

    @EventHandler
    public void killMob(MobKillEvent event) {
        this.action(event);
    }

    @EventHandler
    public void plant(PlantEvent event) {
        this.action(event);
    }

    @EventHandler
    public void harvest(HarvestEvent event) {
        this.action(event);
    }

    @EventHandler
    public void blockBreak(PlayerBlockBreakEvent event) {
        this.action(event);
    }

    @EventHandler
    public void blockPlace(PlayerBlockPlaceEvent event) {
        this.action(event);
    }

    public void action(PlayerEvent event) {
        Player player = event.getPlayer();
        Optional<Quest> quest = plugin.getQuestManager().getQuest(player);
        if (quest.isEmpty()) return;

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
