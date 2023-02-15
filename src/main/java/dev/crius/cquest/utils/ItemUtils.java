package dev.crius.cquest.utils;

import com.cryptomorin.xseries.XMaterial;
import com.github.scropytr.legendinventoryapi.item.Item;
import com.github.scropytr.legendinventoryapi.item.ItemBuilder;
import dev.crius.cquest.config.inventory.ItemConfig;
import dev.crius.cquest.placeholder.Placeholder;

import java.util.List;

public class ItemUtils {

    public static Item makeItem(ItemConfig itemConfig, List<Placeholder> placeholders) {

        if(itemConfig == null) return new Item(XMaterial.AIR);
        String displayName = StringUtils.format(itemConfig.displayName, placeholders);
        List<String> lore = StringUtils.format(itemConfig.lore, placeholders);

        ItemBuilder itemBuilder = new ItemBuilder(itemConfig.material).setDisplayName(displayName)
                .setLore(lore)
                .glowing(itemConfig.glowing)
                .setAmount(itemConfig.amount);

        if (itemConfig.headData != null) {
            itemBuilder.setHeadData(itemConfig.headData);
        } else if (itemConfig.headOwnerUUID != null) {
            itemBuilder.setHeadData(SkinUtils.getHeadData(itemConfig.headOwnerUUID));
        }else if (itemConfig.headOwner != null) {
            itemBuilder.setHeadData(SkinUtils.getHeadData(SkinUtils.getUUID(StringUtils.processPlaceholders(itemConfig.headOwner, placeholders))));
        }

        return itemBuilder.build();
    }


}
