package dev.crius.cquest.listener;

import com.cryptomorin.xseries.XMaterial;
import dev.crius.cquest.CQuest;
import dev.crius.cquest.api.event.impl.customevents.impl.*;
import dev.crius.cquest.quest.Quest;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
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
    public void pickup(PlayerPickupEvent event) {
        ItemStack itemStack = event.getItem().getItemStack();
        ItemMeta itemMeta = itemStack.getItemMeta();
        if(itemMeta.getPersistentDataContainer()
                .has(CQuest.getInstance().getIsDropsKey(), PersistentDataType.BYTE)) {
            itemMeta.getPersistentDataContainer().remove(CQuest.getInstance().getIsDropsKey());
            itemStack.setItemMeta(itemMeta);
            Bukkit.getPluginManager().callEvent(new ObtainEvent(event.getPlayer(), event.getItem()));
            return;
        }
        this.action(event);
    }
    @EventHandler
    public void drop(PlayerDropItemEvent event) {
        System.out.println(event.getItemDrop().getPickupDelay());
       Item item = event.getPlayer().getWorld().dropItem(event.getPlayer().getLocation(), XMaterial.DIAMOND_AXE.parseItem());
        System.out.println(item.getPickupDelay());
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
        System.out.println(event.getEventName());
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
