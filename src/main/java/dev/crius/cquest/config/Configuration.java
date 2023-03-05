package dev.crius.cquest.config;

import com.cryptomorin.xseries.XMaterial;
import dev.crius.cquest.gui.QuestGUI;
import net.kyori.adventure.bossbar.BossBar;
import org.bukkit.entity.EntityType;

import java.util.Arrays;
import java.util.List;

public class Configuration {

    public BossBar bossBar = new BossBar();
    public WonSound wonSound = new WonSound();

    public List<XMaterial> allowedPlants = Arrays.asList(XMaterial.OAK_SAPLING, XMaterial.POTATOES, XMaterial.WHEAT);
    public Messages messages = new Messages();

    public static class WonSound {

        public boolean enabled = true;
        public String sound = "ui.toast.challenge_complete";
        public float volume = 0.6f;

    }

    public static class Messages {

        public String prefix = "&3CQuest &8>>";
        public String reloaded = "%prefix% config dosyaları güncellendi";
        public String completedTitle = "&aTebrikler";
        public String completedSubtitle = "&fGörevi Başarıyla Tamamladın";
        public String completedChatMessage = "%prefix% &aGörevi başarıyla tamamladın";
        public String hasNotPermission = "%prefix% &cBunu yapmak için yeterli yetkin yok";


    }

    public static class BossBar {
        public String content = "&a%quest_description%";
        public net.kyori.adventure.bossbar.BossBar.Color color = net.kyori.adventure.bossbar.BossBar.Color.GREEN;
        public net.kyori.adventure.bossbar.BossBar.Overlay segment = net.kyori.adventure.bossbar.BossBar.Overlay.NOTCHED_10;
    }

}
