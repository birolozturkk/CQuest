package dev.crius.cquest.config;

import com.cryptomorin.xseries.XMaterial;
import dev.crius.cquest.gui.QuestGUI;
import net.kyori.adventure.bossbar.BossBar;
import org.bukkit.entity.EntityType;

import java.util.Arrays;
import java.util.List;

public class Configuration {

    public BossBar bossBar = new BossBar();

    public List<XMaterial> allowedPlants = Arrays.asList(XMaterial.OAK_SAPLING, XMaterial.POTATOES, XMaterial.WHEAT);
    public Messages messages = new Messages();

    public static class Messages {

        public String prefix = "&3CQuest &8>>";
        public String reloaded = "%prefix% config dosyaları güncellendi";
        public String completedTitle = "&aTebrikler";
        public String completedSubtitle = "&fGörevi Başarıyla Tamamladın";


    }
    public static class BossBar {
        public String content = "&d%quest_description%";
        public net.kyori.adventure.bossbar.BossBar.Color color = net.kyori.adventure.bossbar.BossBar.Color.RED;
        public net.kyori.adventure.bossbar.BossBar.Overlay segment = net.kyori.adventure.bossbar.BossBar.Overlay.NOTCHED_10;
    }

}
