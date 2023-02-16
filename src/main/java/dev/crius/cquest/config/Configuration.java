package dev.crius.cquest.config;

import com.cryptomorin.xseries.XMaterial;
import dev.crius.cquest.gui.QuestGUI;

import java.util.Arrays;
import java.util.List;

public class Configuration {

    public String prefix;
    public List<XMaterial> allowedPlants = Arrays.asList(XMaterial.OAK_SAPLING, XMaterial.POTATOES, XMaterial.WHEAT);
}
