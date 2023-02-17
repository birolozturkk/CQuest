package dev.crius.cquest.managers;

import dev.crius.cquest.CQuest;
import dev.crius.cquest.placeholder.PlaceholderBuilder;
import dev.crius.cquest.quest.Quest;
import dev.crius.cquest.utils.StringUtils;
import net.kyori.adventure.Adventure;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.text.Component;
import org.bukkit.Color;
import org.bukkit.entity.Boss;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import javax.swing.plaf.PanelUI;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class BossBarManager {

    private final CQuest plugin;
    private final Map<UUID, BossBar> bossBars = new HashMap<>();

    public BossBarManager(CQuest plugin) {
        this.plugin = plugin;
    }

    public void update(Player player) {
        BossBar bossBar = getBossBar(player);
        Optional<Quest> quest = plugin.getQuestManager().getQuest(player);
        String content = StringUtils.format(quest.get().getDescription(),
                new PlaceholderBuilder().applyForQuest(quest.get(), player).build());
        float progress = (float) quest.get().getProgress(player) / quest.get().getRequirementProgress(player);
        bossBar.progress(progress);
        bossBar.name(Component.text(content));
    }


    public void add(Player player) {
        hide(player);
        Optional<Quest> quest = plugin.getQuestManager().getQuest(player);
        if(quest.isEmpty()) return;
        String content = StringUtils.format(quest.get().getDescription(),
                new PlaceholderBuilder().applyForQuest(quest.get(), player).build());
        BossBar bossBar = BossBar.bossBar(Component.text(content), 0, BossBar.Color.RED, BossBar.Overlay.NOTCHED_6);
        bossBars.put(player.getUniqueId(), bossBar);
        show(player);
    }

    public void show(Player player) {
        BossBar bossBar = getBossBar(player);
        plugin.adventure().player(player).showBossBar(bossBar);
    }

    public void hide(Player player) {
        BossBar bossBar = bossBars.get(player.getUniqueId());
        if(bossBar == null) return;
        bossBars.remove(player.getUniqueId());
        plugin.adventure().player(player).hideBossBar(bossBar);
    }
    private BossBar getBossBar(Player player) {
        BossBar bossBar = bossBars.get(player.getUniqueId());
        if(bossBar == null) {
            add(player);
            return bossBars.get(player.getUniqueId());
        }
        return bossBar;

    }
}
