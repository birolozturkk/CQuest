package dev.crius.cquest.api.event.listener;

import com.cryptomorin.xseries.XMaterial;
import dev.crius.cquest.CQuest;
import dev.crius.cquest.api.event.impl.customevents.impl.*;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.Ageable;
import org.bukkit.entity.Animals;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.util.Objects;

public class CustomEventListener implements Listener {

    @EventHandler
    public void harvest(BlockBreakEvent event) {
        Player player = event.getPlayer();
        Block block = event.getBlock();

        Material material = block.getType();

        if (!isAllowedPlant(material))
            return;

        if (!(block.getBlockData() instanceof Ageable ageable)) return;

        if (ageable.getAge() != ageable.getMaximumAge()) return;

        Bukkit.getPluginManager().callEvent(new HarvestEvent(player, XMaterial.matchXMaterial(material)));
    }

    @EventHandler
    public void plant(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        Block block = event.getBlockPlaced();

        final Material material = block.getType();

        if (!isAllowedPlant(material))
            return;

        Bukkit.getPluginManager().callEvent(new PlantEvent(player, XMaterial.matchXMaterial(material)));
    }

    @EventHandler
    public void kill(EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Player damager)) return;

        if (event.getEntity() instanceof Player) {
            Bukkit.getPluginManager().callEvent(new PlayerKillEvent(damager, (Player) event.getEntity(), event.getCause()));
            return;
        }
        if (!(event.getEntity() instanceof Mob victim)) return;
        if (!isAllowedMob(victim)) return;
        if (victim.getHealth() > event.getFinalDamage()) return;

        Bukkit.getPluginManager().callEvent(new MobKillEvent(damager, victim, event.getCause()));
    }

    @EventHandler
    public void craft(org.bukkit.event.inventory.CraftItemEvent event) {
        if(!(event.getWhoClicked() instanceof Player player)) return;
        Bukkit.getPluginManager().callEvent(new CraftItemEvent(player, event.getCurrentItem()));
    }

    @EventHandler
    public void blockBreak(BlockBreakEvent event) {
        Bukkit.getPluginManager().callEvent(new PlayerBlockBreakEvent(event.getPlayer(),
                XMaterial.matchXMaterial(event.getBlock().getType())));
    }

    @EventHandler
    public void blockPlace(BlockPlaceEvent event) {
        Bukkit.getPluginManager().callEvent(new PlayerBlockPlaceEvent(event.getPlayer(),
                XMaterial.matchXMaterial(event.getBlock().getType())));
    }

    private boolean isAllowedMob(Mob mob) {
        return CQuest.getInstance().getConfiguration()
                .allowedMobs
                .stream()
                .toList()
                .contains(mob.getType());
    }

    private boolean isAllowedPlant(Material material) {
        return CQuest.getInstance().getConfiguration()
                .allowedPlants
                .stream()
                .map(plant -> Objects.requireNonNull(plant).parseMaterial())
                .toList()
                .contains(material);
    }
}