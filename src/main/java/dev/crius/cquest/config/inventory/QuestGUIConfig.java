package dev.crius.cquest.config.inventory;


import com.cryptomorin.xseries.XMaterial;
import com.google.common.collect.ImmutableMap;
import com.j256.ormlite.stmt.query.In;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class QuestGUIConfig extends NoItemGUI {

    public Map<String, ItemConfig> items = ImmutableMap.<String, ItemConfig>builder().put("background", new ItemConfig(XMaterial.BLACK_STAINED_GLASS, 1, "",
            false, null, null, null, Collections.emptyList(), Collections.emptyList())).build();
    public QuestItem questItem = new QuestItem();

    public static class QuestItem {

        public List<Integer> slots = Arrays.asList(0, 9, 18, 27, 36, 45, 2, 11, 20, 29, 38, 47, 4, 13, 22, 31, 40, 49, 6, 15, 24, 33, 42, 51);
        public dev.crius.cquest.config.inventory.ItemConfig completedQuest = new dev.crius.cquest.config.inventory.ItemConfig(XMaterial.GOLD_INGOT, 1, "&eGörev Tamamlandı", false,
                null, null, null, Collections.emptyList());
        public dev.crius.cquest.config.inventory.ItemConfig canCompleteQuest = new dev.crius.cquest.config.inventory.ItemConfig(XMaterial.COAL, 1, "&e%quest_name%", false,
                null, null, null, Arrays.asList("", ""));
        public dev.crius.cquest.config.inventory.ItemConfig canNotCompleteQuest = new dev.crius.cquest.config.inventory.ItemConfig(XMaterial.GOLD_INGOT, 1, "&e???", false,
                null, null, null, Collections.emptyList());
    }

    @NoArgsConstructor
    public static class ItemConfig extends dev.crius.cquest.config.inventory.ItemConfig {
        public List<Integer> slots;

        public ItemConfig(XMaterial material, int amount, String displayName, boolean glowing, String headData, String headOwner, UUID headOwnerUUID, List<String> lore, List<Integer> slots) {
            super(material, amount, displayName, glowing, headData, headOwner, headOwnerUUID, lore);
            this.slots = slots;
        }
    }
}