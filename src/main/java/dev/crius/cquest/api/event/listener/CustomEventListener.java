package dev.crius.cquest.api.event.listener;

import com.cryptomorin.xseries.XMaterial;
import dev.crius.cquest.CQuest;
import dev.crius.cquest.api.event.impl.customevents.impl.*;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.block.data.Ageable;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.List;
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
        if (victim.getHealth() > event.getFinalDamage()) return;

        Bukkit.getPluginManager().callEvent(new MobKillEvent(damager, victim, event.getCause()));
    }

    @EventHandler
    public void kill(EntityDeathEvent event) {
        if (event.getEntity() instanceof Player) return;

        if (!(event.getEntity() instanceof Mob)) return;

        List<ItemStack> drops = event.getDrops();
        drops.forEach(itemStack -> {
            ItemMeta itemMeta = itemStack.getItemMeta();
            itemMeta.getPersistentDataContainer().set(CQuest.getInstance().getIsDropsKey(),
                    PersistentDataType.BYTE, (byte) 0);
            itemStack.setItemMeta(itemMeta);
        });

    }

    @EventHandler
    public void craft(org.bukkit.event.inventory.CraftItemEvent event) {
        if (!(event.getWhoClicked() instanceof Player player)) return;

        ItemStack craftedItem = event.getRecipe().getResult();
        Inventory Inventory = event.getInventory();
        ClickType clickType = event.getClick();
        int realAmount = craftedItem.getAmount();
        if (clickType.isShiftClick()) {
            int lowerAmount = craftedItem.getMaxStackSize() + 1000; //Set lower at recipe result max stack size + 1000 (or just highter max stacksize of reciped item)
            for (ItemStack actualItem : Inventory.getContents()) //For each item in crafting inventory
            {
                if (!actualItem.getType().isAir() && lowerAmount > actualItem.getAmount() && !actualItem.getType().equals(craftedItem.getType())) //if slot is not air && lowerAmount is highter than this slot amount && it's not the recipe amount
                    lowerAmount = actualItem.getAmount(); //Set new lower amount
            }
            //Calculate the final amount : lowerAmount * craftedItem.getAmount
            realAmount = lowerAmount * craftedItem.getAmount();

        }
        Bukkit.getPluginManager().callEvent(new CraftItemEvent(player, event.getCurrentItem(), realAmount));
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


    @EventHandler
    public void pickup(EntityPickupItemEvent event) {
        if (!(event.getEntity() instanceof Player player)) return;
        Bukkit.getPluginManager().callEvent(new PlayerPickupEvent(player, event.getItem()));
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