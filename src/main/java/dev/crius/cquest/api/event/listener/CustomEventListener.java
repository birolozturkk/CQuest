package dev.crius.cquest.api.event.listener;

import com.cryptomorin.xseries.XMaterial;
import dev.crius.cquest.CQuest;
import dev.crius.cquest.api.event.impl.customevents.impl.HarvestEvent;
import dev.crius.cquest.api.event.impl.customevents.impl.PlantEvent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.Ageable;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

import java.util.Objects;

public class CustomEventListener implements Listener {

    @EventHandler
    public void harvest(BlockBreakEvent event){
        Player player = event.getPlayer();
        Block block = event.getBlock();

        Material material = block.getType();

        if (!isAllowedPlant(material))
            return;

        if(!(block.getBlockData() instanceof Ageable ageable)) return;

        if(ageable.getAge() != ageable.getMaximumAge()) return;

        Bukkit.getPluginManager().callEvent(new HarvestEvent(player, XMaterial.matchXMaterial(material)));
    }

    @EventHandler
    public void plant(BlockPlaceEvent event){
        Player player = event.getPlayer();
        Block block = event.getBlockPlaced();

        final Material material = block.getType();

        if (!isAllowedPlant(material))
            return;

        Bukkit.getPluginManager().callEvent(new PlantEvent(player, XMaterial.matchXMaterial(material)));
    }

    private boolean isAllowedPlant(Material material){
        return CQuest.getInstance().getConfiguration()
                .allowedPlants
                .stream()
                .map(plant -> Objects.requireNonNull(plant).parseMaterial())
                .toList()
                .contains(material);
    }
}