package dev.crius.cquest.config.inventory;

import lombok.NoArgsConstructor;

import java.util.Map;

@NoArgsConstructor
public class InventoryConfig extends NoItemGUI {

    public Map<String, ItemConfig> items;

    public InventoryConfig(int size, String title, Map<String, ItemConfig> items) {
        this.size = size;
        this.title = title;
        this.items = items;
    }
}
