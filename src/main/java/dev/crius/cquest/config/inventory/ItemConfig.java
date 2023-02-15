package dev.crius.cquest.config.inventory;

import com.cryptomorin.xseries.XMaterial;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@NoArgsConstructor
public class ItemConfig {

    public XMaterial material;
    public int amount;
    public String displayName;
    public boolean glowing;
    public String headData;
    public String headOwner;
    public UUID headOwnerUUID;
    public List<String> lore;
    public List<Integer> slots;
}