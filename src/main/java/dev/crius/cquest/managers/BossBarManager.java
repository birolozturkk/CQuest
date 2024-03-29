package dev.crius.cquest.managers;

import dev.crius.cquest.CQuest;
import dev.crius.cquest.placeholder.PlaceholderBuilder;
import dev.crius.cquest.quest.Quest;
import dev.crius.cquest.utils.StringUtils;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

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
        if(!isSupport()) return;
        Optional<BossBar> bossBar = getBossBar(player);
        Optional<Quest> quest = plugin.getQuestManager().getQuest(player);
        if (bossBar.isEmpty()) return;
        if (quest.isEmpty()) {
            hide(player);
            return;
        }
        bossBar.get().color(plugin.getConfiguration().bossBar.color);
        bossBar.get().overlay(plugin.getConfiguration().bossBar.segment);
        bossBar.get().progress(getProgress(player, quest.get()));
        bossBar.get().name(Component.text(getContent(player, quest.get())));
    }


    public void create(Player player) {
        Optional<Quest> quest = plugin.getQuestManager().getQuest(player);
        if (quest.isEmpty()) return;

        BossBar bossBar = BossBar.bossBar(Component.text(getContent(player, quest.get())),
                getProgress(player, quest.get()), plugin.getConfiguration().bossBar.color, plugin.getConfiguration().bossBar.segment);
        bossBars.put(player.getUniqueId(), bossBar);
    }

    public void show(Player player) {
        if(!isSupport()) return;
        Optional<BossBar> bossBar = getBossBar(player);
        if (bossBar.isEmpty()) return;
        update(player);
        Audience receiver = plugin.adventure().player(player);
        receiver.showBossBar(bossBar.get());
    }

    public void hide(Player player) {
        BossBar bossBar = bossBars.get(player.getUniqueId());
        if (bossBar == null) return;
        bossBars.remove(player.getUniqueId());
        Audience receiver = plugin.adventure().player(player);
        receiver.hideBossBar(bossBar);
    }

    private float getProgress(Player player, Quest quest) {
        float progress = (float) quest.getProgress(player) / quest.getRequirementProgress();
        if (progress > 1) return 1f;
            return progress;
    }

    private String getContent(Player player, Quest quest) {
        return StringUtils.format(plugin.getConfiguration().bossBar.content,
                new PlaceholderBuilder().applyForQuest(quest, player).build());
    }

    private Optional<BossBar> getBossBar(Player player) {
        BossBar bossBar = bossBars.get(player.getUniqueId());
        if (bossBar == null) {
            create(player);
            return Optional.ofNullable(bossBars.get(player.getUniqueId()));
        }
        return Optional.of(bossBar);
    }

    private boolean isSupport() {
        return plugin.getConfiguration().bossBar != null;
    }
}
